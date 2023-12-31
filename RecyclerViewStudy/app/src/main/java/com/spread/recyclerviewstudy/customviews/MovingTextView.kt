package com.spread.recyclerviewstudy.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.updateLayoutParams
import com.spread.recyclerviewstudy.R


class MovingTextView : AppCompatTextView {

  private var enableAnimator = false

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    initView(attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    initView(attrs)
  }

  private val mMoveTextRunnable = object : Runnable {
    override fun run() {
      updateLayoutParams<FrameLayout.LayoutParams> {
        this.gravity = getNextGravity(this.gravity)
      }
      requestLayout()
      postDelayed(this, 20)
    }
  }

  private fun initView(attrs: AttributeSet) {
    val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MovingTextView)
    enableAnimator = typedArray.getBoolean(R.styleable.MovingTextView_enable_animator, false)
    typedArray.recycle()
    if (enableAnimator) {
      postDelayed(mMoveTextRunnable, 500)
    }
  }

  private fun getNextGravity(currGravity: Int) = when (currGravity) {
    Gravity.TOP or Gravity.START -> Gravity.TOP or Gravity.END
    Gravity.TOP or Gravity.END -> Gravity.BOTTOM or Gravity.END
    Gravity.BOTTOM or Gravity.END -> Gravity.BOTTOM or Gravity.START
    else -> Gravity.TOP or Gravity.START
  }
}