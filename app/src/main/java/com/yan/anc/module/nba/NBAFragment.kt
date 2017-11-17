package com.yan.anc.nba

import com.yan.anc.base.BaseFragment

/**
 * Created by yan on 2017/11/5.
 */
class NBAFragment : BaseFragment() {
    companion object {
        fun newInstance(): NBAFragment = makeArgs(NBAFragment()) as NBAFragment
    }
}