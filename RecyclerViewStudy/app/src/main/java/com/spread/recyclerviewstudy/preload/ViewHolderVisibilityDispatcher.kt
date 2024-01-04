package com.spread.recyclerviewstudy.preload

import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.spread.recyclerviewstudy.R

class ViewHolderVisibilityDispatcher private constructor(): IViewHolderVisibilityDispatcher {

  private lateinit var mRecyclerView: RecyclerView

  private val TAG = "Dispatcher-Spread"

  object Builder {

    private val dispatcher = ViewHolderVisibilityDispatcher()

    fun build(): ViewHolderVisibilityDispatcher {
      return dispatcher
    }

    fun bindRecyclerView(recyclerView: RecyclerView) = this.apply {
      dispatcher.bindRecyclerView(recyclerView)
    }

    fun setListener(listener: VisibilityListener) = this.apply {
      dispatcher.setListener(listener)
    }

  }

  interface VisibilityListener {
    fun onViewHolderVisible(holder: ViewHolder)
    fun onViewHolderInvisible(holder: ViewHolder)
  }

  private var mVerticalHelper: OrientationHelper? = null
  private var mHorizontalHelper: OrientationHelper? = null
  private var mListener: VisibilityListener? = null

  private fun setListener(listener: VisibilityListener) {
    if (mListener == null) {
      mListener = listener
    }
  }

  private fun isChildVisibleVertical(itemView: View): Boolean {
    val verticalHelper = getVerticalHelper(mRecyclerView.layoutManager) ?: return false
    val itemStart = verticalHelper.getDecoratedStart(itemView)
    val itemEnd = verticalHelper.getDecoratedEnd(itemView)
    return !(itemEnd <= 0 || itemStart >= mRecyclerView.height)
  }

  private fun handleAttachedVHVisibility() {
    for (i in 0..<mRecyclerView.childCount) {
      val child = mRecyclerView.getChildAt(i)
      val oldVisible = child.isVisibleByTag
      val newVisible = isChildVisibleVertical(child)
      if (oldVisible != newVisible) {
        child.isVisibleByTag = newVisible
        mListener?.let {
          val holder = child.viewHolder ?: return@let
//          Log.d(TAG, "Scroll[${holder.adapterPosition + 1}] visible: $newVisible")
          if (newVisible) {
            it.onViewHolderVisible(holder)
          } else {
            it.onViewHolderInvisible(holder)
          }
        }
      }
    }
  }

  private fun handleChildVisibility(holder: ViewHolder) {
    handleChildVisibility(holder.itemView)
  }

  private fun handleChildVisibility(itemView: View, fromDetach: Boolean = false) {
    val oldVisible = itemView.isVisibleByTag
    if (fromDetach && oldVisible) {
      mListener?.let {
        itemView.isVisibleByTag = false
        val holder = itemView.viewHolder ?: return@let
//        Log.d(TAG, "Detach[${holder.adapterPosition + 1}] oldVisible: $oldVisible")
        it.onViewHolderInvisible(holder)
      }
      return
    } else if (fromDetach) {
      return
    }
    val newVisible = isChildVisibleVertical(itemView)
    itemView.isVisibleByTag = newVisible
    mListener?.let {
      val holder = itemView.viewHolder ?: return@let
      if (newVisible) {
        it.onViewHolderVisible(holder)
      } else {
        it.onViewHolderInvisible(holder)
      }
    }
  }

  private val View.viewHolder: ViewHolder?
    get() = mRecyclerView.getChildViewHolder(this)

  private var View.isVisibleByTag: Boolean
    get() = getTag(R.id.tag_view_holder_visibility) == true
    set(value) {
      setTag(R.id.tag_view_holder_visibility, value)
    }

  private fun getVerticalHelper(layoutManger: RecyclerView.LayoutManager?): OrientationHelper? {
    if (mVerticalHelper == null || mVerticalHelper?.layoutManager !== layoutManger) {
      mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManger)
    }
    return mVerticalHelper
  }

  override fun dispatchVisibility(occasion: Occasion) {
    when (occasion) {
      is ScrollOccasion -> handleAttachedVHVisibility()
      is DetachOccasion -> handleChildVisibility(occasion.itemView, true)
    }
  }

  override fun bindRecyclerView(recyclerView: RecyclerView) {
    mRecyclerView = recyclerView
  }
}