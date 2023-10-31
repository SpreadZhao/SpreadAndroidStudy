package com.example.frescodisplaydemo.demos

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.imagepipeline.request.ImageRequestBuilder

class ModifyImgActivity : AppCompatActivity(), OnClickListener {

  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private lateinit var mAddGridBtn: Button
  private val uri = Uri.parse("https://ts3.cn.mm.bing.net/th?id=ORMS.62c82ea829046ecb311b7f95f54c66fa&pid=Wdp&w=612&h=304&qlt=90&c=1&rs=1&dpr=2&p=0")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_modify_img)
    initViews()
  }

  private fun initViews() {
    mSimpleDraweeView = findViewById(R.id.modify_img)
    mAddGridBtn = findViewById<Button?>(R.id.modify_button).apply { setOnClickListener(this@ModifyImgActivity) }
  }

  override fun onClick(v: View?) {
    if (v == null) return
    when (v.id) {
      R.id.modify_button -> {
        val postProcessor = object : BasePostprocessor() {
          override fun process(bitmap: Bitmap?) {
            if (bitmap == null) return
            for (x in 0..<bitmap.width step 2) {
              for (y in 0..<bitmap.height step 2) {
                bitmap.setPixel(x, y, Color.RED)
              }
            }
          }

          override fun getName(): String = "redMeshPostProcessor"
        }
        val request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postProcessor)
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(mSimpleDraweeView.controller)
                .build()
        mSimpleDraweeView.controller = controller
      }
    }
  }

}