package com.spread.recyclerviewstudy.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.preload.DetachOccasion
import com.spread.recyclerviewstudy.preload.IViewHolderVisibilityDispatcher
import com.spread.recyclerviewstudy.preload.ScrollOccasion
import com.spread.recyclerviewstudy.preload.ViewHolderVisibilityDispatcher
import com.spread.recyclerviewstudy.preload.WithDispatcher

class DispatcherRecyclerView : RecyclerView {

  var dispatcher: IViewHolderVisibilityDispatcher? = null

  constructor(context: Context) : super(context)

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

  override fun onScrolled(dx: Int, dy: Int) {
    super.onScrolled(dx, dy)
    dispatcher?.dispatchVisibility(ScrollOccasion)
  }

  override fun onChildDetachedFromWindow(child: View) {
    super.onChildDetachedFromWindow(child)
    dispatcher?.dispatchVisibility(DetachOccasion(child))
  }
}