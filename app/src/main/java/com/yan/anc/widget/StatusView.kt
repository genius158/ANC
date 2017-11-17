package com.yan.anc.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.yan.anc.R
import com.yan.pullrefreshlayout.PRLCommonUtils

/**
 * Created by yan on 2017/11/7.
 */
class StatusView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    var status = "loading..."

    fun loading() {
        visibility = VISIBLE
        status = "loading..."
        invalidate()
    }

    fun error() {
        visibility = VISIBLE
        status = "error!"
        invalidate()
    }

    fun empty() {
        visibility = VISIBLE
        status = "null~"
        invalidate()
    }

    private val textPaint: Paint by lazy {
        Paint().apply {
            textSize = PRLCommonUtils.dipToPx(context, 18F).toFloat()
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.cr999999)
            typeface = Typeface.DEFAULT_BOLD
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val fontMetrics = textPaint.fontMetricsInt
        val baseLine = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent
        canvas.drawText(status
                , width / 2 - textPaint.measureText(status) / 2
                , height * 2 / 5 + baseLine.toFloat()
                , textPaint)
    }

}