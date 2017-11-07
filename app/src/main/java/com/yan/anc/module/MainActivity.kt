package com.yan.anc.module

import android.os.Bundle
import android.view.MotionEvent
import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.R
import com.yan.anc.base.BaseActivity
import javax.inject.Inject

/**
 * Created by yan on 2017/11/5.
 */
class MainActivity : BaseActivity(), MainMVPContact.MainView {

    @Inject lateinit var presenter: MainMVPContact.MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.log()
    }

    override fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {
        appComponent.plus(MainDIContact.MainModule(this)).injectTo(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> toastHelper.showShortToast("sdsdsdsdsdsdsdsd")
        }
        return super.onTouchEvent(event)
    }
}
