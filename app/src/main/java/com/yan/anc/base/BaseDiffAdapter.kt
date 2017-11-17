package com.yan.anc.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

/**
 * Created by yan on 2017/11/5.
 */
abstract class BaseDiffAdapter<T, V : RecyclerView.ViewHolder>(protected val context: Context) : RecyclerView.Adapter<V>() {
    protected var items: List<T>? = null
    private var dataVersion = 0

    @SuppressLint("StaticFieldLeak")
    @MainThread
    fun replace(update: List<T>?) {
        dataVersion++
        when {
            items == null -> {
                if (update == null) {
                    return
                }
                items = update
                notifyDataSetChanged()
            }
            update == null -> {
                val oldSize = items!!.size
                items = null
                notifyItemRangeRemoved(0, oldSize)
            }
            else -> {
                val startVersion = dataVersion
                val oldItems = items
                object : AsyncTask<Void, Void, DiffUtil.DiffResult>() {
                    override fun doInBackground(vararg voids: Void): DiffUtil.DiffResult {
                        return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                            override fun getOldListSize(): Int = oldItems!!.size

                            override fun getNewListSize(): Int = update.size

                            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@BaseDiffAdapter.areItemsTheSame(oldItem, newItem)
                            }

                            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                                val oldItem = oldItems!![oldItemPosition]
                                val newItem = update[newItemPosition]
                                return this@BaseDiffAdapter.areContentsTheSame(oldItem, newItem)
                            }
                        })
                    }

                    override fun onPostExecute(diffResult: DiffUtil.DiffResult) {
                        if (startVersion != dataVersion) {
                            // ignore update
                            return
                        }
                        items = update
                        diffResult.dispatchUpdatesTo(this@BaseDiffAdapter)

                    }
                }.execute()
            }
        }
    }


    protected abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    protected abstract fun areContentsTheSame(oldItem: T, newItem: T): Boolean

    override fun getItemCount(): Int = if (items == null) 0 else items!!.size
}