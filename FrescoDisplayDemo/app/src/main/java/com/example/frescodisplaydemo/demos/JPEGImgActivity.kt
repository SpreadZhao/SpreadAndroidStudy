package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

class JPEGImgActivity : AppCompatActivity() {

  private val uri = "https://developer.android.google.cn/static/images/tools/vas-svgerror_2-2_2x.png?hl=zh-cn"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_jpegimg)
    val simpleDraweeView = findViewById<SimpleDraweeView>(R.id.main_sdv4)
    val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))
            .setProgressiveRenderingEnabled(true)
            .build()
    simpleDraweeView.hierarchy.setProgressBarImage(ProgressBarDrawable())
    val controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(request)
            .setOldController(simpleDraweeView.controller)
            .setTapToRetryEnabled(true)
            .build()
    simpleDraweeView.controller = controller
  }
}