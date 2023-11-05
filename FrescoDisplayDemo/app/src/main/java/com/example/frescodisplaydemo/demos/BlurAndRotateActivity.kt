package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.common.RotationOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

class BlurAndRotateActivity : AppCompatActivity() {

  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private lateinit var mBlurBtn: Button
  private lateinit var mDisBlurBtn: Button
  private lateinit var mRotateBtn: Button

  private val uri = Uri.parse("https://gw.alicdn.com/imgextra/i3/4106851852/O1CN01BPbUCX1PYGKlzoaNg_!!4106851852.jpg_Q75.jpg_.webp")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_resize_and_rotate)
    initViews()
    setListeners()
  }

  private fun initViews() {
    mSimpleDraweeView = findViewById(R.id.blur_rotate_img)
    mBlurBtn = findViewById(R.id.blur_btn)
    mDisBlurBtn = findViewById(R.id.disblur_btn)
    mRotateBtn = findViewById(R.id.rotate_btn)
  }

  private fun setListeners() {
    mBlurBtn.setOnClickListener {
      val width = 50
      val height = 50
      val request = ImageRequestBuilder.newBuilderWithSource(uri)
              .setResizeOptions(ResizeOptions(width, height))
              .build()
      val controller = Fresco.newDraweeControllerBuilder()
              .setImageRequest(request)
              .setOldController(mSimpleDraweeView.controller)
              .build()
      mSimpleDraweeView.controller = controller
    }
    mRotateBtn.setOnClickListener {
      val request = ImageRequestBuilder.newBuilderWithSource(uri)
              .setRotationOptions(RotationOptions.forceRotation(RotationOptions.ROTATE_90))
              .build()
      val controller = Fresco.newDraweeControllerBuilder()
              .setImageRequest(request)
              .setOldController(mSimpleDraweeView.controller)
              .build()
      mSimpleDraweeView.controller = controller
    }
  }
}