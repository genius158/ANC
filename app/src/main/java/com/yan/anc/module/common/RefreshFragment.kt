package com.yan.anc.module.common

import com.yan.anc.base.BaseFragment

/**
 * Created by yan on 2017/11/5.
 */
class RefreshFragment : BaseFragment() {
    companion object {
        fun newInstance(): RefreshFragment {
            return makeArgs(RefreshFragment()) as RefreshFragment
        }
    }
}