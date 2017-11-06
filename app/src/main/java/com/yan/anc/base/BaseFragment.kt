package com.yan.anc.base

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * Created by Administrator on 2017/11/5.
 */

abstract class BaseFragment : Fragment() {
    companion object {
        fun makeArgs(fragment: Fragment): Fragment {
            fragment.arguments = Bundle()
            return fragment
        }
    }

}