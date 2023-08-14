package com.example.spreadshop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spreadshop.logic.Repository
import com.example.spreadshop.logic.model.*
import retrofit2.Call

class MainViewModel: ViewModel() {

//    val goodsListLiveData: LiveData<List<Goods>>
//        get() = _goodsListLiveData
//    private val _goodsListLiveData = MutableLiveData<List<Goods>>()
    val goodsList = ArrayList<Goods>()
    val categoryList = ArrayList<Category>()

    var username = ""
    var balance = ""

    val isGotGoodsLiveData = MutableLiveData<Boolean>()
    val isGotCategoryLiveData = MutableLiveData<Boolean>()

    val goodsLiveData: LiveData<Call<GoodsResponse>>
        get() = _goodsLiveData
    private val _goodsLiveData = MutableLiveData<Call<GoodsResponse>>()

    val categoryLiveData: LiveData<Call<CategoryResponse>>
        get() = _categoryLiveData
    private val _categoryLiveData = MutableLiveData<Call<CategoryResponse>>()

    val balanceResponseLiveData: LiveData<Call<BalanceResponse>>
        get() = _balanceLiveData
    private val _balanceLiveData = MutableLiveData<Call<BalanceResponse>>()

    fun getGoods(cmd: String) {
        _goodsLiveData.value = Repository.getGoods(cmd)
    }

    fun getAllCategory(cmd: String){
        _categoryLiveData.value = Repository.getAllCategory(cmd)
    }

    fun getBalance(){
        _balanceLiveData.value = Repository.getBalance(username)
    }

//    fun initGoods(){
//        _goodsListLiveData.value = mutableListOf(Goods(0, "null", "null", 0, 0))
//    }
//
//    fun updateGoods(g: List<Goods>){
//        _goodsListLiveData.value = g
//    }

}