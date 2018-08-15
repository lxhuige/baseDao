package com.xun.basedao

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xun.basedao.sqlite.BaseDaoFactory
import com.xun.basedao.sqlite.UserInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseDaoFactory.getInstance().init(applicationContext,1)
    }

    fun insert(v: View) {
        val dao = BaseDaoFactory.getInstance().getBaseDao(UserInfo::class.java)
        dao?.insert(UserInfo().apply {
            age = 25
            name = "李校辉"
            picHad = "https://github.com/lxhuige/baseDao"
        })
    }

    fun update(v: View) {
        val dao = BaseDaoFactory.getInstance().getBaseDao(UserInfo::class.java)
        dao?.queryById(1)?.let {
            it.age = ++it.age
            dao.update(it)
            return@let
        }
    }

    fun delete(v: View) {
        val dao = BaseDaoFactory.getInstance().getBaseDao(UserInfo::class.java)
        dao?.deleteById(2)
    }

    fun query(v: View) {
        val dao = BaseDaoFactory.getInstance().getBaseDao(UserInfo::class.java)
        dao?.queryAll()?.forEach { item ->
            tvShow.text = tvShow.text.toString() + item.toString()
        }
    }
}
