package com.yan.anc.module.android

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yan.anc.module.common.RefreshFragment
import kotlin.collections.ArrayList

/**
 * Created by yan on 2017/11/5.
 */
class AndroidFragment : RefreshFragment() {
    private var dataList: ArrayList<Any> = ArrayList()

    companion object {
        fun newInstance(): AndroidFragment {
            return makeArgs(AndroidFragment()) as AndroidFragment
        }
    }

    override fun onLoad(rootView: View) {
        super.onLoad(rootView)

        dataList.add("12")
        dataList.add("12")
        dataList.add("12")

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = AndroidAdapter(context, dataList)

    }

}