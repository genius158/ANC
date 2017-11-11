package com.yan.anc.module.android

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.yan.anc.module.common.RefreshFragment
import kotlin.collections.ArrayList

/**
 * Created by yan on 2017/11/5.
 */
class AndroidFragment : RefreshFragment() {
    private var dataList: ArrayList<Any> = ArrayList()

    private lateinit var androidViewModel: AndroidViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       androidViewModel = ViewModelProviders.of(this, modelFactory).get(AndroidViewModel::class.java)
    }

    override fun onLoad(rootView: View) {
        super.onLoad(rootView)

        dataList.add("12")
        dataList.add("12")
        dataList.add("12")

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AndroidAdapter(context, dataList)

    }

    companion object {
        fun newInstance(): AndroidFragment {
            return makeArgs(AndroidFragment()) as AndroidFragment
        }
    }

}