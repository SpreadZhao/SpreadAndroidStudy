package com.example.frescodisplaydemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var showProgressBarBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showProgressBarBtn = findViewById(R.id.progress_bar_btn)
        initViews()
    }

    private fun initViews() {
        showProgressBarBtn.setOnClickListener { naviToActivity<ShowProgressBarActivity>() }
    }

    private inline fun <reified T> naviToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}