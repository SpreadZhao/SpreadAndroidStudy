package com.example.customviewtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat


class TimeService : Service() {

  lateinit var window: TimeWindow

  private var pause = false

  private val TAG = "MainService"

  private val refreshTimeTask = object : Runnable {
    override fun run() {
      window.refreshTime()
      mHandler.postDelayed(this, 31)
    }
  }

  private val mHandler = Handler(Looper.getMainLooper())

  private val screenStateListener = object : ScreenListener.ScreenStateListener {
    override fun onScreenOn() {
      pause = false
      Log.d("MainService", "Screen on")
//            mHandler.post(refreshTimeTask)
    }

    override fun onScreenOf() {
      pause = true
      Log.d("MainService", "Screen off")
//            screenListener.unregisterListener()
    }

    override fun onUserPresent() {
      Log.d("MainService", "Lock up")
    }
  }

  private lateinit var screenListener: ScreenListener

  override fun onCreate() {
    super.onCreate()
    val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    val CHANNEL_ID = "my_channel_01"
    val channel = NotificationChannel(
      CHANNEL_ID,
      "Channel human readable title",
      NotificationManager.IMPORTANCE_DEFAULT
    )
    manager.createNotificationChannel(channel)
    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle("Listening time")
      .build()
    startForeground(1, notification)
    screenListener = ScreenListener(this)
    screenListener.setOnScreenListener(screenStateListener)
    window = TimeWindow(MainApplication.context).addOnStateListener { isOpen ->
      val intent = Intent("com.spread.customview.ACTION_TIME_WINDOW").apply {
        setPackage(packageName)
        putExtra("is_open", isOpen)
      }
      sendBroadcast(intent)
    }
    if (!window.isOpen) window.open()
    mHandler.post(refreshTimeTask)
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        intent?.let {
//            val open = it.getBooleanExtra("switch", false)
//            if (open) {
//                window.open()
//            } else {
//                window.close()
//            }
//        }
//
    if (intent != null) {
      if (intent.action == "ACTION_STOP_TIME") {
        stopSelf()
      }
    }
    if (!window.isOpen) window.open()
    Log.d("MainService", "onStartCommand")
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onDestroy() {
    if (window.isOpen) window.close()
    mHandler.removeCallbacks(refreshTimeTask)
    screenListener.unregisterListener()
    Log.e(TAG, "onDestroy")
    super.onDestroy()
  }

}