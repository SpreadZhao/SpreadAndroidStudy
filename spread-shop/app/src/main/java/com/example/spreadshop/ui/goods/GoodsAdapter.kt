package com.example.spreadshop.ui.goods

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spreadshop.MainActivity
import com.example.spreadshop.R
import com.example.spreadshop.logic.model.Command
import com.example.spreadshop.logic.model.Goods

class GoodsAdapter(val context: Context, val goodsList: List<Goods>): RecyclerView.Adapter<GoodsAdapter.ViewHolder>(){

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val goodsImage: ImageView = view.findViewById(R.id.goods_image)
        val goodsName: TextView = view.findViewById(R.id.goods_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.goods_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val mainActivity = context as MainActivity
            val position = holder.adapterPosition
            val goods = goodsList[position]
            val intent = Intent(context, GoodsActivity::class.java).apply {
                putExtra("goods_name", goods.goods_name)
                putExtra("goods_storage", goods.goods_storage)
                putExtra("goods_price", goods.goods_price)
                putExtra("goods_category", goods.goods_category)
                putExtra("goods_id", goods.goods_id)
                putExtra("username", mainActivity.mainViewModel.username)
            }
            context.startActivity(intent)
        }
        return holder
//        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = goodsList[position]
        holder.goodsName.text = goods.goods_name
        val id = "ic_goods_${goods.goods_id}"
        Glide.with(context).load(Command.getImageByReflect(id)).into(holder.goodsImage)
    }

    override fun getItemCount() = goodsList.size
}