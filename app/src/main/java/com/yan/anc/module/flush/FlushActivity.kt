package com.yan.anc.module.flush

import android.content.Intent
import android.view.MotionEvent

import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.base.BaseActivity
import com.yan.anc.module.MainActivity

/**
 * Created by yan on 2017/11/8.
 */

class FlushActivity : BaseActivity() {
    override fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        return super.onTouchEvent(event)
    }
}
