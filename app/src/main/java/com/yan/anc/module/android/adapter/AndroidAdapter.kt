package com.yan.anc.module.android.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yan.anc.R
import com.yan.anc.base.BaseDiffAdapter
import com.yan.anc.module.android.repository.AndroidData

/**
 * Created by yan on 2017/11/5.
 */
class AndroidAdapter(context: Context) : BaseDiffAdapter<Any, RecyclerView.ViewHolder>(context) {

    override fun getItemViewType(position: Int): Int {
        if (items?.get(0) !is AndroidData) {
            return 1
        }
        return 2
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        when (getItemViewType(position)) {
            1 -> items?.get(position)?.let { loadHeader(holder, it) }
            2 -> items?.get(position)?.let { loadItem(holder as AndroidItemHolder, it as AndroidData) }
        }
    }

    private fun loadHeader(holder: RecyclerView.ViewHolder?, any: Any) {


    }

    private fun loadItem(itemHolder: AndroidItemHolder, androidData: AndroidData) {
        itemHolder.contentTv.text = androidData.desc
        itemHolder.timeTv.text = androidData.publishedAt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return object : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_android_header, parent, false)) {}
        }
        return AndroidItemHolder(context, parent)
    }


    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is AndroidData && newItem is AndroidData) {
            return oldItem._id.equals(newItem._id)

        } else if (oldItem !is AndroidData && newItem !is AndroidData) {
            return false
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is AndroidData && newItem is AndroidData) {
            return oldItem._id.equals(newItem._id) && oldItem.publishedAt.equals(newItem.publishedAt)

        } else if (oldItem !is AndroidData && newItem !is AndroidData) {
            return false
        }
        return false
    }
}