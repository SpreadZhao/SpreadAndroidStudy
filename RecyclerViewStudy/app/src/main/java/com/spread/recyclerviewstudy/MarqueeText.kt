package com.spread.recyclerviewstudy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class MarqueeText : View {

  private lateinit var realText: String   // 实际要显示的文本

  private val mPaint = Paint()

//    private var textWidth = 0   // 文字实时宽度

  private var x = 0   // 文字的横坐标

  private var mLastFocusX = 0

  private var mLastFocusY = 0

  var text: String
    get() = realText
    set(value) {
      realText = value
      postInvalidate()
    }

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    init()
  }

  private fun init() {
    realText = "None"
    mPaint.textSize = 14F.sp(context)
    mPaint.color = Color.BLACK
    mPaint.isAntiAlias = true
//        textWidth = mPaint.measureText(realText).toInt()
    x = measuredWidth
  }

  private val currTextHeight: Float
    get() {
      val metrics = mPaint.fontMetrics
      return abs(metrics.ascent - metrics.descent)
    }

  // 基线位置的偏移量，用来计算Canvas画图时的纵坐标
  private val baselineOffset: Float
    get() {
      val metrics = mPaint.fontMetrics
      return (currTextHeight - metrics.bottom + metrics.top) / 2 - metrics.top
    }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    Log.d("MarqueeText", "onMeasure called")

    val widthMode = MeasureSpec.getMode(widthMeasureSpec)
    val heightMode = MeasureSpec.getMode(heightMeasureSpec)
    val widthSize = MeasureSpec.getSize(widthMeasureSpec)
    val heightSize = MeasureSpec.getSize(heightMeasureSpec)

    if (heightMode == MeasureSpec.AT_MOST) {
//            val bounds = Rect()
//            mPaint.getTextBounds(
//                realText,
//                0, realText.length,
//                bounds
//            )
      // 实现wrap_content
      setMeasuredDimension(widthSize, (paddingTop + currTextHeight + paddingBottom).toInt())
    } else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
  }

  override fun onTouchEvent(event: MotionEvent): Boolean {
    Log.d("TimeWindow", "onTouch, ev: $event")
    when (event.action) {
      MotionEvent.ACTION_MOVE -> {
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()
        val destX = x - mLastFocusX
        val destY = y - mLastFocusY
        val left = left + destX
        val top = top + destY
        val right = right + destY
        val bottom = bottom + destY
        layout(left, top, right, bottom)
        mLastFocusX = x
        mLastFocusY = y
      }

      MotionEvent.ACTION_DOWN -> {
        mLastFocusX = event.rawX.toInt()
        mLastFocusY = event.rawY.toInt()
        performClick()
      }
    }
    return true
  }

  override fun performClick(): Boolean {
    return super.performClick()
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    Log.d("MarqueeText", "onLayout called")
    super.onLayout(changed, left, top, right, bottom)
  }

  override fun onDraw(canvas: Canvas) {
    Log.d("MarqueeText", "onDraw called")
    val textWidth = mPaint.measureText(realText)
    if (measuredWidth < textWidth) {
      canvas.drawText(realText, x.toFloat(), paddingTop + baselineOffset, mPaint)
      x -= 2
      if (x < -textWidth) {
        x = measuredWidth
      }
      postInvalidateDelayed(10)
    } else {
      canvas.drawText(realText, measuredWidth - textWidth, paddingTop + baselineOffset, mPaint)
    }
  }

  private fun Float.sp(context: Context) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)
}