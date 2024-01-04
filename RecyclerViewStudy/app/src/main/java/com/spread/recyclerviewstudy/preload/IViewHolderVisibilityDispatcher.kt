package com.spread.recyclerviewstudy.preload

import androidx.recyclerview.widget.RecyclerView

interface IViewHolderVisibilityDispatcher {
  fun dispatchVisibility(occasion: Occasion)
  fun bindRecyclerView(recyclerView: RecyclerView)
}
