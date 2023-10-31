package com.example.frescodisplaydemo.demos

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView

/**
 * 想验证，你可以先断网进去，我测试的时候，得删除应用数据才管用
 */
class RetryAndFailActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_retry_and_fail)
    val uri = Uri.parse("https://ts4.cn.mm.bing.net/th?id=ORMS.2f08733f21ad08a65194267e622665a2&pid=Wdp&w=300&h=156&qlt=90&c=1&rs=1&dpr=1&p=0")
    val mSimpleDraweeView = findViewById<SimpleDraweeView>(R.id.fail_retry_img)
    val controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setTapToRetryEnabled(true)
            .setOldController(mSimpleDraweeView.controller)
            .build()
    mSimpleDraweeView.controller = controller
  }
}