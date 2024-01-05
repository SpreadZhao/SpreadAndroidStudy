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
import com.spread.recyclerviewstudy.data.ItemType.DATA_TYPE_EVEN
import com.spread.recyclerviewstudy.data.ItemType.DATA_TYPE_ODD
import com.spread.recyclerviewstudy.data.ItemType.DATA_TYPE_OTHER
import com.spread.recyclerviewstudy.preload.ViewHolderVisibilityDispatcher
import com.spread.recyclerviewstudy.recyclerview.TestRecyclerView
import com.spread.recyclerviewstudy.tool.BottomToolbarButtonsAdapter
import com.spread.recyclerviewstudy.tool.ButtonItem

class BigItemRecyclerViewActivity : AppCompatActivity() {
  companion object {
    const val TAG = "BigItemRecyclerViewActivity-Spread"
    private const val SCREEN_HEIGHT = 2199
    private const val ONE_THIRD_HEIGHT = SCREEN_HEIGHT / 3
  }

  private lateinit var recyclerView: RecyclerView

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
    recyclerView = findViewById(R.id.big_item_recyclerview)
    val adapter = MyAdapter()

    recyclerView.adapter = adapter
//    recyclerView.setItemViewCacheSize(0)
//    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = myLinearLayoutManager
    recyclerView.itemAnimator = null

    initBottomToolbar(adapter)

    findViewById<Button>(R.id.delete_2).setOnClickListener {
      adapter.remove2()
    }
    findViewById<Button>(R.id.reverse).setOnClickListener {
      adapter.reverse()
    }
    findViewById<Button>(R.id.scroll_custom).setOnClickListener {
      val len = findViewById<EditText>(R.id.scroll_len).text.toString().toInt()
      recyclerView.scrollBy(0, len)
    }
  }

  private fun initBottomToolbar(adapter: MyAdapter) {
    val bottomToolbarRV = findViewById<RecyclerView>(R.id.big_item_bottom_toolbar_recyclerview)
    bottomToolbarRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    bottomToolbarRV.adapter = BottomToolbarButtonsAdapter(recyclerView, listOf(
      ButtonItem("Remove3") { adapter.remove3() },
      ButtonItem("InsertAfter2") { adapter.insertAfter2() },
      ButtonItem("Insert1") { adapter.insertAfterFirst() },
      ButtonItem("Insert2") { adapter.insertBeforeFirst() },
      ButtonItem("RemoveLast") { adapter.removeLast() },
      ButtonItem("Reverse") { adapter.reverse() },
      ButtonItem("Append") { adapter.append() },
      ButtonItem("Scroll") { recyclerView.scrollBy(0, 392) },
      ButtonItem("Scroll2") { recyclerView.scrollBy(0, 74) },
      ButtonItem("Scroll3") { recyclerView.scrollBy(0, 278) }
    ))
  }

  private val myLinearLayoutManager = object : LinearLayoutManager(this) {
  }.apply {
    isItemPrefetchEnabled = false
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val dataSet = createListData(1..10, DATA_TYPE_EVEN, DATA_TYPE_ODD)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ONE_THIRD_HEIGHT + 10)
        background = if (viewType == DATA_TYPE_EVEN) {
          ColorDrawable(Color.parseColor("#CC0033"))
        } else if (viewType == DATA_TYPE_ODD) {
          ColorDrawable(Color.parseColor("#0066CC"))
        } else {
          ColorDrawable(Color.parseColor("#f47920"))
        }
      }
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.itemView.findViewById<TextView>(R.id.big_text_text).apply {
        text = dataSet[position].str
        textSize = 100f
        adjustGravity()
      }
      printHolder(TAG, "onBindViewHolder", holder)
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
      super.onViewAttachedToWindow(holder)
      printHolder(TAG, "onViewAttachedToWindow", holder)
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
      super.onViewDetachedFromWindow(holder)
      printHolder(TAG, "onViewDetachedFromWindow", holder)
    }

    override fun onViewRecycled(holder: MyViewHolder) {
      super.onViewRecycled(holder)
      printHolder(TAG, "onViewRecycled", holder)
    }

    override fun getItemViewType(position: Int): Int {
      return dataSet[position].type
    }

    override fun getItemCount(): Int {
      return dataSet.size
    }

    fun remove2() {
      dataSet.removeAt(1)
      notifyItemRemoved(1)
    }

    fun remove3() {
      dataSet.removeAt(2)
      notifyItemRemoved(2)
    }

    fun insertAfter2() {
      val newItem = Data("Spread", DATA_TYPE_OTHER)
      dataSet.add(2, newItem)
      notifyItemInserted(2)
    }

    fun reverse() {
      dataSet.reverse()
      notifyDataSetChanged()
    }

    fun removeLast() {
      val lastIndex = dataSet.lastIndex
      dataSet.removeLast()
      notifyItemRemoved(lastIndex)
    }

    fun append() {
      val dataNum = dataSet.size + 1
      val dataType = if (dataNum % 2 == 0) DATA_TYPE_EVEN else DATA_TYPE_ODD
      dataSet.add(Data(dataNum.toString(), dataType))
      notifyItemInserted(dataSet.lastIndex)
    }

    fun insertAfterFirst() {
      val newItem = Data("Spread", DATA_TYPE_OTHER)
      dataSet.add(1, newItem)
      notifyItemInserted(1)
    }

    fun insertBeforeFirst() {
      val newItem = Data("Zhao", DATA_TYPE_OTHER)
      dataSet.add(0, newItem)
      notifyItemInserted(0)
    }
  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}

fun printHolder(tag: String, event: String, holder: RecyclerView.ViewHolder) {
  val holderId = holder.hashCode()
  val holderItemText = holder.itemView.findViewById<TextView>(R.id.big_text_text).text
  val holderAdapterPosition = holder.adapterPosition
  val holderLayoutPosition = holder.layoutPosition
  Log.d(tag, "$event, holderId: $holderId, holderItemText: $holderItemText, adapterPosition: $holderAdapterPosition, layoutPosition: $holderLayoutPosition")
}