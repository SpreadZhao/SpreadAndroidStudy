package com.spread.recyclerviewstudy.itemactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.spread.recyclerviewstudy.R

class BigItemRecyclerViewActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_big_item_recycler_view)
    val rootView = findViewById<FrameLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val recyclerView = findViewById<RecyclerView>(R.id.big_item_recyclerview)
    val adapter = MyAdapter()
    recyclerView.adapter = adapter
    recyclerView.setItemViewCacheSize(0)
    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.itemAnimator = null
    findViewById<Button>(R.id.delete_2).setOnClickListener {
      adapter.remove2()
    }
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val nums = mutableListOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 2892 / 2)
      }
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = nums[position].toString()
        textSize = 100f
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(this@BigItemRecyclerViewActivity, com.google.android.material.R.color.design_default_color_primary)
      }
    }

    override fun getItemCount() = nums.size

    fun remove2() {
      nums.removeAt(1)
      notifyItemRemoved(1)
    }

  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}