package com.example.customviewtest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class ScreenListener(
    private var mContext: Context
) {

    // lateinit is redundant
    private lateinit var mScreenReceiver: ScreenBroadcastReceiver
    private lateinit var mScreenStateListener: ScreenStateListener

    init {
        mScreenReceiver = ScreenBroadcastReceiver()
    }

    // inner去掉，找不到mScreenStateListener
    inner class ScreenBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent?.action
            if (action != null) {
                when (action) {
                    Intent.ACTION_SCREEN_ON -> mScreenStateListener.onScreenOn()
                    Intent.ACTION_SCREEN_OFF -> mScreenStateListener.onScreenOf()
                    Intent.ACTION_USER_PRESENT -> mScreenStateListener.onUserPresent()
                }
            }

        }

    }

    fun unregisterListener() {
        mContext.unregisterReceiver(mScreenReceiver)
    }

    fun setOnScreenListener(listener: ScreenStateListener) {
        mScreenStateListener = listener
        IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
            addAction(Intent.ACTION_USER_PRESENT)
            mContext.registerReceiver(mScreenReceiver, this)
        }
    }

    interface ScreenStateListener {
        fun onScreenOn()
        fun onScreenOf()
        fun onUserPresent()
    }
}