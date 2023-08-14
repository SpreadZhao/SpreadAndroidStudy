package com.example.spreadshop.ui.goods

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spreadshop.MainActivity
import com.example.spreadshop.R
import com.example.spreadshop.logic.model.Category
import com.example.spreadshop.logic.model.Command

class CategoryAdapter(val context: Context, val categoryList: List<Category>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val categoryName: TextView = view.findViewById(R.id.category_name)
        val categoryImage: ImageView = view.findViewById(R.id.category_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val category = categoryList[position]
            val mainActivity = context as MainActivity
            mainActivity.mainViewModel.getGoods(Command.getCategoryGoods(category.category))
        }
        return holder
//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.category
        Log.d("SpreadShopTest", "category name: ${category.category}")
//        val id: Int
//        when(category.category){
//            "手机" -> {
//                Log.d("SpreadShopTest", "category id: 手机")
//                id = R.drawable.ic_phone
//            }
//            "衣服" -> {
//                Log.d("SpreadShopTest", "category id: 衣服")
//                id = R.drawable.ic_cloth
//            }
//            "裤子" -> {
//                Log.d("SpreadShopTest", "category id: 裤子")
//                id = R.drawable.ic_trousers
//            }
//            else -> {
//                Log.d("SpreadShopTest", "category id: else")
//                id = R.drawable.test_maotai
//            }
//        }
        val id = "ic_category_${category.category}"

        Log.d("SpreadShopTest", "val id: $id")

        Glide.with(context).load(Command.getImageByReflect(id)).into(holder.categoryImage)
    }

    override fun getItemCount() = categoryList.size
}