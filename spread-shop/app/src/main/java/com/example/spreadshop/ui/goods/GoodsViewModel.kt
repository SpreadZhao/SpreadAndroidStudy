package com.example.spreadshop.ui.goods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spreadshop.logic.Repository
import com.example.spreadshop.logic.model.OrderResponse
import retrofit2.Call

class GoodsViewModel: ViewModel() {
    val goodsNameLiveData = MutableLiveData<String>()
    val goodsStorageLiveData = MutableLiveData<Int>()
    val goodsPriceLiveData = MutableLiveData<Int>()
    val goodsCategoryLiveData = MutableLiveData<String>()
    val goodsIdLiveData = MutableLiveData<Int>()

    var username = ""

    val orderLiveData: LiveData<Call<OrderResponse>>
        get() = _orderLiveData
    private val _orderLiveData = MutableLiveData<Call<OrderResponse>>()

    val isSetFullyLiveData = MutableLiveData<Boolean>()

//    init {
//        goodsName = intent.getStringExtra("goods_name") ?: "null"
//        goodsStorage = intent.getStringExtra("goods_storage") ?: 0
//        goodsPrice = intent.getStringExtra("goods_price") ?: 9999
//        goodsCategory = intent.getStringExtra("goods_category") ?: "null"
//    }

    fun setGoodsName(n: String){
        goodsNameLiveData.value = n
    }

    fun setGoodsStorage(s: Int){
        goodsStorageLiveData.value = s
    }

    fun setGoodsPrice(p: Int){
        goodsPriceLiveData.value = p
    }

    fun setGoodsCategory(c: String){
        goodsCategoryLiveData.value = c
    }

    fun setGoodsId(i: Int){
        goodsIdLiveData.value = i
    }

    fun requestOrder(username: String, goods_id: Int, number: Int){
        _orderLiveData.value = Repository.requestOrder(username, goods_id, number)
    }

}