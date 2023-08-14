package com.example.spreadshop.ui.goods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spreadshop.MainActivity
import com.example.spreadshop.SpreadShopApplication
import com.example.spreadshop.databinding.ActivityGoodsBinding
import com.example.spreadshop.logic.model.Command
import com.example.spreadshop.logic.model.OrderResponse
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.lxj.xpopup.interfaces.OnInputConfirmListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoodsActivity : AppCompatActivity() {

    private lateinit var bindingGoods: ActivityGoodsBinding
    private lateinit var goodsViewModel: GoodsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingGoods = ActivityGoodsBinding.inflate(layoutInflater)
        setContentView(bindingGoods.root)

//        val goodsName = intent.getStringExtra("goods_name") ?: "null"
//        val goodsStorage = intent.getStringExtra("goods_storage") ?: 0
//        val goodsPrice = intent.getStringExtra("goods_price") ?: 9999
//        val goodsCategory = intent.getStringExtra("goods_category") ?: "null"
        goodsViewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)

        goodsViewModel.setGoodsName(intent.getStringExtra("goods_name") ?: "null")
        goodsViewModel.setGoodsCategory(intent.getStringExtra("goods_category") ?: "null")
        goodsViewModel.setGoodsPrice(intent.getIntExtra("goods_price", 9999))
        goodsViewModel.setGoodsStorage(intent.getIntExtra("goods_storage", -1))
        goodsViewModel.setGoodsId(intent.getIntExtra("goods_id", -1))
        goodsViewModel.username = intent.getStringExtra("username") ?: ""
        goodsViewModel.isSetFullyLiveData.value = true


        setSupportActionBar(bindingGoods.toolbarDetail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        goodsViewModel.isSetFullyLiveData.observe(this){
            if(it == true){
                bindingGoods.collapsingToolbar.title = goodsViewModel.goodsNameLiveData.value
                val imgId = "ic_goods_${goodsViewModel.goodsIdLiveData.value}"
                Glide.with(this).load(Command.getImageByReflect(imgId)).into(bindingGoods.goodsImageDetail)
                bindingGoods.goodsTextDetail.text = generateGoodsDetail()
            }else{
                Log.d("SpreadShopTest", "isSetFullyLiveData: false")
            }
        }

        bindingGoods.buyBtn.setOnClickListener {
//            XPopup.Builder(this@GoodsActivity).asConfirm("title", "content", object: OnConfirmListener{
//                override fun onConfirm() {
//                    Toast.makeText(this@GoodsActivity, "test", Toast.LENGTH_SHORT).show()
//                }
//            }).show()
            XPopup.Builder(this@GoodsActivity).asInputConfirm("Buying ${goodsViewModel.goodsNameLiveData.value}", "Please enter the purchase quantity: ", "1", object: OnInputConfirmListener{
                override fun onConfirm(text: String?) {
                    if(goodsViewModel.isSetFullyLiveData.value == true && text != null && text != "" && text.isDigitsOnly()){
                        goodsViewModel.requestOrder(goodsViewModel.username, goodsViewModel.goodsIdLiveData.value!!, text.toInt())
                    }else if(text == ""){
                        goodsViewModel.requestOrder(goodsViewModel.username, goodsViewModel.goodsIdLiveData.value!!, 1)
                        Log.d("SpreadShopTest", "Only Buy One!")
                    }else{
                        Log.d("SpreadShopTest", "XPopInput return Nothing, buy fail")
                        return
                    }
//                    XPopup.Builder(this@GoodsActivity).asConfirm("Order Info", "Buy Success!!!") {
//                        val mainActivity = SpreadShopApplication.context as MainActivity
//                        mainActivity.mainViewModel.getGoods(Command.GET_RECOMMAND)
//                        this@GoodsActivity.finish()
//                    }.show()

                }
            }).show()
        }

        goodsViewModel.orderLiveData.observe(this){
            it.enqueue(object : Callback<OrderResponse>{
                override fun onResponse(
                    call: Call<OrderResponse>,
                    response: Response<OrderResponse>
                ) {
                    val orderResponse = response.body()
                    if(orderResponse != null){
                        val order = orderResponse.order
                        if(orderResponse.success){
                            //Toast.makeText(this@GoodsActivity, order.message, Toast.LENGTH_SHORT).show()
                            XPopup.Builder(this@GoodsActivity).asConfirm("Order Info", order.message) {
                                val mainActivity = SpreadShopApplication.context as MainActivity
                                mainActivity.mainViewModel.getGoods(Command.GET_RECOMMAND)
                                this@GoodsActivity.finish()
                            }.show()
                        }else{
                            Log.d("SpreadShopTest", "orderResponse.success is fail, msg: ${order.message}")
                            XPopup.Builder(this@GoodsActivity).asConfirm("Failed!!!", order.message
                            ) {
                                Toast.makeText(
                                    this@GoodsActivity,
                                    "buy failed over",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.show()
                        }
                    }else{
                        Log.d("SpreadShopTest", "orderResponse is null")
                    }
                }

                override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("SpreadShopTest", "orderResponse on failure")
                }
            })
        }











    }// end onCreate

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()    // 关闭当前Activity
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateGoodsDetail(): String{
        val res = StringBuilder()
        res.appendLine("goods_name: ${goodsViewModel.goodsNameLiveData.value}")
        res.appendLine("goods_category: ${goodsViewModel.goodsCategoryLiveData.value}")
        res.appendLine("goods_price: ${goodsViewModel.goodsPriceLiveData.value}")
        res.appendLine("goods_storage: ${goodsViewModel.goodsStorageLiveData.value}")
        val sb = goodsViewModel.goodsNameLiveData.value?.repeat(500)
        res.append(sb)
        return res.toString()
    }
}