package com.yan.anc.widget

import android.content.Context
import android.util.AttributeSet
import com.yan.pullrefreshlayout.PullRefreshLayout
import com.yan.pullrefreshlayout.ShowGravity

/**
 * Created by yan on 2017/11/7.
 */
class RefreshLayout(context: Context, attrs: AttributeSet) : PullRefreshLayout(context, attrs) {
    init {
        setHeaderShowGravity(ShowGravity.FOLLOW_CENTER)
        setHeaderView(RefreshHeader(context, this))

        setOnRefreshListener(object : OnRefreshListenerAdapter() {
            override fun onRefresh() {
                super.onRefresh()
                postDelayed({ refreshComplete() }, 2000L)
            }
        })
    }

    fun superRefreshComplete() = super.refreshComplete()

    override fun refreshComplete() = getHeaderView<RefreshHeader>().refreshFinish()

}