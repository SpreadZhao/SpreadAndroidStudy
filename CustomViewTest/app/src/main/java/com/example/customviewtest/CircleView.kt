package com.example.customviewtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircleView : View {
    private val mColor = Color.RED
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = mColor }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 200)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val w = width - paddingLeft - paddingRight
        val h = height - paddingTop - paddingBottom
        val r = min(w, h) / 2
        canvas?.drawCircle(
            (paddingLeft + w / 2).toFloat(),
            (paddingTop + h / 2).toFloat(),
            r.toFloat(),
            mPaint
        )
    }


}