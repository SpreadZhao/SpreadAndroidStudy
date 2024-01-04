package com.spread.recyclerviewstudy.preload

/**
 * 一个有Dispatcher的RecyclerView, LayoutManger, Adapter等
 */
interface WithDispatcher {
  fun setDispatcher(dispatcher: IViewHolderVisibilityDispatcher)
  fun getDispatcher(): IViewHolderVisibilityDispatcher?
}