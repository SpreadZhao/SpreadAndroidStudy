package com.example.spreadshop.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.example.spreadshop.logic.model.UserInfo
import com.google.gson.Gson

object UserInfoDao {
    fun saveUserInfo(userInfo: UserInfo, context: Context){
        context.getSharedPreferences("uinfo", Context.MODE_PRIVATE).edit {
            putString("userinfo", Gson().toJson(userInfo))
        }
    }

    fun getSavedUserInfo(context: Context): UserInfo{
        val userInfoJson = context.getSharedPreferences("uinfo", Context.MODE_PRIVATE).getString("userinfo", "")
        return Gson().fromJson(userInfoJson, UserInfo::class.java)
    }

    fun isUserInfoSaved(context: Context): Boolean = context.getSharedPreferences("uinfo", Context.MODE_PRIVATE).contains("userinfo")

    fun clear(context: Context) = context.getSharedPreferences("uinfo", Context.MODE_PRIVATE).edit().clear().apply()
    // 书上是在Fragment里做的，那个时候Activity已经有了，所以不会报错
// 要是在Activity里做，就会产生lateinit property context has not been initialized
    //private fun sharedPreferences() = SpreadShopApplication.context.getSharedPreferences("uinfo", Context.MODE_PRIVATE)
}
