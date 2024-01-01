package com.spread.toucheventstudy

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

class TouchView : AppCompatTextView {

  private var num = 0

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    initView(attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    initView(attrs)
  }

  private fun initView(attrs: AttributeSet) {
    val typeArray = context.obtainStyledAttributes(attrs, R.styleable.TouchView)
    num = typeArray.getInt(R.styleable.TouchView_num, 0)
    typeArray.recycle()
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    Log.d("TouchEventSpread", "${event?.actionStr()} event handled by ${num}")
    return true
  }
}