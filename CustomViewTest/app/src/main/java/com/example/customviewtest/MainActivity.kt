package com.example.customviewtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

//    lateinit var window: TimeWindow

//    val testView: TextView by lazy {
//        findViewById(R.id.test_view)
//    }

    private lateinit var testView: TextView

    private val TAG = "MainActivity"

    private var windowRuning = false

    inner class TimeServiceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                windowRuning = it.getBooleanExtra("is_open", false)
                Log.d(TAG, "windowRuning: $windowRuning")
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(TAG, "onCreate")
        Log.e(TAG, "info: $this")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerReceiver(
            TimeServiceReceiver(),
            IntentFilter().apply {
                addAction("com.spread.customview.ACTION_TIME_WINDOW")
            },
            RECEIVER_NOT_EXPORTED
        )

        if (!Settings.canDrawOverlays(this)) {
//            startActivityForResult(intent, 0)
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == ComponentActivity.RESULT_OK &&
                    Settings.canDrawOverlays(this)) {
                    startService(
                        Intent(this, TimeService::class.java)
                    )
                }
            }.launch(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
            )
        } else {
            startForegroundService(
                Intent(this, TimeService::class.java)
            )
        }


//        if (Settings.canDrawOverlays(this)) {
//            startService(
//                Intent(this, MainService::class.java)
//            )
//        }

        val mt = findViewById<MarqueeText>(R.id.marquee)
        val ct = findViewById<EditText>(R.id.control_marquee)
        val submit = findViewById<Button>(R.id.submit)
        val wdc = findViewById<Button>(R.id.window_ctl)
        val startSecond = findViewById<Button>(R.id.start_activity)
        val startMe = findViewById<Button>(R.id.start_me)
        testView = findViewById(R.id.test_view)

//        window = TimeWindow(this)
//        val window = (applicationContext as MainApplication).window
//        if (!window.isOpen) window.open()


        submit.setOnClickListener {
            mt.text = ct.text.toString()
        }
        startSecond.setOnClickListener {
            startActivity(
                Intent(this, SecondActivity::class.java)
            )
        }
        startMe.setOnClickListener {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }
        wdc.setOnClickListener {
            if (windowRuning) {
                startService(
                    Intent(this, TimeService::class.java).apply {
                        action = "ACTION_STOP_TIME"
                    }
                )
            } else {
                startService(
                    Intent(this, TimeService::class.java)
                )
            }
        }
//        wdc.setOnClickListener {
//            if (window.isOpen) startService(Intent(this, MainService::class.java).apply {
//                putExtra("switch", true)
//            })
//            else window.open()
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (Settings.canDrawOverlays(this)) {
//            startForegroundService(
//                Intent(this, MainService::class.java)
//            )
//        }
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



    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            val width = testView.measuredWidth
            val height = testView.measuredHeight
            Log.v(TAG, "width: $width")
            Log.v(TAG, "height: $height")
        }
    }

//    override fun onStart() {
//        super.onStart()
//        val observer = testView.viewTreeObserver
//        observer.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
////                observer.removeGlobalOnLayoutListener(this)
//                observer.removeOnGlobalLayoutListener(this)
//                val width = testView.measuredWidth
//                val height = testView.measuredHeight
//            }
//        })
//    }

    override fun onRestart() {
        Log.e(TAG, "onRestart")
        super.onRestart()
    }

    override fun onDestroy() {
//        if (window.isOpen) window.close()
//        startService(
//            Intent(this, MainService::class.java).apply {
//                action = "ACTION_STOP_TIME"
//            }
//        )
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }
}