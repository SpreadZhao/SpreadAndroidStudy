package com.spread.recyclerviewstudy.tool

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ButtonViewHolder(private val buttonView: Button) : RecyclerView.ViewHolder(buttonView) {
  fun bind(item: ButtonItem) {
    buttonView.text = item.name
    buttonView.setOnClickListener(item.onClick)
  }
}