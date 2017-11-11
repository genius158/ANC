package com.yan.anc.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator
import com.yan.anc.R
import com.yan.pullrefreshlayout.PRLCommonUtils
import com.yan.pullrefreshlayout.PullRefreshLayout

/**
 * Created by yan on 2017/11/7.
 */
class RefreshHeader(context: Context, private val pullRefreshLayout: RefreshLayout) : View(context), PullRefreshLayout.OnPullListener {
    private val refreshText: String by lazy { context.getString(R.string.app_name) }

    private val layoutWidth: Int
    private var refreshWidth: Int = 70
    private var refreshBorderWidth: Int = 3
    private var refreshpadding: Int = 10
    private var rotation: Int = 0

    private var textSize: Int = 12

    private var angle: Int = 0

    private val camera: Camera = Camera()
    private val circlePaint: Paint = Paint()
    private val textPaint: Paint = Paint()

    private var pullState = 0

    private var isRefreshFinish: Boolean = false

    private val refreshEndRepeatListener: AnimatorListenerAdapter = object : AnimatorListenerAdapter() {
        override fun onAnimationRepeat(animation: Animator) {
            if (isRefreshFinish) {
                pullRefreshLayout.superRefreshComplete()
                animation.cancel()
            }
        }
    }

    private val refreshingAnimation: ValueAnimator = ValueAnimator.ofInt(0, 360).apply {
        duration = 1300
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.RESTART
        interpolator = LinearInterpolator()
        addUpdateListener({ animation -> rotation = animation.animatedValue as Int })
        addListener(refreshEndRepeatListener)
    }

    init {
        refreshWidth = PRLCommonUtils.dipToPx(context, refreshWidth.toFloat())
        refreshBorderWidth = PRLCommonUtils.dipToPx(context, refreshBorderWidth.toFloat())
        refreshpadding = PRLCommonUtils.dipToPx(context, refreshpadding.toFloat())
        textSize = PRLCommonUtils.dipToPx(context, textSize.toFloat())

        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        layoutWidth = displayMetrics.widthPixels

        circlePaint.strokeWidth = refreshBorderWidth.toFloat()
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeCap = Paint.Cap.ROUND
        circlePaint.isAntiAlias = true
        circlePaint.color = ContextCompat.getColor(context, R.color.colorPrimary)

        textPaint.textSize = textSize.toFloat()
        textPaint.isAntiAlias = true
        textPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        textPaint.typeface = Typeface.DEFAULT_BOLD
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(layoutWidth, refreshWidth)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(RectF((
                layoutWidth.toFloat() - refreshWidth.toFloat() + circlePaint.strokeWidth) / 2 + refreshpadding
                , circlePaint.strokeWidth / 2 + refreshpadding
                , (layoutWidth.toFloat() + refreshWidth.toFloat() - circlePaint.strokeWidth) / 2 - refreshpadding
                , refreshWidth.toFloat() - circlePaint.strokeWidth / 2 - refreshpadding
        ), -90F, if (pullState == 1) 360F else angle.toFloat(), false, circlePaint)

        if (pullState == 1) {
            canvas.save()
            camera.save()
            camera.rotateY(rotation.toFloat())
            canvas.translate(layoutWidth.toFloat() / 2, refreshWidth.toFloat() / 2)
            camera.applyToCanvas(canvas)
            canvas.translate(-layoutWidth.toFloat() / 2, -refreshWidth.toFloat() / 2)
            camera.restore()

            canvas.drawText(refreshText
                    , layoutWidth / 2 - textPaint.measureText(refreshText) / 2
                    , (refreshWidth / 2 + getTextBaseLineOffset()).toFloat()
                    , textPaint)
            canvas.restore()
            invalidate()
        } else {
            canvas.drawText(refreshText
                    , layoutWidth / 2 - textPaint.measureText(refreshText) / 2
                    , (refreshWidth / 2 + getTextBaseLineOffset()).toFloat()
                    , textPaint)
        }
    }

    private fun getTextBaseLineOffset(): Int {
        val fontMetrics = textPaint.fontMetricsInt
        return (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
    }

    fun refreshFinish() {
        isRefreshFinish = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (pullState == 1) {
            refreshingAnimation.start()
        } else if (isRefreshFinish) {
            refreshEndRepeatListener.onAnimationEnd(refreshingAnimation)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        refreshingAnimation.cancel()
    }

    override fun onPullHolding() {
        pullState = 1
        refreshingAnimation.start()
        invalidate()
    }

    override fun onPullReset() {
        pullState = 0
        rotation = 0
        isRefreshFinish = false
    }

    override fun onPullChange(percent: Float) {
        if (pullState != 1) {
            angle = (Math.min(Math.abs(percent), 1F) * 360).toInt()
            invalidate()
        }
    }

    override fun onPullFinish() {
        pullState = 2
    }

    override fun onPullHoldTrigger() {}

    override fun onPullHoldUnTrigger() {}

}