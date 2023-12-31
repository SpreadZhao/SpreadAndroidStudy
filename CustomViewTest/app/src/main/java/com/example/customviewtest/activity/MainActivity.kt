package com.example.customviewtest.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.customviewtest.MarqueeText
import com.example.customviewtest.R
import com.example.customviewtest.SecondActivity
import com.example.customviewtest.TimeService


class MainActivity : AppCompatActivity(), OnClickListener {

  private val buttons = ArrayList<Button>()

  private val ids = listOf(
    R.id.simple_btn,
    R.id.trouble_btn,
    R.id.view_pager_btn,
    R.id.view_pager2_btn,
    R.id.pager_snap_btn
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    findButtonsAndAdd<Button>(ids)
    setListener()
  }

  private inline fun <reified V : Button> findButtonsAndAdd(ids: List<Int>) {
    ids.forEach {
      val view = findViewById<V>(it)
      buttons.add(view)
    }
  }

  private fun setListener() {
    buttons.forEach { it.setOnClickListener(this) }
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.simple_btn -> naviToActiviy(SimpleActivity::class.java)
      R.id.trouble_btn -> naviToActiviy(TroubleActivity::class.java)
      R.id.view_pager_btn -> naviToActiviy(ViewPagerActivity::class.java)
      R.id.view_pager2_btn -> naviToActiviy(ViewPager2Activity::class.java)
      R.id.pager_snap_btn -> naviToActiviy(PagerSnapActivity::class.java)
    }
  }

  private fun <T> naviToActiviy(activity: Class<T>) {
    val intent = Intent(this, activity)
    startActivity(intent)
  }


}