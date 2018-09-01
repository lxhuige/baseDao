package com.xun.basedao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.xun.basedao.adapter.RecycleViewAdap
import com.xun.basedao.library.weight.ItemDiverDecoration
import com.xun.basedao.library.weight.ItemGridDiverDecoration
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecycleViewActivity : AppCompatActivity() {

    private val adapter by lazy { RecycleViewAdap() }
    private val itemDiverDecoration by lazy { ItemDiverDecoration(this) }
    private val itemGridDiverDecoration by lazy { ItemGridDiverDecoration(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)
        button.setOnClickListener {
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is GridLayoutManager) {
                recyclerView.addItemDecoration(itemDiverDecoration)
                recyclerView.removeItemDecoration(itemGridDiverDecoration)
                recyclerView.layoutManager = LinearLayoutManager(this)
//                if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
//                    layoutManager.orientation = LinearLayoutManager.VERTICAL
//                    itemDiverDecoration.setOrientation(LinearLayoutManager.VERTICAL)
//                } else {
//                    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                    itemDiverDecoration.setOrientation(LinearLayoutManager.HORIZONTAL)
//                }
            } else {

                recyclerView.removeItemDecoration(itemDiverDecoration)
                recyclerView.addItemDecoration(itemGridDiverDecoration)
                recyclerView.layoutManager = GridLayoutManager(this, 3)


            }
            adapter.notifyDataSetChanged()
        }
        recyclerView.addItemDecoration(itemGridDiverDecoration)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter

    }
}
