package com.example.notificationlistener

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationListener : NotificationListenerService() {
    private val TAG = "NotificationListener"
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        Log.d("")
    }
}