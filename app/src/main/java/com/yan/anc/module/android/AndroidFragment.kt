package com.yan.anc.module.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.yan.anc.module.android.adapter.AndroidAdapter
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
            Log.e("androidViewModel ", data?.data?.results.toString() + "   " + data?.status + "    " + data?.isRefresh)
            if (data?.isRefresh!!) {
                doRefresh(data)
            }
        })

    }

    private fun doRefresh(data: Resource<ApiResponse<List<AndroidData>>>) {
        when (data.status) {
            Status.LOADING -> if ((recyclerView.adapter as AndroidAdapter).itemCount == 0) {
                statusView.loading()
            }
            Status.SUCCESS -> {
                if ((data.data?.results == null || data.data.results!!.isEmpty()) && (recyclerView.adapter as AndroidAdapter).itemCount == 0) {
                    statusView.empty()
                } else {
                    statusView.visibility = View.GONE
                }
                (recyclerView.adapter as AndroidAdapter).replace(data.data?.results)
                refreshLayout.refreshComplete()

            }
            else->{
                if ((recyclerView.adapter as AndroidAdapter).itemCount == 0) {
                    statusView.error()
                } else {
                    toastHelper.showShortToast("获取数据失败")
                }
                refreshLayout.refreshComplete()
            }
        }
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
        recyclerView.adapter = AndroidAdapter(context)


        refreshLayout.postDelayed({ refreshLayout.autoRefresh() }, 120)
    }

    companion object {
        fun newInstance(): AndroidFragment = makeArgs(AndroidFragment()) as AndroidFragment
    }

}