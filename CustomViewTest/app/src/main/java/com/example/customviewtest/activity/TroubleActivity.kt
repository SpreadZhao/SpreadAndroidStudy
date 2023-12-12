package com.example.customviewtest.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.customviewtest.R

class TroubleActivity : AppCompatActivity() {

  private lateinit var text: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_trouble)
    val rootView = findViewById<ConstraintLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    text = findViewById(R.id.max_two_lines_text)
    text.text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
    val alignBtn = findViewById<Button>(R.id.align_btn)
    val view1 = findViewById<View>(R.id.view1)
    val view2 = findViewById<View>(R.id.view2)
    alignBtn.setOnClickListener {
      val constraintSet = ConstraintSet()
      constraintSet.clone(this, R.layout.activity_trouble)
      constraintSet.connect(view1.id, ConstraintSet.TOP, view2.id, ConstraintSet.TOP)
      constraintSet.connect(view1.id, ConstraintSet.BOTTOM, view2.id, ConstraintSet.BOTTOM)
      constraintSet.applyTo(rootView)
    }
  }
}