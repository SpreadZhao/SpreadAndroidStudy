package com.spread.toucheventstudy

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

class MyFrameLayout : FrameLayout {
  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
    Log.d("TouchEventSpread", "FrameLayout intercept ${ev?.actionStr()} event")
    if (ev?.action == MotionEvent.ACTION_MOVE) return true
    return super.onInterceptTouchEvent(ev)
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    Log.d("TouchEventSpread", "FrameLayout handle ${event?.actionStr()} event")
    super.onTouchEvent(event)
    return true
  }
}

fun MotionEvent.actionStr(): String {
  return when(action) {
    MotionEvent.ACTION_DOWN -> "DOWN"
    MotionEvent.ACTION_MOVE -> "MOVE"
    MotionEvent.ACTION_UP -> "UP"
    else -> "OTHER"
  }
}