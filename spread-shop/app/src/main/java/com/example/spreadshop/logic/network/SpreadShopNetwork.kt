package com.example.spreadshop.logic.network

object SpreadShopNetwork {
    private val loginService = ServiceCreator.create<LoginResultService>()
    private val goodsService = ServiceCreator.create<GoodsService>()
    private val categoryResponseService = ServiceCreator.create<CategoryResponseService>()
    private val orderResponseService = ServiceCreator.create<OrderResponseService>()
    private val registerResponseService = ServiceCreator.create<RegisterResponseService>()
    private val myOrderResponseService = ServiceCreator.create<MyOrderResponseService>()
    private val balanceResponseService = ServiceCreator.create<BalanceResponseService>()

    fun getLoginResult(username: String, password: String) = loginService.getLoginResult(username, password)
    fun getGoods(cmd: String) = goodsService.getGoods(cmd)
    fun getAllCategory(cmd: String) = categoryResponseService.getAllCategory(cmd)
    fun requestOrder(username: String, goods_id: Int, number: Int) = orderResponseService.requestOrder(username, goods_id, number)
    fun getRegisterResponse(username: String, password: String) = registerResponseService.getRegisterResponse(username, password)
    fun getMyOrder(username: String) = myOrderResponseService.getMyOrder(username)
    fun getBalance(username: String) = balanceResponseService.getBalance(username)
    // 这里为什么能拿到cmd这个变量？是因为lambda表达式？
//    fun getGoods(cmd: String) = goodsService.getGoods(cmd).enqueue(object: Callback<List<Goods>>{
//        override fun onResponse(call: Call<List<Goods>>, response: Response<List<Goods>>) {
//            val list = response.body()
//            if(list != null){
//                Log.d("SpreadShopTest", "[$cmd]list is not null")
//            }else{
//                Log.d("SpreadShopTest", "[$cmd]list is null")
//            }
//
//        }
//
//
//        override fun onFailure(call: Call<List<Goods>>, t: Throwable) {
//            t.printStackTrace()
//            Log.d("SpreadShopTest", "[showall]on failure")
//        }
//    })
}