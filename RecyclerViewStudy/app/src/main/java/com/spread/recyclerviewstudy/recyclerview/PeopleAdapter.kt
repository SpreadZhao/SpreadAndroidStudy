package com.spread.recyclerviewstudy.recyclerview

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutParams

class PeopleAdapter(private val context: Context, private val peopleList: MutableList<People>) :
  Adapter<PeopleAdapter.ViewHolder>() {

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView = if (itemView is ViewGroup) {
      itemView.getChildAt(0) as TextView
    } else {
      null
    }
    val ageView = if (itemView is ViewGroup) {
      itemView.getChildAt(1) as TextView
    } else {
      null
    }
  }

  // 创建RecyclerView中item对应的View的地方
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(generateItemView(context))
  }

  override fun getItemCount() = peopleList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val people = peopleList[position]
    holder.nameView?.text = people.name
    holder.ageView?.text = people.age.toString()
  }

  fun addItem(people: People) {
    peopleList.add(people)
    notifyItemInserted(peopleList.lastIndex)
  }
}

fun generateItemView(
  context: Context,
  name: String? = null,
  age: Int? = null,
  gender: Gender? = null
): LinearLayout {
  val set = name != null && age != null && gender != null
  val nameView = TextView(context).apply {
    layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    if (set) {
      text = name
    }
  }
  val ageView = TextView(context).apply {
    layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    if (set) {
      text = age.toString()
    }
  }
  return LinearLayout(context).apply {
    orientation = LinearLayout.HORIZONTAL
    layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    addView(nameView)
    addView(ageView)
  }
}