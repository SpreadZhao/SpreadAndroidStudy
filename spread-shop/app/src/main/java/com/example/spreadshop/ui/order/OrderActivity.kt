package com.example.spreadshop.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spreadshop.R
import com.example.spreadshop.databinding.ActivityOrderBinding
import com.example.spreadshop.logic.model.MyOrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderActivity : AppCompatActivity() {

    private lateinit var bindingOrder: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingOrder = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(bindingOrder.root)

        setSupportActionBar(bindingOrder.orderToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.username = intent.getStringExtra("username").toString()
        if(orderViewModel.username != ""){
            Log.d("SpreadShopTest", "OrderActivity: getting My Order, username=${orderViewModel.username}")
            orderViewModel.getMyOrder()
        }else{
            Log.d("SpreadShopTest", "OrderActivity: get fail")
        }

        val orderLayoutManager = LinearLayoutManager(this)
        bindingOrder.orderRecycler.layoutManager = orderLayoutManager
        val orderAdapter = OrderAdapter(this, orderViewModel.orderList)
        bindingOrder.orderRecycler.adapter = orderAdapter

        orderViewModel.myOrderResponseLiveData.observe(this){
            it.enqueue(object : Callback<MyOrderResponse>{
                override fun onResponse(
                    call: Call<MyOrderResponse>,
                    response: Response<MyOrderResponse>
                ) {
                    val myOrderResponse = response.body()
                    if(myOrderResponse != null){
                        if(myOrderResponse.success){
                            val list = myOrderResponse.orderList
                            orderViewModel.orderList.clear()
                            orderViewModel.orderList.addAll(list)
                            orderViewModel.isGotOrderLiveData.value = true
//                            for (order in list){
//                                Log.d("SpreadShopTest", order.goods_name)
//                            }
                        }else{
                            Log.d("SpreadShopTest", "My Order fail")
                        }
                    }else{
                        Log.d("SpreadShopTest", "My OrderResponse is null")
                    }
                }

                override fun onFailure(call: Call<MyOrderResponse>, t: Throwable) {
                    Log.d("SpreadShopTest", "My Order on failure")
                    t.printStackTrace()
                }
            })
        }

        orderViewModel.isGotOrderLiveData.observe(this){
            if(it == true){
                Log.d("SpreadShopTest", "is got order live data true")
                runOnUiThread {
                    orderAdapter.notifyDataSetChanged()
                    bindingOrder.orderRefresh.isRefreshing = false
                }
            }
        }

        bindingOrder.orderRefresh.setOnRefreshListener {
            orderViewModel.getMyOrder()
        }

    }// end onCreate

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}