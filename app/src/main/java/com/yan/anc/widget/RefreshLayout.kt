package com.yan.anc.widget

import android.content.Context
import android.util.AttributeSet
import com.yan.pullrefreshlayout.PullRefreshLayout

/**
 * Created by yan on 2017/11/7.
 */
class RefreshLayout(context: Context?, attrs: AttributeSet?) : PullRefreshLayout(context, attrs) {
    init {
        isRefreshEnable = false
    }
}