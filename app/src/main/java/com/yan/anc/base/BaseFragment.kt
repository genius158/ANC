package com.yan.anc.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.App

/**
 * Created by yan on 2017/11/5.
 */

open class BaseFragment : Fragment() {
    companion object {
        fun makeArgs(fragment: Fragment): Fragment {
            fragment.arguments = Bundle()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent(App.appComponent)
    }

    protected open fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {}

}