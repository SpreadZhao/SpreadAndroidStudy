package com.spread.recyclerviewstudy.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.spread.recyclerviewstudy.recyclerview.actionStr

class TestTextView : AppCompatTextView {

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }

  override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    super.onLayout(changed, left, top, right, bottom)
  }

  override fun onTouchEvent(event: MotionEvent?): Boolean {
    Log.d("TestRecyclerView", "ViewItem handle ${event?.actionStr()}[${event?.hashCode()}]")

    return super.onTouchEvent(event)
  }
}