package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.example.frescodisplaydemo.processor.FastGreyScalePostProcessor
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

class GrayScaleActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_gray_scale)
    val uri = Uri.parse("https://ts1.cn.mm.bing.net/th?id=OLC.XQoY25SjdusZlw480x360&w=205&h=180&c=8&rs=1&qlt=90&o=6&dpr=2&pid=3.1&rm=2")
    val greyScaleView = findViewById<SimpleDraweeView>(R.id.grey_scale_view)
    val greyScaleBtn = findViewById<Button>(R.id.grey_scale_btn)
    greyScaleView.setImageURI(uri)
    greyScaleBtn.setOnClickListener {
      val request = ImageRequestBuilder.newBuilderWithSource(uri)
              .setPostprocessor(FastGreyScalePostProcessor())
              .build()
      val controller = Fresco.newDraweeControllerBuilder()
              .setImageRequest(request)
              .build()
      greyScaleView.controller = controller
    }
  }
}