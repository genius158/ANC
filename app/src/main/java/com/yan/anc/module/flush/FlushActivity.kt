package com.yan.anc.module.flush

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.gyf.barlibrary.ImmersionBar

import com.yan.anc.R
import com.yan.anc.base.BaseActivity
import com.yan.anc.module.MainActivity
import kotlinx.android.synthetic.main.activity_flush.*

/**
 * Created by yan on 2017/11/8.
 */

class FlushActivity : BaseActivity() {
    private lateinit var flushViewModel: FlushViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flush)
        ImmersionBar.with(this).init()
        loadView()
        loadModel()
    }

    private fun loadView() {
        flushCv.onCountDownEndListener = { toMain() }
        flushCv.setOnClickListener {
            flushCv.cancel()
            flushCv.onCountDownEndListener.invoke()
        }
    }

    private fun loadModel() {
        flushViewModel = ViewModelProviders.of(this, FlushViewModel.Factory(this, flushIv)).get(FlushViewModel::class.java)
        flushViewModel.getTurnRoad().observe(this, Observer<Int> { road ->
            when (road) {
                1 -> flushCv.star()
                -1 -> flushIv.postDelayed({
                    if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
                        toMain()
                    }
                }, 1800)
            }
        })
    }

    private fun toMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {}

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy()
    }

}
