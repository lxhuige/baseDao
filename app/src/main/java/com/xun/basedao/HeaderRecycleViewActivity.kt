package com.xun.basedao

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.xun.basedao.adapter.RecycleViewAdap
import com.xun.basedao.library.weight.ItemDiverDecoration
import com.xun.basedao.library.weight.ItemGridDiverDecoration
import kotlinx.android.synthetic.main.activity_header_recycle_view.*

class HeaderRecycleViewActivity : AppCompatActivity() {
    private val adapter by lazy { RecycleViewAdap() }
    private val itemDiverDecoration by lazy { ItemDiverDecoration(this) }
    private val itemGridDiverDecoration by lazy { ItemGridDiverDecoration(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_header_recycle_view)

        recyclerView.addItemDecoration(itemDiverDecoration)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val inflate = LayoutInflater.from(this).inflate(R.layout.header_item, null)
        val footer = LayoutInflater.from(this).inflate(R.layout.header_item, null)
        val footer1 = LayoutInflater.from(this).inflate(R.layout.header_item, null)

        recyclerView.addFooterView(inflate)
        recyclerView.addHeaderView(footer)
        recyclerView.addHeaderView(footer1)

        delete.setOnClickListener { recyclerView.removeHeader(footer) }
    }
}
