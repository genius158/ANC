package com.yan.anc.module

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.yan.anc.module.android.AndroidFragment
import com.yan.anc.module.comic.ComicAndAnimationFragment
import com.yan.anc.nba.NBAFragment

/**
 * Created by yan on 2017/11/5.
 */
class MainVPAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragmentLists: ArrayList<Fragment> = ArrayList()

    init {
        fragmentLists.add(AndroidFragment.newInstance())
        fragmentLists.add(ComicAndAnimationFragment.newInstance())
        fragmentLists.add(NBAFragment.newInstance())
    }

    override fun getItem(position: Int): Fragment = fragmentLists.get(position)

    override fun getCount(): Int = 3

}