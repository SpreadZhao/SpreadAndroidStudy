package com.example.frescodisplaydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var showProgressBarBtn: Button
    private lateinit var circleCornerBtn: Button
    private lateinit var multipleScaleBtn: Button
    private lateinit var gifBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findButtons()
        initViews()
    }

    private fun findButtons() {
        showProgressBarBtn = findViewById(R.id.progress_bar_btn)
        circleCornerBtn = findViewById(R.id.circle_corner_btn)
        multipleScaleBtn = findViewById(R.id.multiple_scale_type_btn)
        gifBtn = findViewById(R.id.gif)
    }

    private fun initViews() {
        showProgressBarBtn.setOnClickListener { naviToActivity<ShowProgressBarActivity>() }
        circleCornerBtn.setOnClickListener { naviToActivity<CircleAndCornerActivity>() }
        multipleScaleBtn.setOnClickListener { naviToActivity<MultipleScaleTypeActivity>() }
        gifBtn.setOnClickListener { naviToActivity<GifActivity>() }
    }

    private inline fun <reified T> naviToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}