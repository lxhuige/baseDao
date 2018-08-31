package com.xun.basedao.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.xun.basedao.R
import kotlinx.android.synthetic.main.recycleview_item.view.*
import java.util.ArrayList

class RecycleViewAdap : RecyclerView.Adapter<ViewHolder>() {

    private val data by lazy {
        val list = ArrayList<String>()

        for (i in 0..60) {
            list.add(String.format("item%s", i))
        }

        return@lazy list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return ViewHolder(inflate)

    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvTitle.text = data[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "pos$position", Toast.LENGTH_LONG).show()
        }
    }
}