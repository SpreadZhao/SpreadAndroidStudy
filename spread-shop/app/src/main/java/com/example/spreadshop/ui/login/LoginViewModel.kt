package com.example.spreadshop.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spreadshop.logic.Repository
import com.example.spreadshop.logic.model.LoginResult
import com.example.spreadshop.logic.model.RegisterResponse
import com.example.spreadshop.logic.model.UserInfo
import retrofit2.Call

class LoginViewMode: ViewModel() {

    val username: LiveData<String>
        get() = _username
    private val _username = MutableLiveData<String>()

    val password: LiveData<String>
        get() = _password
    private val _password = MutableLiveData<String>()

    val isSetFullyLiveData = MutableLiveData<Boolean>()

    val loginResult: LiveData<Call<LoginResult>>
        get() = _loginResult
    private val _loginResult = MutableLiveData<Call<LoginResult>>()

    val registerResponseLiveData: LiveData<Call<RegisterResponse>>
        get() = _registerResponse
    private val _registerResponse = MutableLiveData<Call<RegisterResponse>>()

    fun setUsername(uname: String){
        _username.value = uname
    }

    fun setPassword(passwd: String){
        _password.value = passwd
    }


    fun getLoginResult(username: String, password: String){
        _loginResult.value = Repository.getLoginResult(username, password)
    }

    fun getRegisterResponse(username: String, password: String){
        _registerResponse.value = Repository.getRegisterResponse(username, password)
    }

    fun saveUserInfo(context: Context){
        val userInfo = UserInfo(_username.value!!, _password.value!!)
        Repository.saveUserInfo(userInfo, context)
    }

    fun getSavedUserInfo(context: Context) = Repository.getSavedUserInfo(context)

    fun isUserInfoSaved(context: Context) = Repository.isUserInfoSaved(context)

    fun clear(context: Context) = Repository.clear(context)

}