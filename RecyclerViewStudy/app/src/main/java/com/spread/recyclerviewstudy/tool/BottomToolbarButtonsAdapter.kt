package com.spread.recyclerviewstudy.tool

import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.itemactivity.BigItemRecyclerViewActivity

class BottomToolbarButtonsAdapter(
  private val mRecyclerView: RecyclerView,
  private val btns: List<ButtonItem>
) : RecyclerView.Adapter<ButtonViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
    val btn = Button(parent.context).apply {
      layoutParams = ViewGroup.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
      )
    }
    return ButtonViewHolder(btn)
  }

  override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
    holder.bind(btns[position])
  }

  override fun getItemCount(): Int {
    return btns.size
  }
}