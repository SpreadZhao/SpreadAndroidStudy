package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView

class CircleAndCornerActivity : AppCompatActivity() {

  private val uri = Uri.parse("https://ts1.cn.mm.bing.net/th?id=ORMS.da7ae78f5cf3b591b0d9b595e19081fb&pid=Wdp&w=300&h=156&qlt=90&c=1&rs=1&dpr=1.75&p=0")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_circle_and_corner)
    val cornerBtn = findViewById<Button>(R.id.corner_btn)
    val circleBtn = findViewById<Button>(R.id.circle_btn)
    val image = findViewById<SimpleDraweeView>(R.id.corner_circle_img)

    val roundingParams = RoundingParams.fromCornersRadius(5f)
    roundingParams.setBorder(resources.getColor(com.google.android.material.R.color.design_default_color_error), 1f)

    image.setImageURI(uri)

    circleBtn.setOnClickListener {
      roundingParams.roundAsCircle = true
      image.hierarchy.roundingParams = roundingParams
//            val controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setOldController(image.controller)
//                .build()
//            image.controller = controller
    }

    cornerBtn.setOnClickListener {
      roundingParams.setCornersRadius(90f)
      roundingParams.roundAsCircle = false
      image.hierarchy.roundingParams = roundingParams
//            val controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setOldController(image.controller)
//                .build()
//            image.controller = controller
    }
  }
}