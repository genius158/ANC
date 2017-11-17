package com.yan.anc.module.common

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.anc.R
import com.yan.anc.base.BaseFragment
import com.yan.anc.module.common.viewmodel.ModelFactory
import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.utils.ToastHelper
import com.yan.anc.widget.StatusView
import com.yan.pullrefreshlayout.PullRefreshLayout
import javax.inject.Inject

/**
 * Created by yan on 2017/11/5.
 */
open class RefreshFragment : BaseFragment() {
    private var rootView: View? = null
    protected lateinit var refreshLayout: PullRefreshLayout
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var statusView: StatusView

    @Inject lateinit var modelFactory: ModelFactory

    @Inject lateinit var toastHelper: ToastHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_frefresh, container, false)
            onLoad(rootView as View)
        }
        return rootView
    }

    protected open fun onLoad(rootView: View) {
        refreshLayout = rootView.findViewById(R.id.refreshLayout)
        recyclerView = rootView.findViewById(R.id.recyclerView)
        statusView = rootView.findViewById(R.id.statusView)

        refreshLayout.setOnRefreshListener(object : PullRefreshLayout.OnRefreshListenerAdapter() {
            override fun onRefresh() {
                this@RefreshFragment.onRefresh()
            }
        })
    }

    override fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {
        appComponent.plus(RefreshDIContact.RefreshModule()).injectTo(this)
    }

    open fun onRefresh() {

    }
}