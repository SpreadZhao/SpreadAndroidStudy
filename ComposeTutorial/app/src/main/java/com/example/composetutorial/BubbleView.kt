package com.example.composetutorial

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.PointFEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.animation.addListener
import kotlin.math.hypot

class BubbleView(context: Context) : View(context) {

    // 抗锯齿画笔
    private val mBubblePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
    }

    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)

//    private lateinit var mContext: Context
//    private lateinit var mAttrs: AttributeSet
    private var mBubbleMoveRadius = 0F
    private var mBubbleStillRadius = 0F
    private var mBubbleColor = Color.RED
    private var mTextStr = ""
    private var mTextSize = 10F.sp
    private var mTextColor = Color.WHITE
    private var mBubbleState = BUBBLE_DEFAULT
    private lateinit var mBubbleMoveCenter: PointF
    private lateinit var mBubbleStillCenter: PointF
    private val mTextRect = Rect()
    private val mBeiPath = Path()
    private var mDistance = 0F
    private var mMaxDistance = 0F


    companion object {
        // 气泡的四种状态
        private const val BUBBLE_DEFAULT = 0
        private const val BUBBLE_CONNECT = 1
        private const val BUBBLE_APART = 2
        private const val BUBBLE_DISMISS = 3
    }

//    constructor(context: Context) : super(context) {
//        mContext = context
//    }
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        mContext = context
//        mAttrs = attrs
//    }
//    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
//        mContext = context
//        mAttrs = attrs
//    }

    init {
        mBubbleMoveRadius = 20F
        mBubbleStillRadius = mBubbleMoveRadius
        mTextStr = "S"
        mTextPaint.apply {
            color = mTextColor
            textSize = mTextSize
        }
        mMaxDistance = 60 * mBubbleMoveRadius

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!::mBubbleMoveCenter.isInitialized) {
            mBubbleMoveCenter = PointF(w / 2F, h / 2F)
        } else {
            mBubbleMoveCenter.set(w / 2F, h / 2F)
        }
        if (!::mBubbleStillCenter.isInitialized) {
            mBubbleStillCenter = PointF(w / 2F, h / 2F)
        } else {
            mBubbleStillCenter.set(w / 2F, h / 2F)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBubbleState == BUBBLE_CONNECT) {
            canvas.drawCircle(
                mBubbleStillCenter.x,
                mBubbleStillCenter.y,
                mBubbleStillRadius,
                mBubblePaint
            )
            val cosTana = (mBubbleMoveCenter.x - mBubbleStillCenter.x) / mDistance
            val sinTana = (mBubbleMoveCenter.y - mBubbleStillCenter.y) / mDistance
            val mAStartX = mBubbleStillCenter.x - mBubbleStillRadius * sinTana
            val mAStartY = mBubbleStillCenter.y + mBubbleStillRadius * cosTana
            val mBEndX = mBubbleMoveCenter.x - mBubbleMoveRadius * sinTana
            val mBEndY = mBubbleMoveCenter.y + mBubbleMoveRadius * cosTana
            val mCStartX = mBubbleMoveCenter.x + mBubbleMoveRadius * sinTana
            val mCStartY = mBubbleMoveCenter.y - mBubbleMoveRadius * cosTana
            val mDEndX = mBubbleStillCenter.x + mBubbleStillRadius * sinTana
            val mDEndY = mBubbleStillCenter.y - mBubbleStillRadius * cosTana
            val mGCenterX = (mBubbleStillCenter.x + mBubbleMoveCenter.x) / 2
            val mGCenterY = (mBubbleStillCenter.y + mBubbleMoveCenter.y) / 2
            mBeiPath.reset()
            mBeiPath.moveTo(mAStartX, mAStartY)
            mBeiPath.quadTo(mGCenterX, mGCenterY, mBEndX, mBEndY)
            mBeiPath.lineTo(mCStartX, mCStartY)
            mBeiPath.quadTo(mGCenterX, mGCenterY, mDEndX, mDEndY)
            mBeiPath.close()
            canvas.drawPath(mBeiPath, mBubblePaint)
        }
        // 这一段一定要放在后面！保证先画圆，后画文字。这样文字才能显示在上面
        if (mBubbleState != BUBBLE_DISMISS) {
            mBubbleMoveCenter.let {
                canvas.drawCircle(it.x, it.y, mBubbleMoveRadius, mBubblePaint)
                mTextPaint.getTextBounds(mTextStr, 0, mTextStr.length, mTextRect)
                canvas.drawText(
                    mTextStr,
                    it.x - mTextRect.width() / 2,
                    it.y + mTextRect.height() / 2,
                    mTextPaint
                )
            }
        }
        if (mBubbleState == BUBBLE_APART) {

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                if (mDistance < mMaxDistance) {
                    mBubbleState = BUBBLE_CONNECT
                }
                performClick()
            }
            ACTION_MOVE ->  {
                mBubbleMoveCenter.x = event.x
                mBubbleMoveCenter.y = event.y
                mDistance = hypot(
                    x = event.x - mBubbleStillCenter.x,
                    y = event.y - mBubbleStillCenter.y
                )
                if (mBubbleState == BUBBLE_CONNECT) {
                    if (mDistance > mMaxDistance) {
                        mBubbleState = BUBBLE_APART
                    } else {
                        if (mBubbleStillRadius > 0){
                            mBubbleStillRadius = mBubbleMoveRadius - mDistance / 6
                            if (mBubbleStillRadius < 0) mBubbleStillRadius = 0F
                        }
                    }
                }
                invalidate()
            }
            ACTION_UP -> {
                if (mBubbleState == BUBBLE_CONNECT) {
                    startBubbleReset()
                } else if (mBubbleState == BUBBLE_APART) {
                    if (mDistance <= mMaxDistance) {
                        mBubbleState = BUBBLE_CONNECT
                        startBubbleReset()
                    }
                }
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun startBubbleReset() {
        ValueAnimator.ofObject(
            PointFEvaluator(),
            PointF(mBubbleMoveCenter.x, mBubbleMoveCenter.y),
            PointF(mBubbleStillCenter.x, mBubbleStillCenter.y)
        ).apply {
            duration = 500
            // 回弹效果
            interpolator = OvershootInterpolator(5F)
            addUpdateListener {
                mBubbleMoveCenter = animatedValue as PointF
                invalidate()
            }
            addListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    mBubbleStillRadius = 0F
                    invalidate()
                }

                override fun onAnimationEnd(animation: Animator) {
                    mBubbleStillRadius = mBubbleMoveRadius
                    invalidate()
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
        }.start()
    }

    private val Float.sp: Float
        get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, context.resources.displayMetrics)
}