package com.yan.anc.module.flush

import android.arch.lifecycle.*
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yan.anc.utils.GlideApp
import java.lang.ref.WeakReference

/**
 * Created by yan on 2017/11/8.
 */

class FlushViewModel(activity: FlushActivity, wrIv: WeakReference<ImageView>) : ViewModel() {
    private val flushImg: String = "http://api.dujin.org/bing/1366.php"

    private val turnRoad: MutableLiveData<Int> = MutableLiveData()

    fun getTurnRoad(): MutableLiveData<Int> = turnRoad

    init {
        GlideApp.with(activity).load(flushImg).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().listener(object : RequestListener<Drawable> {
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                turnRoad.value = 1
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                turnRoad.value = -1
                return false
            }
        }).into(wrIv.get())
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val flushActivity: FlushActivity, private var iv: ImageView) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = FlushViewModel(flushActivity, WeakReference(iv)) as T
    }

}