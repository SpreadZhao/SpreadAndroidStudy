package com.spread.recyclerviewstudy.itemactivity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.spread.recyclerviewstudy.R

class BigItemRecyclerViewActivity : AppCompatActivity() {
  companion object {
    const val TAG = "BigItemRecyclerViewActivity-Spread"
    private const val SCREEN_HEIGHT = 2199
    private const val ONE_THIRD_HEIGHT = SCREEN_HEIGHT / 3
  }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_big_item_recycler_view)
    val rootView = findViewById<FrameLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val recyclerView = findViewById<RecyclerView>(R.id.big_item_recyclerview)
    val adapter = MyAdapter()
    val layoutManager = LinearLayoutManager(this).apply {
      isItemPrefetchEnabled = false
    }
    recyclerView.adapter = adapter
//    recyclerView.setItemViewCacheSize(0)
//    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = layoutManager
    recyclerView.itemAnimator = null
    findViewById<Button>(R.id.delete_2).setOnClickListener {
      adapter.remove2()
    }
    findViewById<Button>(R.id.reverse).setOnClickListener {
      adapter.reverse()
    }
    findViewById<Button>(R.id.scroll).setOnClickListener {
      recyclerView.scrollBy(0, 392)
    }
    findViewById<Button>(R.id.scroll2).setOnClickListener {
      recyclerView.scrollBy(0, 74)
    }
    findViewById<Button>(R.id.scroll3).setOnClickListener {
      recyclerView.scrollBy(0, 278)
    }
    findViewById<Button>(R.id.scroll_custom).setOnClickListener {
      val len = findViewById<EditText>(R.id.scroll_len).text.toString().toInt()
      recyclerView.scrollBy(0, len)
    }
  }

  class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val dataSet = createListData(1..10, 11, 22)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ONE_THIRD_HEIGHT + 10)
        background = if (viewType == 11) {
          ColorDrawable(Color.parseColor("#CC0033"))
        } else {
          ColorDrawable(Color.parseColor("#0066CC"))
        }
      }
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = dataSet[position].num.toString()
        textSize = 100f
        adjustGravity()
      }
      printHolder("onBindViewHolder", holder)
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
      super.onViewAttachedToWindow(holder)
      printHolder("onViewAttachedToWindow", holder)
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
      super.onViewDetachedFromWindow(holder)
      printHolder("onViewDetachedFromWindow", holder)
    }

    override fun onViewRecycled(holder: MyViewHolder) {
      super.onViewRecycled(holder)
      printHolder("onViewRecycled", holder)
    }

    override fun getItemViewType(position: Int): Int {
      return dataSet[position].type
    }

    override fun getItemCount() = dataSet.size

    fun remove2() {
      dataSet.removeAt(1)
      notifyItemRemoved(1)
    }

    fun reverse() {
      dataSet.reverse()
      notifyDataSetChanged()
    }

  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

private fun printHolder(event: String, holder: BigItemRecyclerViewActivity.MyViewHolder) {
  val holderId = holder.hashCode()
  val holderItemText = holder.itemView.findViewById<TextView>(R.id.big_text_text).text
  val holderAdapterPosition = holder.adapterPosition
  val holderLayoutPosition = holder.layoutPosition
  Log.d(BigItemRecyclerViewActivity.TAG, "$event, holderId: $holderId, holderItemText: $holderItemText, adapterPosition: $holderAdapterPosition, layoutPosition: $holderLayoutPosition")
}