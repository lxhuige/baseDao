package com.xun.basedao.library.weight.headerandfooterrecycleView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * 添加头部 尾部的RecycleView
 */
public class HeaderRecyclerVIew extends RecyclerView {
    static final String TAG = "HeaderRecyclerVIew";
    private Adapter mAdapter;
    ArrayList<FixedViewInfo> mHeaderViewInfos = new ArrayList<>();
    ArrayList<FixedViewInfo> mFooterViewInfos = new ArrayList<>();

    public HeaderRecyclerVIew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeaderViewInfos.size() > 0 || mFooterViewInfos.size() > 0) {
            mAdapter = wrapHeaderListAdapterInternal(mHeaderViewInfos, mFooterViewInfos, adapter);
        } else {
            mAdapter = adapter;
        }
        super.setAdapter(mAdapter);
    }


    public void addFooterView(View v) {
        addFooterView(v, null);
    }

    public void addFooterView(View v, Object data) {
        if (v.getParent() != null && v.getParent() != this) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "The specified child already has a parent. "
                        + "You must call removeView() on the child's parent first.");
            }
        }
        final FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.viewType = 9090 + mFooterViewInfos.size();
        mFooterViewInfos.add(info);
        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecycleViewAdapter)) {
                wrapHeaderListAdapterInternal();
            }
        }
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null);
    }

    public void addHeaderView(View v, Object data) {
        if (v.getParent() != null && v.getParent() != this) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "The specified child already has a parent. "
                        + "You must call removeView() on the child's parent first.");
            }
        }
        final FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.viewType = 8080 + mHeaderViewInfos.size();
        mHeaderViewInfos.add(info);
        // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof HeaderViewRecycleViewAdapter)) {
                wrapHeaderListAdapterInternal();
            }
        }
    }

    protected void wrapHeaderListAdapterInternal() {
        mAdapter = wrapHeaderListAdapterInternal(mHeaderViewInfos, mFooterViewInfos, mAdapter);
        super.setAdapter(mAdapter);
    }

    private Adapter wrapHeaderListAdapterInternal(ArrayList<FixedViewInfo> headerViewInfos, ArrayList<FixedViewInfo> footerViewInfos, Adapter adapter) {
        return new HeaderViewRecycleViewAdapter(adapter, headerViewInfos, footerViewInfos);
    }

    /**
     * 移除头部View
     *
     * @param view 要移除的View
     * @return false
     */
    public boolean removeHeader(View view) {
        for (int i = mHeaderViewInfos.size() - 1; i >= 0; i--) {
            if (mHeaderViewInfos.get(i).view == view) {
                mHeaderViewInfos.remove(i);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 移除尾部 View
     *
     * @param view 要移除的View
     * @return false
     */
    public boolean removeFooter(View view) {
        for (int i = mFooterViewInfos.size() - 1; i >= 0; i--) {
            if (mFooterViewInfos.get(i).view == view) {
                mFooterViewInfos.remove(i);
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
                return true;
            }
        }
        return false;
    }

    public Object getItem(int pos) {
        if (mAdapter instanceof HeaderViewRecycleViewAdapter) {
            return ((HeaderViewRecycleViewAdapter) mAdapter).getItem(pos);
        }
        return null;
    }


    /**
     * A class that represents a fixed view in a list, for example a header at the top
     * or a footer at the bottom.
     */
    public class FixedViewInfo {
        /**
         * The view to add to the list
         */
        public View view;
        /**
         * The data backing the view. This is returned from {@link HeaderRecyclerVIew#getItem(int)}.
         */
        public Object data;

        public int viewType;
    }

}
