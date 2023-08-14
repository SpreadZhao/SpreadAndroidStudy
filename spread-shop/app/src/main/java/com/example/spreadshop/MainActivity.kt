package com.example.spreadshop

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spreadshop.databinding.ActivityMainBinding
import com.example.spreadshop.databinding.NavHeaderBinding
import com.example.spreadshop.logic.model.*
import com.example.spreadshop.ui.goods.CategoryAdapter
import com.example.spreadshop.ui.goods.GoodsAdapter
import com.example.spreadshop.ui.order.OrderActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMain: ActivityMainBinding
    private lateinit var bindingNavHeader: NavHeaderBinding

    private lateinit var searchEdit: SearchView
    lateinit var mainViewModel: MainViewModel

    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        bindingNavHeader = NavHeaderBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        setSupportActionBar(bindingMain.toolbar)

//        supportActionBar?.let{
//            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
//        }
        //let和apply都可以
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        mainViewModel.username = intent.getStringExtra("username").toString()

        if(mainViewModel.username != "") mainViewModel.getBalance()
        //Log.d("SpreadShopTest", "Log in username: $username")
//        bindingNavHeader.userName.text = username
        if(bindingMain.navView.headerCount > 0){
            val header = bindingMain.navView.getHeaderView(0)
            val uname = header.findViewById<TextView>(R.id.user_name)
            uname.text = "user name: ${mainViewModel.username}"
        }





        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // 将viewModel里的goodsList设置为一个list，只是让它不为空而已
        // 这样才能设置goodsRecycler的adapter
        // 这个操作要在发起网络请求之前完成，避免错误覆盖网络请求回来的信息
        //mainViewModel.initGoods()

        // 打开MainActivity之后立刻发起请求获取所有的种类
        mainViewModel.getAllCategory(Command.GET_ALL_CATEGORY)

        // 打开之后获取一次推荐商品
        mainViewModel.getGoods(Command.GET_RECOMMAND)

        // 设置goodsRecycler的adapter
        // 由于ArrayList的构造函数，所以goodsList不为空
        val goodsLayoutManager = GridLayoutManager(this, 2)
        bindingMain.goodsRecycler.layoutManager = goodsLayoutManager
        val goodsAdapter = GoodsAdapter(this, mainViewModel.goodsList)
        bindingMain.goodsRecycler.adapter = goodsAdapter

        // 设置categoryRecycler的adapter
        val categoryLayoutManager = GridLayoutManager(this, 1)
        categoryLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        bindingMain.categoryRecycler.layoutManager = categoryLayoutManager
        val categoryAdapter = CategoryAdapter(this, mainViewModel.categoryList)
        bindingMain.categoryRecycler.adapter = categoryAdapter

//        val test = getImageByReflect("ic_menu")
//        Log.d("SpreadShopTest", "test reflect: $test")
//        Log.d("SpreadShopTest", "test reflect real: ${R.drawable.ic_menu}")
//        val goodsService = ServiceCreator.create<GoodsService>()
//        goodsService.getAllGoods(Command.SHOW_ALL).enqueue(object: Callback<List<Goods>>{
//            override fun onResponse(call: Call<List<Goods>>, response: Response<List<Goods>>) {
//                val list = response.body()
//                if(list != null){
//                    Log.d("SpreadShopTest", "[showall]list is not null")
//                }else{
//                    Log.d("SpreadShopTest", "[showall]list is null")
//                }
//            }
//
//            override fun onFailure(call: Call<List<Goods>>, t: Throwable) {
//                t.printStackTrace()
//                Log.d("SpreadShopTest", "[showall]on failure")
//            }
//        })

//        Repository.getGoods(Command.SHOW_ALL)



        mainViewModel.goodsLiveData.observe(this){
            it.enqueue(object : Callback<GoodsResponse>{
                override fun onResponse(call: Call<GoodsResponse>, response: Response<GoodsResponse>) {
                    val goodsResponse = response.body()
                    if(goodsResponse != null){
                        Log.d("SpreadShopTest", "goodsResponse is not null")
                        if(goodsResponse.success){
                            Log.d("SpreadShopTest", "goodsResponse success!")
                            val list = goodsResponse.goods

                            // goods recycler
//                            val layoutManager = GridLayoutManager(this@MainActivity, 2)
//                            bindingMain.goodsRecycler.layoutManager = layoutManager
//                            val adapter = GoodsAdapter(this@MainActivity, list)
//                            bindingMain.goodsRecycler.adapter = adapter
                            //mainViewModel.updateGoods(list)

                            mainViewModel.goodsList.clear()
                            mainViewModel.goodsList.addAll(list)
                            mainViewModel.isGotGoodsLiveData.value = true

//                            goodsAdapter.notifyDataSetChanged()
//                            bindingMain.swipeRefresh.isRefreshing = false

//                            for(goods in list){
//                                Log.d("SpreadShopTest", "goods: ${goods.goods_name}")
//                            }
                        }else{
                            Log.d("SpreadShopTest", "goodsResponse fail!")
                        }
                    }else{
                        Log.d("SpreadShopTest", "goodsResponse is null")
                    }
                }

                override fun onFailure(call: Call<GoodsResponse>, t: Throwable) {
                    t.printStackTrace()
                    mainViewModel.goodsList.clear()
                    mainViewModel.isGotGoodsLiveData.value = false
                    Log.d("SpreadShopTest", "goodsResponse on failure")
                }
            })
        }// end mainViewModel.goodsLiveData.observe

        mainViewModel.categoryLiveData.observe(this){
            it.enqueue(object: Callback<CategoryResponse>{
                override fun onResponse(
                    call: Call<CategoryResponse>,
                    response: Response<CategoryResponse>
                ) {
                    val categoryResponse = response.body()
                    if(categoryResponse != null){
                        if(categoryResponse.success){
                            Log.d("SpreadShopTest", "category success")
                            val list = categoryResponse.categories

                            // category recycler
//                            val categoryLayoutManager = GridLayoutManager(this@MainActivity, 1)
//                            bindingMain.categoryRecycler.layoutManager = categoryLayoutManager
//                            val adapter = CategoryAdapter(this@MainActivity, list)
//                            bindingMain.categoryRecycler.adapter = adapter

                            mainViewModel.categoryList.clear()
                            mainViewModel.categoryList.addAll(list)
                            mainViewModel.isGotCategoryLiveData.value = true

//                            for(category in list){
//                                Log.d("SpreadShopTest", "category: $category")
//                            }
                        }else{
                            Log.d("SpreadShopTest", "Category fail")
                        }
                    }else{
                        Log.d("SpreadShopTest", "category is null")
                    }
                }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    t.printStackTrace()
                    mainViewModel.categoryList.clear()
                    mainViewModel.isGotCategoryLiveData.value = false
                    Log.d("SpreadShopTest", "category on failure")
                }
            })
        }// end mainViewModel.categoryLiveData.observe

        mainViewModel.balanceResponseLiveData.observe(this){
            it.enqueue(object : Callback<BalanceResponse>{
                override fun onResponse(
                    call: Call<BalanceResponse>,
                    response: Response<BalanceResponse>
                ) {
                    val balanceR = response.body()
                    if(balanceR != null){
                        if(balanceR.success){
                            mainViewModel.balance = balanceR.balance.toString()
                            if(bindingMain.navView.headerCount > 0){
                                val header = bindingMain.navView.getHeaderView(0)
                                val uname = header.findViewById<TextView>(R.id.ballance)
                                Log.d("SpreadShopTest", "ballance: ${mainViewModel.balance}")
                                uname.text = "ballance: ${mainViewModel.balance}"
                            }
                        }else{
                            Log.d("SreadShopTest", "ballanceR fail")
                        }
                    }else{
                        Log.d("SpreadShopTest", "ballanceR is null, msg=${response.message()}")
                    }
                }

                override fun onFailure(call: Call<BalanceResponse>, t: Throwable) {
                    Log.d("SpreadShopTest", "BR on failure")
                    t.printStackTrace()
                }
            })
        }

//        mainViewModel.goodsListLiveData.observe(this){
//            Log.d("SpreadShopTest", "update goodsLiveData changed!")
//            thread {
//                runOnUiThread {
//                    goodsAdapter.notifyDataSetChanged()
//                    bindingMain.swipeRefresh.isRefreshing = false
//                }
//            }
//        }


        mainViewModel.isGotGoodsLiveData.observe(this){
            if(it == true){
                Log.d("SpreadShopTest", "got live data!")
//                for(goods in mainViewModel.goodsList){
//                    Log.d("SpreadShopTest", "[live data test]name: ${goods.goods_name}")
//                }
//                runOnUiThread {
//                    goodsAdapter.notifyDataSetChanged()
//                    bindingMain.swipeRefresh.isRefreshing = false
//                }
            }else{
                Log.d("SpredShopTest", "didn't got live data")
                Toast.makeText(this@MainActivity, "refresh failed", Toast.LENGTH_SHORT).show()
            }

            runOnUiThread {
                goodsAdapter.notifyDataSetChanged()
                bindingMain.swipeRefresh.isRefreshing = false
            }
        }

        mainViewModel.isGotCategoryLiveData.observe(this){
            if(it == true){
                Log.d("SpreadShopTest", "got category live data")
            }else{
                Log.d("SpreadShopTest", "didn't got category live data")
                Toast.makeText(this@MainActivity, "category refresh failed", Toast.LENGTH_SHORT).show()
            }

            runOnUiThread {
                categoryAdapter.notifyDataSetChanged()
            }
        }

        bindingMain.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_mybag -> Log.d("SpreadShopTest", "You clicked mybag")
                R.id.nav_order -> {
                    Log.d("SpreadShopTest", "You clicked myorder")
                    val intent = Intent(this@MainActivity, OrderActivity::class.java).apply {
                        putExtra("username", mainViewModel.username)
                    }
                    startActivity(intent)
                }
                R.id.nav_contact -> Log.d("SpreadShopTest", "You clicked Contact")
                R.id.nav_logout -> Log.d("SpreadShopTest", "You clicked logout")
            }
            bindingMain.drawerLayout.closeDrawers()
            true
        }

        bindingMain.swipeRefresh.setOnRefreshListener {
            mainViewModel.getGoods(Command.GET_RECOMMAND)
            mainViewModel.getAllCategory(Command.GET_ALL_CATEGORY)
        }



//        searchEdit.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                if(query != null){
//                    mainViewModel.getGoods(Command.getSearchGoods(query))
//                }else{
//                    Log.d("SpreadShopTest", "SearchView: query is null")
//                }
//                return true
//            }
//
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                return true
//            }
//        })
//        searchEdit.setOnSuggestionListener(object: SearchView.OnSuggestionListener{
//            override fun onSuggestionSelect(position: Int): Boolean {
//
//            }
//
//            override fun onSuggestionClick(position: Int): Boolean {
//
//            }
//
//        })


    }// end onCreate

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> bindingMain.drawerLayout.openDrawer(GravityCompat.START)
//            R.id.backup -> Log.d("SpreadShopTest", "you clicked backup")
//            R.id.delete -> Log.d("SpreadShopTest", "you clicked delete")
//            R.id.settings -> Log.d("SpreadShopTest", "you clicked settings")
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)

        val searchItem = menu?.findItem(R.id.search_edit)
        //searchEdit = findViewById(R.id.search_edit)
        //searchEdit = MenuItemCompat.getActionView(searchItem) as SearchView
        searchEdit = searchItem?.actionView as SearchView

        searchEdit.isSubmitButtonEnabled = true
        searchEdit.imeOptions = EditorInfo.IME_ACTION_SEARCH

        searchEdit.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null){
                    mainViewModel.getGoods(Command.getSearchGoods(query))
                }else{
                    Log.d("SpreadShopTest", "SearchView: query is null")
                }
                inputMethodManager.hideSoftInputFromWindow(searchEdit.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                return true
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == ""){
                    mainViewModel.getGoods(Command.GET_RECOMMAND)
                }
                return true
            }
        })
//        searchEdit.setOnSuggestionListener(object: SearchView.OnSuggestionListener{
//            override fun onSuggestionSelect(position: Int): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onSuggestionClick(position: Int): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        })
        return true
    }



}