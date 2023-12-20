package com.spread.recyclerviewstudy

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.spread.recyclerviewstudy.itemactivity.BigItemRecyclerViewActivity
import com.spread.recyclerviewstudy.itemactivity.PagerSnapActivity
import com.spread.recyclerviewstudy.itemactivity.SimpleRecyclerViewActivity
import com.spread.recyclerviewstudy.itemactivity.ViewPager2Activity
import com.spread.recyclerviewstudy.itemactivity.ViewPagerActivity

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root_layout)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    val mainRecycler = findViewById<RecyclerView>(R.id.main_btns_recycler_view)
    mainRecycler.adapter = MainBtnsAdapter()
    mainRecycler.layoutManager = LinearLayoutManager(this)
  }

  private fun <T> naviToActiviy(activity: Class<T>) {
    val intent = Intent(this, activity)
    startActivity(intent)
  }

  inner class MainBtnsAdapter : RecyclerView.Adapter<MainBtnsViewHolder>() {

    private val btnInfos = listOf(
      ButtonInfo("Simple Recycler View", SimpleRecyclerViewActivity::class.java),
      ButtonInfo("View Pager", ViewPagerActivity::class.java),
      ButtonInfo("View Pager 2", ViewPager2Activity::class.java),
      ButtonInfo("Pager Snap", PagerSnapActivity::class.java),
      ButtonInfo("Big Item", BigItemRecyclerViewActivity::class.java)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBtnsViewHolder {
      val btnView = Button(parent.context).apply {
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER
      }
      return MainBtnsViewHolder(btnView)
    }

    override fun onBindViewHolder(holder: MainBtnsViewHolder, position: Int) {
      holder.btn.apply {
        val info = btnInfos[position]
        text = info.displayName
        setOnClickListener { naviToActiviy(info.target) }
      }
    }

    override fun getItemCount() = btnInfos.size
  }

  inner class MainBtnsViewHolder(val btn: Button) : ViewHolder(btn)

  class ButtonInfo(val displayName: String, val target: Class<*>)
}