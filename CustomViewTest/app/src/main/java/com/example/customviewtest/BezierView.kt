package com.example.customviewtest

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class BezierView : View {
    private var bezierPath = Path()
    private val paint = Paint()

    private val mColor = Color.BLACK

    constructor(context: Context?) : super(context) {
        initDraw()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initDraw()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initDraw()
    }

    // 初始化路径和画笔
    private fun initDraw() {
        bezierPath = Path()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.color = Color.RED
        paint.strokeWidth = 1.5.toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val defaultValue = 700
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        //AT_MOST对应的是wrap_content的宽高
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultValue, defaultValue)
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultValue, heightSpecSize)
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultValue)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 半弧的宽度是可以根据手指位置而变化的，这里简化写死为200
        val currentWidth = 200f
        val height = height.toFloat()
        val maxWidth = width
        val centerY = height / 2
        val progress = currentWidth / maxWidth
        if (progress == 0f) {
            return
        }

        //开始画半弧图形
        //设置画笔颜色，现在设置为黑色
        paint.color = mColor
        //半弧颜色的深度是可以根据手指位置而变化的，这里简化写死
        paint.alpha = (500 * progress).toInt()
        val bezierWidth = currentWidth / 2
        val coordinateX = 0f //2

        //正式绘制贝塞尔曲线，使用cubicTo()方法
        bezierPath.reset()
        bezierPath.moveTo(coordinateX, 0F)
        bezierPath.cubicTo(
            coordinateX,
            height / 4f,
            bezierWidth,
            height * 3f / 8,
            bezierWidth,
            centerY
        )
        bezierPath.cubicTo(
            bezierWidth,
            height * 5f / 8,
            coordinateX,
            height * 3f / 4,
            coordinateX,
            height
        )
        bezierPath.let { canvas.drawPath(it, paint) }
    }
}