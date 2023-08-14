package com.example.marqueue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.hello)
        text.isSelected = true
        text.text = "赵传博吴宇航钟红文送瑞瑞李赟哈哈哈呵呵呵嘿嘿嘿你好谢谢再见"
    }
}