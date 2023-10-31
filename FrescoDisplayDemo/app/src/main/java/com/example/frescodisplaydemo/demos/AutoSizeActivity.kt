package com.example.frescodisplaydemo.demos

import android.app.ActionBar.LayoutParams
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.example.frescodisplaydemo.utils.ScreenUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.drawee.view.SimpleDraweeView

class AutoSizeActivity : AppCompatActivity(), OnClickListener {

  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private lateinit var mLoadBigBtn: Button
  private lateinit var mLoadSmallBtn: Button
  private lateinit var mLayout: LinearLayout
  private val uri = Uri.parse("https://ts4.cn.mm.bing.net/th?id=ORMS.0c2b5cf0d39cb3d339c4c23aa883a8a6&pid=Wdp&w=612&h=304&qlt=90&c=1&rs=1&dpr=2&p=0")
  private val uri2 = Uri.parse("https://img-blog.csdnimg.cn/20201224101652504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjM3MjE1OQ==,size_16,color_FFFFFF,t_70")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_auto_size)
    initViews()
  }

  private fun initViews() {
    mLoadBigBtn = findViewById<Button?>(R.id.auto_size_big_btn).apply { setOnClickListener(this@AutoSizeActivity) }
    mLoadSmallBtn = findViewById<Button?>(R.id.auto_size_small_btn).apply { setOnClickListener(this@AutoSizeActivity) }
    mLayout = findViewById(R.id.auto_size_layout)
  }

  override fun onClick(v: View) {
    when (v.id) {
      R.id.auto_size_big_btn -> {
        mSimpleDraweeView = SimpleDraweeView(this)
        val screenWidth = ScreenUtil.getScreenWidth(this)
        val params = LayoutParams(screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
        mSimpleDraweeView.layoutParams = params
        mSimpleDraweeView.aspectRatio = 1.0f
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(mSimpleDraweeView.controller)
                .build() as PipelineDraweeController
        mSimpleDraweeView.controller = controller
        mLayout.removeAllViews()
        mLayout.addView(mSimpleDraweeView)
      }

      R.id.auto_size_small_btn -> {
        mSimpleDraweeView = SimpleDraweeView(this)
        val screenWidth = ScreenUtil.getScreenWidth(this)
        val params = LayoutParams(screenWidth / 2, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.CENTER
        mSimpleDraweeView.layoutParams = params
        mSimpleDraweeView.aspectRatio = 1.0f
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(mSimpleDraweeView.controller)
                .build() as PipelineDraweeController
        mSimpleDraweeView.controller = controller
        mLayout.removeAllViews()
        mLayout.addView(mSimpleDraweeView)
      }
    }
  }

}