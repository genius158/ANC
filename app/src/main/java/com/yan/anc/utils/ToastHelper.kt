package com.yan.anc.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

import javax.inject.Inject

/**
 * Created by yan on 2017/11/6.
 */

class ToastHelper() {
    private lateinit var toast: Toast

    @SuppressLint("ShowToast")
    @Inject
    constructor(context: Context) : this() {
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun showShortToast(str: String) {
        toast.apply {
            setText(str)
            duration = Toast.LENGTH_SHORT
        }.show()
    }
}