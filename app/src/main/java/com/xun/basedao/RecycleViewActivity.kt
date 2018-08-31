package com.xun.basedao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.xun.basedao.adapter.RecycleViewAdap
import com.xun.basedao.library.weight.ItemDiverDecoration
import kotlinx.android.synthetic.main.activity_recycle_view.*

class RecycleViewActivity : AppCompatActivity() {

    private val adapter by lazy { RecycleViewAdap() }
    private val itemDiverDecoration by lazy { ItemDiverDecoration(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view)
        button.setOnClickListener {
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is LinearLayoutManager) {
                if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    itemDiverDecoration.setOrientation(LinearLayoutManager.VERTICAL)
                } else {
                    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    itemDiverDecoration.setOrientation(LinearLayoutManager.HORIZONTAL)
                }
            }
        }
        itemDiverDecoration.setVerticalPadding(30,30)
        itemDiverDecoration.setDraw(false)
        recyclerView.addItemDecoration(itemDiverDecoration)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}
