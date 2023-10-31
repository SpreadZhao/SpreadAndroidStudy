package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

class GifActivity : AppCompatActivity(), OnClickListener {

  private lateinit var requestBtn: Button
  private lateinit var stopButton: Button
  private lateinit var startButton: Button
  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private val uri = Uri.parse("https://s1.aigei.com/src/img/gif/fc/fc9ee60300bb4e23b750cf87a6199a36.gif?imageMogr2/auto-orient/thumbnail/!282x282r/gravity/Center/crop/282x282/quality/85/&e=1735488000&token=P7S2Xpzfz11vAkASLTkfHN7Fw-oOZBecqeJaxypL:ZcZRnZhKndE5TVxwjojFkKKqiU0=")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_gif)
    initViews()
  }

  private fun initViews() {
    mSimpleDraweeView = findViewById(R.id.gif_image)
    requestBtn = findViewById<Button>(R.id.gif_request_button).apply { setOnClickListener(this@GifActivity) }
    stopButton = findViewById<Button>(R.id.gif_stop_button).apply { setOnClickListener(this@GifActivity) }
    startButton = findViewById<Button>(R.id.gif_start_button).apply { setOnClickListener(this@GifActivity) }
  }

  override fun onClick(v: View?) {
    if (v == null) return
    when (v.id) {
      R.id.gif_request_button -> {
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(false)
                .setOldController(mSimpleDraweeView.controller)
                .build()
        mSimpleDraweeView.controller = controller
      }

      R.id.gif_start_button -> {
        val animation = mSimpleDraweeView.controller?.animatable
        animation?.let { if (!it.isRunning) it.start() }
      }

      R.id.gif_stop_button -> {
        val animation = mSimpleDraweeView.controller?.animatable
        animation?.let { if (it.isRunning) it.stop() }
      }
    }
  }
}