package com.xun.basedao.library.weight.headerandfooterrecycleView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HeaderViewRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private RecyclerView.Adapter mAdapter;
    private ArrayList<HeaderRecyclerVIew.FixedViewInfo> mHeaderViewInfos;
    private ArrayList<HeaderRecyclerVIew.FixedViewInfo> mFooterViewInfos;

    public HeaderViewRecycleViewAdapter(RecyclerView.Adapter mAdapter, ArrayList<HeaderRecyclerVIew.FixedViewInfo> mHeaderViewInfos, ArrayList<HeaderRecyclerVIew.FixedViewInfo> mFooterViewInfos) {
        this.mAdapter = mAdapter;
        this.mHeaderViewInfos = mHeaderViewInfos;
        this.mFooterViewInfos = mFooterViewInfos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for (HeaderRecyclerVIew.FixedViewInfo item : mHeaderViewInfos) {
            if (item.viewType == viewType) {
                return new ViewHolder(item.view);
            }
        }
        for (HeaderRecyclerVIew.FixedViewInfo item : mFooterViewInfos) {
            if (item.viewType == viewType) {
                return new ViewHolder(item.view);
            }
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int numHeaders = getHeadersCount();
        if (position >= numHeaders) {
            final int adjPosition = position - numHeaders;
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).viewType;
        }
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(position);
            }
        }
        return mFooterViewInfos.get(adjPosition - adapterCount).viewType;
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
    }


    public Object getItem(int position) {
        // Header (negative positions will throw an IndexOutOfBoundsException)
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return mHeaderViewInfos.get(position).data;
        }
        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return null;
            }
        }
        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return mFooterViewInfos.get(adjPosition - adapterCount).data;
    }

    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

}
