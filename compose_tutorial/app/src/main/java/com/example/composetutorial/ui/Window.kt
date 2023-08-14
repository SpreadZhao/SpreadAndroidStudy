package com.example.composetutorial.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.composetutorial.R
import java.lang.Exception

class Window(private val context: Context) {
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    @SuppressLint("InflateParams")
    private val rootView = layoutInflater.inflate(R.layout.window, null)
    private val windowParams = WindowManager.LayoutParams(
        0,
        0,
        0,
        0,
             WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )
    private fun getCurrentDisplayMetrics(): DisplayMetrics {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    private fun calculateSizeAndPosition(
        params: WindowManager.LayoutParams,
        widthInDp: Int,
        heightInDp: Int
    ) {
        val dm = getCurrentDisplayMetrics()
        params.gravity = Gravity.TOP or Gravity.START
        params.width = (widthInDp * dm.density).toInt()
        params.height = (heightInDp * dm.density).toInt()
        params.x = (dm.widthPixels - params.width) / 2
        params.y = (dm.widthPixels - params.height) / 2
    }

    private fun initWindowParams() {
        calculateSizeAndPosition(windowParams, 100, 30)
    }

    private fun initWindow() {

    }

    init {
        initWindowParams()
        initWindow()
    }

    fun open() {
        try {
            windowManager.addView(rootView, windowParams)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun close() {
        try {
            windowManager.removeView(rootView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}