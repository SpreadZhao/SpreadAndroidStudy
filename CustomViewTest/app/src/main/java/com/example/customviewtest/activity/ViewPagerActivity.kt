package com.example.customviewtest.activity

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.Alignment
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
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.customviewtest.R

class ViewPagerActivity : AppCompatActivity() {

  private val views = ArrayList<View>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_view_pager)
    val rootView = findViewById<ConstraintLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    addViews()
    val viewPager = findViewById<ViewPager>(R.id.pager)
    viewPager.adapter = MyAdapter()
  }

  private fun addViews() {
    repeat(10) {
      val bigText = LayoutInflater.from(this).inflate(R.layout.big_text, null)
      bigText.findViewById<TextView>(R.id.big_text_text).apply {
        text = "${it + 1}"
        textSize = 50f
        gravity = Gravity.CENTER
        background = ContextCompat.getDrawable(this@ViewPagerActivity, com.google.android.material.R.color.design_default_color_primary)
      }

      views.add(bigText)
    }
  }


  inner class MyAdapter : PagerAdapter() {

    override fun getCount() = views.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
      container.addView(views[position])
      return views[position]
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
      return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
      container.removeView(`object` as View)
    }
  }
}