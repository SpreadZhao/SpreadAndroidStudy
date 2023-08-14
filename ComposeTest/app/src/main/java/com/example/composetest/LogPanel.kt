package com.example.composetest

import android.util.Log
import androidx.compose.runtime.Composable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception

@Composable
fun LogPanel() {
}

object Logger {
    private var mLogThread: LogThread? = null
    private const val TAG = "Logger"
    @Synchronized
    fun onGetMessage(listener: LogMsgListener) = apply {
        if (mLogThread != null) {
            try {
                removeThread()
            } catch (e: InterruptedException) {
                Log.e(TAG, "Interrupted exception: $e")
            }
        }
        mLogThread = LogThread(listener)
    }

    @Synchronized
    fun startLog() {
        mLogThread?.start()
    }

    @Synchronized
    fun onClearLog(listener: OnClearListener) {
        mLogThread?.let {
            try {
                removeThread()
            } catch (e: InterruptedException) {
                Log.e(TAG, "Interrupted exception: $e")
            }
        }
        mLogThread = LogThread().setClearFlag(true)
        startLog()
        listener.invoke()
    }

    @Synchronized
    fun removeThread() {
        mLogThread?.process?.destroy()
        mLogThread?.exit = true
        mLogThread?.interrupt()
        mLogThread?.join()
    }
}

class LogThread(private val listener: LogMsgListener? = null) : Thread() {
    var process: Process? = null
    private val tag = "LogThread"
    @Volatile
    var exit = false
    var clear = false
    val filter = "TAG"
    fun setClearFlag(ifClear: Boolean) = apply {
        clear = ifClear
    }

    override fun run() {
        try {
            val cmd = if (clear) {
                " -c "
            } else {
                " -s $filter"
            }
            process = Runtime.getRuntime().exec(
                "logcat $cmd"
            )
            val br = BufferedReader(InputStreamReader(process?.inputStream))
            var msg: String?
            try {
                while (process?.isAlive == true && !exit && !currentThread().isInterrupted) {
                    msg = br.readLine()
                    if (msg == null) break
                    // 处理日志的信息，如二次过滤
                    listener?.invoke(msg)
                }
            } catch (e: IOException) {
                Log.e(tag, "IOException: $e")
            }
        } catch (e: Exception) {
            Log.e(tag, "Exception: $e")
        } finally {
            Log.d(tag, "Log thread finally exit")
        }
    }
}

typealias LogMsgListener = (String) -> Unit
typealias OnClearListener = () -> Unit