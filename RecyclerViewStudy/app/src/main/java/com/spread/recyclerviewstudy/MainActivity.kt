package com.spread.recyclerviewstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.spread.recyclerviewstudy.recyclerview.Female
import com.spread.recyclerviewstudy.recyclerview.Gender
import com.spread.recyclerviewstudy.recyclerview.Male
import com.spread.recyclerviewstudy.recyclerview.People
import com.spread.recyclerviewstudy.recyclerview.PeopleAdapter

class MainActivity : AppCompatActivity() {
    private var age = 1
    private val peopleList = listOf(
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
        People("sdfsdf", age++, Male)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the correct layout for setContentView
        setContentView(R.layout.activity_main)

        // Get the root view from the content view
        val rootView = findViewById<ViewGroup>(R.id.root_layout)

        // Create and configure RecyclerView
        val recyclerView = RecyclerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PeopleAdapter(this@MainActivity, peopleList)
            visibility = View.VISIBLE
        }

        // Add RecyclerView to the root view
        rootView.addView(recyclerView)
    }
}