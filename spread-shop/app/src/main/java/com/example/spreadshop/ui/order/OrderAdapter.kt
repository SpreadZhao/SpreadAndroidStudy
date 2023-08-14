package com.example.spreadshop.ui.order

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spreadshop.R
import com.example.spreadshop.logic.model.Bag

class OrderAdapter(val context: Context, val orderList: List<Bag>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val username: TextView = view.findViewById(R.id.order_username)
        val orderId: TextView = view.findViewById(R.id.order_id)
        val orderTime: TextView = view.findViewById(R.id.order_time)
        val orderGoodsName: TextView = view.findViewById(R.id.order_goods_name)
        val orderGoodsNum: TextView = view.findViewById(R.id.order_goods_num)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orderList[position]
        Log.d("SpreadShopTest", "OrderAdapter: name=${order.goods_name}")
        holder.username.text = "username: ${order.account_name}"
        holder.orderId.text = "order id: ${order.order_id}"
        holder.orderTime.text = "order time: ${order.order_time}"
        holder.orderGoodsName.text = "goods name: ${order.goods_name}"
        holder.orderGoodsNum.text = "goods num: ${order.goods_number}"
    }

    override fun getItemCount() = orderList.size
}