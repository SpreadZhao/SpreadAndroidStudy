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
    private const val DATA_TYPE_EVEN = 11 // 偶数
    private const val DATA_TYPE_ODD = 22  // 奇数
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
    val layoutManager = LinearLayoutManager(this).apply {
      isItemPrefetchEnabled = false
    }
    recyclerView.adapter = adapter
//    recyclerView.setItemViewCacheSize(0)
//    recyclerView.setHasFixedSize(true)
    recyclerView.layoutManager = layoutManager
    recyclerView.itemAnimator = null

    initBottomToolbar()

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

  private fun initBottomToolbar() {
    val bottomToolbarRV = findViewById<RecyclerView>(R.id.big_item_bottom_toolbar_recyclerview)
    bottomToolbarRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    bottomToolbarRV.adapter = BottomToolbarButtonsAdapter(recyclerView)
  }

  class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
  }

  inner class BottomToolbarButtonsAdapter(private val mRecyclerView: RecyclerView) : RecyclerView.Adapter<ButtonViewHolder>() {

    private val mAdapter = mRecyclerView.adapter as MyAdapter

    private val btns: List<ButtonItem> = listOf(
      ButtonItem("Insert1") { mAdapter.insertAfterFirst() },
      ButtonItem("RemoveLast") { mAdapter.removeLast() },
      ButtonItem("Reverse") { mAdapter.reverse() },
      ButtonItem("Append") { mAdapter.append() },
      ButtonItem("Scroll") { mRecyclerView.scrollBy(0, 392) },
      ButtonItem("Scroll2") { mRecyclerView.scrollBy(0, 74) },
      ButtonItem("Scroll3") { mRecyclerView.scrollBy(0, 278) }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
      val btn = Button(parent.context).apply {
        layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
      }
      return ButtonViewHolder(btn)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
      holder.bind(btns[position])
    }

    override fun getItemCount(): Int {
      return btns.size
    }
  }

  inner class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private val dataSet = createListData(1..10, DATA_TYPE_EVEN, DATA_TYPE_ODD)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ONE_THIRD_HEIGHT + 10)
        background = if (viewType == DATA_TYPE_EVEN) {
          ColorDrawable(Color.parseColor("#CC0033"))
        } else {
          ColorDrawable(Color.parseColor("#0066CC"))
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

    override fun getItemCount(): Int {
      return dataSet.size
    }

    fun remove2() {
      dataSet.removeAt(1)
      notifyItemRemoved(1)
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
      val newItem = Data("Spread", DATA_TYPE_EVEN)
      dataSet.add(1, newItem)
      notifyItemInserted(1)
    }
  }

  inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

  inner class ButtonViewHolder(private val buttonView: Button) : RecyclerView.ViewHolder(buttonView) {
    fun bind(item: ButtonItem) {
      buttonView.text = item.name
      buttonView.setOnClickListener(item.onClick)
    }
  }

  inner class ButtonItem(val name: String, val onClick: View.OnClickListener)
}

private fun printHolder(event: String, holder: BigItemRecyclerViewActivity.MyViewHolder) {
  val holderId = holder.hashCode()
  val holderItemText = holder.itemView.findViewById<TextView>(R.id.big_text_text).text
  val holderAdapterPosition = holder.adapterPosition
  val holderLayoutPosition = holder.layoutPosition
  Log.d(BigItemRecyclerViewActivity.TAG, "$event, holderId: $holderId, holderItemText: $holderItemText, adapterPosition: $holderAdapterPosition, layoutPosition: $holderLayoutPosition")
}