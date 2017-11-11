package com.yan.anc.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.arch.lifecycle.GenericLifecycleObserver
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.yan.anc.R

/**
 * Created by yan on 2017/11/7.
 */
class CountdownView(context: Context, attrs: AttributeSet) : View(context, attrs), GenericLifecycleObserver {
    private var fragment: Fragment? = null

    private val TITLE: String by lazy { context.getString(R.string.title_cv) }

    private val textColor: Int by lazy { ContextCompat.getColor(context, R.color.colorCvPrimary) }
    private val bgColor: Int by lazy { ContextCompat.getColor(context, R.color.colorCvBg) }

    private val DURING: Int = 4000

    private var rotation: Int = 0

    lateinit var onCountDownEndListener: () -> Unit?

    private val circlePaint: Paint by lazy {
        Paint().apply {
            strokeWidth = width.toFloat() / 12
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = textColor
        }
    }
    private val textPaint: Paint by lazy {
        Paint().apply {
            textSize = width.toFloat() / 3
            isAntiAlias = true
            color = textColor
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    private val valueAnimation: ValueAnimator = ValueAnimator.ofInt(0, 360).apply {
        duration = DURING.toLong()
        interpolator = LinearInterpolator()
        addUpdateListener({ animation ->
            run {
                rotation = animation.animatedValue as Int
                invalidate()
            }
        })
        addListener(object : AnimatorListenerAdapter() {
            private var isCancel: Boolean = false
            override fun onAnimationCancel(animation: Animator?) {
                isCancel = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                if (!isCancel) {
                    onCountDownEndListener.invoke()
                }
            }
        })
    }

    init {
        getLifecycleOwner()?.lifecycle?.addObserver(this)

        visibility = GONE
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(width.toFloat() / 2
                , width.toFloat() / 2
                , width.toFloat() / 2
                , textPaint.apply { color = bgColor })

        canvas.drawText(TITLE
                , width / 2 - textPaint.measureText(TITLE) / 2
                , (width / 2 + getTextBaseLineOffset()).toFloat()
                , textPaint.apply { color = textColor })

        canvas.drawArc(RectF(circlePaint.strokeWidth / 2
                , circlePaint.strokeWidth / 2
                , width - circlePaint.strokeWidth / 2
                , width - circlePaint.strokeWidth / 2
        ), 0F, rotation.toFloat(), false, circlePaint)

    }

    private fun getTextBaseLineOffset(): Int {
        val fontMetrics = textPaint.fontMetricsInt
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cancel()
    }

    fun star() {
        visibility = VISIBLE
        valueAnimation.start()
    }

    fun cancel() {
        valueAnimation.cancel()
    }

    fun attachFragment(fragment: Fragment) {
        this.fragment = fragment
    }

    private fun getLifecycleOwner(): LifecycleOwner? {
        if (fragment == null) {
            if (context is LifecycleOwner) {
                return context as LifecycleOwner
            }
        } else {
            if (fragment is LifecycleOwner) {
                return fragment
            }
        }

        return null
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.e("onStateChanged", source.lifecycle.currentState.toString() + "   " + event)
        when (event) {
            Lifecycle.Event.ON_RESUME -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                valueAnimation.resume()
            }
            Lifecycle.Event.ON_PAUSE -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                valueAnimation.pause()
            }
            Lifecycle.Event.ON_DESTROY -> {
                cancel()
                source.lifecycle.removeObserver(this)
            }
        }
    }

}