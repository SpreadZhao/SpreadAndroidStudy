package com.spread.recyclerviewstudy.itemactivity

import android.content.ClipData.Item
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spread.recyclerviewstudy.R
import com.spread.recyclerviewstudy.data.ItemType
import com.spread.recyclerviewstudy.preload.LayoutCompletedOccasion
import com.spread.recyclerviewstudy.preload.ViewHolderVisibilityDispatcher
import com.spread.recyclerviewstudy.recyclerview.DispatcherRecyclerView
import com.spread.recyclerviewstudy.tool.BottomToolbarButtonsAdapter
import com.spread.recyclerviewstudy.tool.ButtonItem

class DispatcherActivity : AppCompatActivity() {

  companion object {
    private const val TAG = "DispatcherActivity-Spread"
  }

  private lateinit var recyclerView: DispatcherRecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_dispatcher)
    val rootView = findViewById<FrameLayout>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    recyclerView = findViewById(R.id.dispatcher_recyclerview)
    val adapter = MyAdapter()
    val layoutManager = object : LinearLayoutManager(this) {
      override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return height
      }

      override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        dispatcher.dispatchVisibility(LayoutCompletedOccasion)
      }
    }
    recyclerView.adapter = adapter
    recyclerView.layoutManager = layoutManager
//    recyclerView.itemAnimator = null
//    PagerSnapHelper().attachToRecyclerView(recyclerView)

    val bottomRV = findViewById<RecyclerView>(R.id.bottom_toolbar)
    bottomRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
    bottomRV.adapter = BottomToolbarButtonsAdapter(bottomRV, listOf(
      ButtonItem("Scroll") { recyclerView.scrollBy(0, 500) },
      ButtonItem("First Shrink") { adapter.firstShrink() },
      ButtonItem("Insert After 1") { adapter.insertOne() },
      ButtonItem("Delete Second") { adapter.deleteOne() }
    ))
    dispatcher = ViewHolderVisibilityDispatcher.Builder
      .bindRecyclerView(recyclerView)
      .setListener(visibilityListener)
      .build()
    recyclerView.dispatcher = dispatcher
  }

  private val visibilityListener = object : ViewHolderVisibilityDispatcher.VisibilityListener {
    override fun onViewHolderVisible(holder: RecyclerView.ViewHolder) {
      printHolder(TAG, "Visible", holder)
//          Log.d(TAG, "${holder.bindingAdapterPosition + 1}可见啦")
    }

    override fun onViewHolderInvisible(holder: RecyclerView.ViewHolder) {
      printHolder(TAG, "Invisible", holder)
//          Log.d(TAG, "${holder.bindingAdapterPosition + 1}不可见啦")
    }
  }

  private lateinit var dispatcher: ViewHolderVisibilityDispatcher

  inner class MyAdapter : RecyclerView.Adapter<DispatcherActivity.MyViewHolder>() {

    private val dataSet = createListData(1..10, ItemType.DATA_TYPE_EVEN, ItemType.DATA_TYPE_ODD)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.big_text, parent, false).apply {
        background = if (viewType == ItemType.DATA_TYPE_EVEN) {
          ColorDrawable(Color.parseColor("#CC0033"))
        } else if (viewType == ItemType.DATA_TYPE_ODD) {
          ColorDrawable(Color.parseColor("#0066CC"))
        } else {
          ColorDrawable(Color.parseColor("#f47920"))
        }
        layoutParams = RecyclerView.LayoutParams(
          RecyclerView.LayoutParams.MATCH_PARENT,
          2199 / 3 + 10
        )
      }
      return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.textView.text = dataSet[position].str
      holder.textView.textSize = 100f
      holder.typeView.text = dataSet[position].type.toString()
      holder.textView.adjustGravity()
      holder.typeView.adjustGravityForType()
    }

    override fun getItemCount() = dataSet.size

    fun firstShrink() {
      val firstChild = recyclerView.getChildAt(0)
      firstChild.updateLayoutParams<ViewGroup.LayoutParams> {
        width = LayoutParams.MATCH_PARENT
        height = 2199 / 3
      }
    }

    fun insertOne() {
      val newItem = Data("Spread", ItemType.DATA_TYPE_OTHER)
      dataSet.add(1, newItem)
      notifyItemInserted(1)
    }

    fun deleteOne() {
      dataSet.removeAt(1)
      notifyItemRemoved(1)
    }

    override fun getItemViewType(position: Int) = dataSet[position].type
  }

  inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.big_text_text)
    val typeView: TextView = itemView.findViewById(R.id.big_text_type)
  }

}