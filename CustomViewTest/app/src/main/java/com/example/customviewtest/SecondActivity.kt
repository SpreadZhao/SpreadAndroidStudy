package com.example.customviewtest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class SecondActivity : AppCompatActivity() {
    private val TAG = "SecondActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG, "onCreate")
        Log.e(TAG, "info: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val startMain = findViewById<Button>(R.id.start_main)
        startMain.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
    }

    override fun onStart() {
        Log.e(TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.e(TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.e(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        Log.e(TAG, "onRestart")
        super.onRestart()
    }

}