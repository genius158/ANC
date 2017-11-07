package com.yan.anc.module.android

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yan.anc.R
import java.util.*

/**
 * Created by yan on 2017/11/5.
 */
class AndroidAdapter(private val context: Context, private val dataList: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 1) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when(getItemViewType(position)){
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return object : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_android_header, parent, false)) {}
        }
        return object : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_android_header, parent, false)) {}
    }

    override fun getItemCount(): Int = dataList.size

}