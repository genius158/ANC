package com.yan.anc.module.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.yan.anc.R

/**
 * Created by yan on 2017/11/5.
 */
class AndroidItemHolder( context: Context, parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_android_data, parent, false)) {

    val contentTv: TextView = itemView.findViewById(R.id.contentTv)
    val timeTv: TextView = itemView.findViewById(R.id.timeTv)

}