package com.example.frescodisplaydemo.utils

import android.content.Context

object ScreenUtil {
    fun getScreenWidth(context: Context) = context.resources.displayMetrics.widthPixels
    fun getScreenHeight(context: Context) = context.resources.displayMetrics.heightPixels
}