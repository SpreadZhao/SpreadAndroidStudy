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
    Log.d("TestRecyclerView", "requestLayout()")
    super.requestLayout()
  }

  override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
    return super.dispatchTouchEvent(ev)
  }

  override fun onTouchEvent(e: MotionEvent?): Boolean {
    return super.onTouchEvent(e)
//    return false
  }
}