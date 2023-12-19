package com.example.customviewtest.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.customviewtest.R

class PagerSnapActivity : AppCompatActivity() {

  private val TAG = "PagerSnapActivity-Spread-"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_pager_snap)
    val rootView = findViewById<ConstraintLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val recyclerView = findViewById<RecyclerView>(R.id.pager_snap_recyclerview)
    recyclerView.adapter = MyAdapter()
    recyclerView.layoutManager = object : LinearLayoutManager(this) {
      override fun calculateExtraLayoutSpace(
        state: RecyclerView.State,
        extraLayoutSpace: IntArray
      ) {
        extraLayoutSpace[1] = height
      }
    }
    PagerSnapHelper().attachToRecyclerView(recyclerView)
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val nums = listOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false)
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = nums[position].toString()
        textSize = 50f
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(this@PagerSnapActivity, com.google.android.material.R.color.design_default_color_primary)
      }
      Log.d(TAG, "current bind: ${nums[position]}")
    }

    override fun getItemCount() = nums.size

  }

  inner class MyViewHolder(itemView: View) : ViewHolder(itemView)
}