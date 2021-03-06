package com.yan.anc.module

import android.os.Bundle
import android.support.v4.view.ViewPager
import com.yan.anc.DaggerAppDIContact_AppComponent
import com.yan.anc.R
import com.yan.anc.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by yan on 2017/11/5.
 */
class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        load()
    }

    override fun appComponent(appComponent: DaggerAppDIContact_AppComponent) {
        appComponent.injectTo(this)
    }

    private fun load() {
        mainVp.adapter = MainVPAdapter(supportFragmentManager)
        mainVp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                mainBnv.menu.getItem(position).isChecked = true
            }
        })
        mainBnv.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.android -> mainVp.setCurrentItem(0, Math.abs(mainVp.currentItem - 0) == 1)
                R.id.nba -> mainVp.setCurrentItem(1, Math.abs(mainVp.currentItem - 1) == 1)
                R.id.comic -> mainVp.setCurrentItem(2, Math.abs(mainVp.currentItem - 2) == 1)
            }
            false
        }
    }

    private var lastTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 1000) {
            super.onBackPressed()
        } else {
            toastHelper.showShortToast(getString(R.string.title_exit_app))
        }
        lastTime = System.currentTimeMillis()
    }
}
