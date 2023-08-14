package com.example.customviewtest

import android.content.Context
import android.graphics.PixelFormat
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread

class TimeWindow(context: Context) : FrameLayout(context) {

//    private var mDownFocusX = 0F
//
//    private var mDownFocusY = 0F

    private var mLastFocusX = 0

    private var mLastFocusY = 0

    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val _rootView by lazy {
        layoutInflater.inflate(R.layout.window, null)
    }

    private val time by lazy {
        _rootView.findViewById<TextView>(R.id.time).apply {

        }
    }

    private val timeFormat = SimpleDateFormat("HH:mm:ss:SSS", Locale.CHINESE)

    var isOpen = false

    private val windowParams = WindowManager.LayoutParams(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT,
        0,
        0,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
        PixelFormat.TRANSLUCENT
    )

    private val currentDisplayMetrics: DisplayMetrics
        get() {
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)
            return dm
        }

    private val stateListeners = CopyOnWriteArrayList<(Boolean) -> Unit>()

    fun addOnStateListener(onState: (Boolean) -> Unit) = apply {
        if (!stateListeners.contains(onState)) {
            stateListeners.add(onState)
        }
    }

    private fun performOnState() {
        for (listener in stateListeners) {
            listener.invoke(isOpen)
        }
    }

    private fun updateState(newState: Boolean) {
        isOpen = newState
        performOnState()
    }


//    companion object {
//        fun registerForWindowState(window: TimeWindow, onState: (Boolean) -> Unit) {
//
//        }
//    }

//    private val refreshTask = object : Runnable {
//        override fun run() {
//            refreshTime()
//            postDelayed(this, 1000)
//        }
//    }
//
//    private val mHandler = Handler(Looper.getMainLooper())

    private fun calculateSizeAndPosition(params: WindowManager.LayoutParams) {
        val dm = currentDisplayMetrics
        params.gravity = Gravity.TOP or Gravity.START
//        params.x = (dm.widthPixels - params.width) / 2
//        params.y = (dm.widthPixels - params.height) / 2
    }

    private fun initWindowParams() = calculateSizeAndPosition(windowParams)

    fun refreshTime() {
        val currTime = timeFormat.format(Calendar.getInstance().time)
        time.post {
            time.text = currTime
        }
//        Log.d("TimeWindow", "refresh time: $currTime")
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d("TimeWindow", "dispatch, ev: $ev")
//        if (ev.action == MotionEvent.ACTION_DOWN) {
//            mLastFocusX = (ev.rawX + 0.5).toInt()
////            mDownFocusX = mLastFocusX.toFloat()
//            mLastFocusY = (ev.rawY + 0.5).toInt()
////            mDownFocusY = mLastFocusY.toFloat()
//        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("TimeWindow", "onTouch, ev: $event")
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val x = (event.rawX).toInt()
                val y = (event.rawY).toInt()
                val destX = x - mLastFocusX
                val destY = y - mLastFocusY
                windowParams.x += destX
                windowParams.y += destY
//                windowParams.x = x
//                windowParams.y = y
                windowManager.updateViewLayout(this, windowParams)
                mLastFocusX = x
                mLastFocusY = y
            }
            MotionEvent.ACTION_DOWN -> {
                mLastFocusX = (event.rawX).toInt()
                mLastFocusY = (event.rawY).toInt()
                performClick()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private fun setTimer() {
        val timer = Timer()
        thread {
            timer.schedule(
                object : TimerTask() {
                    override fun run() = refreshTime()
                },
                0,
                1000
            )
        }
    }

    init {
        initWindowParams()
        addView(_rootView)
//        setTimer()
//        mHandler.post(refreshTask)
    }

    @Synchronized
    fun open() {
        try {
            windowManager.addView(this, windowParams)
            updateState(true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Synchronized
    fun close() {
        try {
            windowManager.removeView(this)
            updateState(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}