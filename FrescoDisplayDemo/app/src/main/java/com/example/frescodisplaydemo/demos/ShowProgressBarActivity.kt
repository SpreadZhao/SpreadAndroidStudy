package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.view.SimpleDraweeView

class ShowProgressBarActivity : AppCompatActivity() {

  private val uri: Uri = Uri.parse("https://img-s-msn-com.akamaized.net/tenant/amp/entityid/AA1iRWIR.img?w=612&h=304&q=90&m=6&f=jpg&x=366&y=309&u=t")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_show_progress_bar)
    val button = findViewById<Button>(R.id.progress_btn)
    val image = findViewById<SimpleDraweeView>(R.id.progress_bar_img)
    button.setOnClickListener {
      image.hierarchy.setProgressBarImage(ProgressBarDrawable())
      image.setImageURI(uri)
//            val controller = Fresco.newDraweeControllerBuilder()
//                .setUri(uri)
//                .setOldController(image.controller)
//                .build()
//            image.controller = controller
    }
  }
}