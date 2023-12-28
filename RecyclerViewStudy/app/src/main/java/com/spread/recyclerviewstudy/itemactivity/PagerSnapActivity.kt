package com.spread.recyclerviewstudy.itemactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.R
import kotlin.math.min

class PagerSnapActivity : AppCompatActivity() {

  private val TAG = "PagerSnapActivity-Spread"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_pager_snap)
    val rootView = findViewById<ConstraintLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val recyclerView = findViewById<RecyclerView>(R.id.pager_snap_recyclerview)
    recyclerView.adapter = MyAdapter()
    recyclerView.setItemViewCacheSize(0)
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = object : LinearLayoutManager(this) {
      override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return min(height, width)
      }
    }.apply {
      isItemPrefetchEnabled = false
    }
    PagerSnapHelper().attachToRecyclerView(recyclerView)
  }

  /**
   * 老版的PagerSnapHelper，下刷一个，上刷两个。
   */
  private val myPageSnapHelper = object : PagerSnapHelper() {
    override fun findTargetSnapPosition(
      layoutManager: RecyclerView.LayoutManager,
      velocityX: Int,
      velocityY: Int
    ): Int {
      val itemCount = layoutManager.itemCount
      if (itemCount == 0) {
        return RecyclerView.NO_POSITION
      }
      val verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
      val horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
      val mStartMostChildView = if (layoutManager.canScrollVertically()) {
        findStartView(layoutManager, verticalHelper)
      } else {
        findStartView(layoutManager, horizontalHelper)
      }
      if (mStartMostChildView == null) {
        return RecyclerView.NO_POSITION
      }
      val centerPosition = layoutManager.getPosition(mStartMostChildView)
      if (centerPosition == RecyclerView.NO_POSITION) {
        return RecyclerView.NO_POSITION
      }
      val forwardDirection = if (layoutManager.canScrollHorizontally()) {
        velocityX > 0
      } else {
        velocityY > 0
      }
      var reverseLayout = false
      if (layoutManager is RecyclerView.SmoothScroller.ScrollVectorProvider) {
        val vectorProvider = layoutManager as RecyclerView.SmoothScroller.ScrollVectorProvider
        val vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1)
        if (vectorForEnd != null) {
          reverseLayout = vectorForEnd.x < 0 || vectorForEnd.y < 0
        }
      }
      return if (reverseLayout){
        if (forwardDirection) centerPosition - 1 else centerPosition
      } else {
        if (forwardDirection) centerPosition + 1 else centerPosition
      }
    }

    private fun findStartView(layoutManager: RecyclerView.LayoutManager, helper: OrientationHelper): View? {
      val childCount = layoutManager.childCount
      if (childCount == 0){
        return null
      }
      var closestChild: View? = null
      var startest = Int.MAX_VALUE
      for (i in 0 ..< childCount) {
        val child = layoutManager.getChildAt(i)
        val childStart = helper.getDecoratedStart(child)
        if (childStart < startest) {
          startest = childStart
          closestChild = child
        }
      }
      return closestChild
    }
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val nums = listOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false)
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = nums[position].toString()
        textSize = 100f
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(this@PagerSnapActivity, com.google.android.material.R.color.design_default_color_primary)
      }
      Log.d(TAG, "current bind: ${nums[position]}")
    }

    override fun onViewRecycled(holder: MyViewHolder) {
      super.onViewRecycled(holder)
      Log.d(TAG, "Recycle: ${holder.itemView.findViewById<TextView>(R.id.big_text_text).text}")
    }

    override fun getItemCount() = nums.size

  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}