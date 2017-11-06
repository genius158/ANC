package com.yan.anc.module.android

import com.yan.anc.base.BaseFragment

/**
 * Created by yan on 2017/11/5.
 */
class AndroidFragment : BaseFragment() {
    companion object {
        fun newInstance(): AndroidFragment {
            return makeArgs(AndroidFragment()) as AndroidFragment
        }
    }
}