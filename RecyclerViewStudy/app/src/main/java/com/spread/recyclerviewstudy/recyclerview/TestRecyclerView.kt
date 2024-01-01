package com.spread.recyclerviewstudy.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView


class TestRecyclerView : RecyclerView {
  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun requestLayout() {
//    Log.d("TestRecyclerView", "requestLayout()")
    super.requestLayout()
  }

  override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
    return super.dispatchTouchEvent(ev)
  }

  override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
    Log.d("TestRecyclerView", "RecyclerView intercept ${e?.actionStr()}[${e?.hashCode()}]")
    return super.onInterceptTouchEvent(e)
  }

  override fun onTouchEvent(e: MotionEvent?): Boolean {
    Log.d("TestRecyclerView", "RecyclerView handle ${e?.actionStr()}[${e?.hashCode()}]")
    return super.onTouchEvent(e)
//    return false
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