package com.yan.anc.module.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.yan.anc.module.android.repository.AndroidData
import com.yan.anc.module.common.RefreshFragment
import com.yan.anc.repository.ApiResponse
import com.yan.anc.repository.Resource
import com.yan.anc.repository.Status
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
        androidViewModel.androidData.observe(this, Observer<Resource<ApiResponse<List<AndroidData>>>> { data ->
            Log.e("androidViewModel", data?.data.toString() + "   " + data?.isRefresh + "   " + data?.status + "    androidViewModel")

            data?.let {
                if (it.isRefresh!! && it.status != Status.LOADING) {
                    refreshLayout.refreshComplete()
                }
            }
        })

    }

    override fun onRefresh() {
        androidViewModel.getData()
    }

    override fun onLoad(rootView: View) {
        super.onLoad(rootView)

        dataList.add("12")
        dataList.add("12")
        dataList.add("12")

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AndroidAdapter(context, dataList)


        refreshLayout.postDelayed({ refreshLayout.autoRefresh() }, 120)
    }

    companion object {
        fun newInstance(): AndroidFragment = makeArgs(AndroidFragment()) as AndroidFragment
    }

}