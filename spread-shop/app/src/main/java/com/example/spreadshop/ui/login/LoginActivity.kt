package com.example.spreadshop.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.spreadshop.MainActivity
import com.example.spreadshop.databinding.ActivityLoginBinding
import com.example.spreadshop.logic.model.LoginResult
import com.example.spreadshop.logic.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding
    private lateinit var loginViewMode: LoginViewMode

    private lateinit var inputMethodManager: InputMethodManager
//    private lateinit var username: String
//    private lateinit var password: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

//        loginViewMode = ViewModelProvider(this).get(LoginViewMode::class.java)
        loginViewMode = ViewModelProvider(this)[LoginViewMode::class.java]
        //loginViewMode.isSetFullyLiveData.value = false

        if(loginViewMode.isUserInfoSaved(this)){
            val uinfo = loginViewMode.getSavedUserInfo(this)
            // 给输入框设置值要用setText不能用语法糖
            bindingLogin.accountEdit.setText(uinfo.username)
            bindingLogin.passwordEdit.setText(uinfo.password)
            bindingLogin.rememberPwd.isChecked = true
        }

        bindingLogin.loginBtn.setOnClickListener {

            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//            username = bindingLogin.accountEdit.text.toString()
//            password = bindingLogin.passwordEdit.text.toString()
            loginViewMode.setUsername(bindingLogin.accountEdit.text.toString())
            loginViewMode.setPassword(bindingLogin.passwordEdit.text.toString())


            val username = loginViewMode.username.value
            val password = loginViewMode.password.value

            if(username != "" && password != ""){
                Log.d("SpreadShopTest", "username: $username")
                Log.d("SpreadShopTest", "password: $password")
                //Smart cast to 'String' is impossible, because 'loginViewMode.username.value' is a complex expression
//                loginResultService.getLoginResult(username!!, password!!).enqueue(object: Callback<LoginResult> {
//                    override fun onResponse(
//                        call: Call<LoginResult>,
//                        response: Response<LoginResult>
//                    ) {
//                        Log.d("SpreadShopTest", "on Response")
//                        val loginResult = response.body()
//                        if(loginResult != null){
//                            Log.d("SpreadShopTest", "loginResult.message: ${loginResult.message}")
//                            if(loginResult.success){
//                                Log.d("SpreadShopTest", "Login Success")
//                            }else{
//                                Log.d("SpreadShopTest", "Login Fail")
//                            }
//                        }else{
//                            Log.d("SpreadShopTest", "LoginResult is Null")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<LoginResult>, t: Throwable) {
//                        Log.d("SpreadShopTest", "on Failure")
//                        t.printStackTrace()
//                    }
//                })

//                Repository.getLoginResult(username!!, password!!)
                loginViewMode.getLoginResult(username!!, password!!)

            }else{
                Log.d("SpreadShopTest", "usernameEmpty: ${username == ""}")
                Log.d("SpreadShopTest", "passwordEmpty: ${password == ""}")
            }

        }// end bindingLogin.loginBtn.setOnClickListener

        bindingLogin.registerBtn.setOnClickListener {
            val username = bindingLogin.accountEdit.text.toString()
            val password = bindingLogin.passwordEdit.text.toString()
            loginViewMode.getRegisterResponse(username, password)
        }

        bindingLogin.testBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", "root")
            startActivity(intent)
        }


        loginViewMode.loginResult.observe(this){
            it.enqueue(object: Callback<LoginResult>{
                override fun onResponse(
                    call: Call<LoginResult>,
                    response: Response<LoginResult>
                ) {
                    Log.d("SpreadShopTest", "on Response")
                    val loginResult = response.body()
                    if(loginResult != null){
                        Log.d("SpreadShopTest", "loginResult.message: ${loginResult.message}")
                        if(loginResult.success){
                            Log.d("SpreadShopTest", "Login Success")

//                            val editor = prefs.edit()
//                            if(bindingLogin.rememberPwd.isChecked){
//                                editor.putBoolean("remembered", true)
//                                editor.putString("username", loginViewMode.username.value)
//                                editor.putString("password", loginViewMode.password.value)
//                            }else{
//                                editor.clear()
//                            }
//                            editor.apply()
                            if(loginViewMode.username.value != "" && loginViewMode.password.value != ""){
                                loginViewMode.isSetFullyLiveData.value = true
                            }

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("username", loginResult.username)
                            startActivity(intent)
                        }else{
                            Log.d("SpreadShopTest", "Login Fail")
                                bindingLogin.loginInfoText.text = loginResult.message
//                                bindingLogin.loginInfoText.text = "Spread Shop"

                        }
                    }else{
                        Log.d("SpreadShopTest", "LoginResult is Null")
                    }
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.d("SpreadShopTest", "on Failure")
                    bindingLogin.loginInfoText.text = "Over Time"
                    t.printStackTrace()
                }
            })
        }

        loginViewMode.registerResponseLiveData.observe(this){
            it.enqueue(object : Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val regResponse = response.body()
                    if(regResponse != null) {
                        bindingLogin.loginInfoText.text = regResponse.register.message
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    t.printStackTrace()
                    bindingLogin.loginInfoText.text = "Register Over Time"
                }
            })
        }

        loginViewMode.isSetFullyLiveData.observe(this){
            if(it == true && bindingLogin.rememberPwd.isChecked){
                loginViewMode.saveUserInfo(this)
            }else{
                loginViewMode.clear(this)
            }
        }

    }// end onCreate


}