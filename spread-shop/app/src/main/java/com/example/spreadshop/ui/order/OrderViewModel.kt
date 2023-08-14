package com.example.spreadshop.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spreadshop.logic.Repository
import com.example.spreadshop.logic.model.Bag
import com.example.spreadshop.logic.model.MyOrderResponse
import retrofit2.Call

class OrderViewModel: ViewModel() {
    val myOrderResponseLiveData: LiveData<Call<MyOrderResponse>>
        get() = _myOrderResponse
    private val _myOrderResponse = MutableLiveData<Call<MyOrderResponse>>()

    var username = ""

    val orderList = ArrayList<Bag>()

    val isGotOrderLiveData = MutableLiveData<Boolean>()

    fun getMyOrder(){
        _myOrderResponse.value = Repository.getMyOrder(username)
    }

}