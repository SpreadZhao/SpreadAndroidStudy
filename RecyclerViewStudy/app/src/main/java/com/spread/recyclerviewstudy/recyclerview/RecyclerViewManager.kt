package com.spread.recyclerviewstudy.recyclerview

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.R

class RecyclerViewManager(private val activity: Activity) {
  private var age = 1

  private val peopleList = mutableListOf(
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male),
    People("haha", age++, Female),
    People("sdfsdf", age++, Male),
    People("spread", age++, Male)
  )

  private lateinit var recyclerView: RecyclerView

  private val peopleAdapter = PeopleAdapter(activity, peopleList)


  fun initRecyclerView() {
    // Get the root view from the content view
    val rootView = activity.findViewById<ViewGroup>(R.id.root_layout)

    // Create and configure RecyclerView
    recyclerView = RecyclerView(activity).apply {
      layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        0,
        1f
      )
      layoutManager = LinearLayoutManager(activity)
      adapter = peopleAdapter
      visibility = View.VISIBLE
//      itemAnimator = null
    }

    // Add RecyclerView to the root view
    rootView.addView(recyclerView)
  }

  val recyclerViewInitialized: Boolean
    get() = ::recyclerView.isInitialized

  fun addPeople(people: People) {
    peopleList.add(people)
    peopleAdapter.notifyItemInserted(peopleList.lastIndex)
  }

  fun changePeople(index: Int, people: People) {
    peopleList[index] = people
    peopleAdapter.notifyItemChanged(index)
  }

  fun reversePeople() {
    peopleList.reverse()
    peopleAdapter.notifyDataSetChanged()
  }
}