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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.customviewtest.R

class ViewPager2Activity : AppCompatActivity() {

  private val TAG = "ViewPager2Activity-Spread"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_view_pager2)
    val rootView = findViewById<ConstraintLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val pager2 = findViewById<ViewPager2>(R.id.pager2)
    pager2.adapter = MyAdapter2()
    pager2.offscreenPageLimit = 1
  }

  inner class MyAdapter2 : RecyclerView.Adapter<MyViewHolder>() {

    private val nums = listOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false)
      return MyViewHolder(view)
    }

    override fun getItemCount(): Int = nums.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = nums[position].toString()
        textSize = 100f
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(this@ViewPager2Activity, com.google.android.material.R.color.design_default_color_primary)
      }
      Log.d(TAG, "current bind: ${nums[position]}")
    }

  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}