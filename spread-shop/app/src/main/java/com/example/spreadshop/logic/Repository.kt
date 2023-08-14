package com.example.spreadshop.logic

import android.content.Context
import com.example.spreadshop.logic.dao.UserInfoDao
import com.example.spreadshop.logic.model.UserInfo
import com.example.spreadshop.logic.network.SpreadShopNetwork


object Repository {
    // network
    fun getLoginResult(username: String, password: String) = SpreadShopNetwork.getLoginResult(username, password)
    fun getGoods(cmd: String) = SpreadShopNetwork.getGoods(cmd)
    fun getAllCategory(cmd: String) = SpreadShopNetwork.getAllCategory(cmd)
    fun requestOrder(username: String, goods_id: Int, number: Int) = SpreadShopNetwork.requestOrder(username, goods_id, number)
    fun getRegisterResponse(username: String, password: String) = SpreadShopNetwork.getRegisterResponse(username, password)
    fun getMyOrder(username: String) = SpreadShopNetwork.getMyOrder(username)
    fun getBalance(username: String) = SpreadShopNetwork.getBalance(username)

    // dao
    fun saveUserInfo(userInfo: UserInfo, context: Context) = UserInfoDao.saveUserInfo(userInfo, context)
    fun getSavedUserInfo(context: Context) = UserInfoDao.getSavedUserInfo(context)
    fun isUserInfoSaved(context: Context) = UserInfoDao.isUserInfoSaved(context)
    fun clear(context: Context) = UserInfoDao.clear(context)

}