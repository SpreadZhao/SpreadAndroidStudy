package com.spread.recyclerviewstudy.preload

import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView实现这个接口，就能将
 */
interface IDisPatcherRecyclerView {
  val mInnerDispatcher: ViewHolderVisibilityDispatcher
}