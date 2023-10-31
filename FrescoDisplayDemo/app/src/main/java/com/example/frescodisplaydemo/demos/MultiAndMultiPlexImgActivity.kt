package com.example.frescodisplaydemo.demos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MultiAndMultiPlexImgActivity : AppCompatActivity() {

  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private lateinit var mMultiBtn: Button
  private lateinit var mSaveBtn: Button
  private lateinit var mMultiplexBtn: Button
  private lateinit var mDeleteBtn: Button

  private val lowResUri =
          "https://gw.alicdn.com/bao/uploaded/i4/106333868/O1CN017vtRdZ1eRas7lU3g6_!!0-saturn_solar.jpg_300x300q90.jpg"
  private val highResUri =
          "https://gw.alicdn.com/bao/uploaded/i3/2215107289709/O1CN01R07mK62LamH5Pheb9_!!2215107289709.jpg_Q75.jpg_.webp"

  private val uri1 = // 用于本地的图片地址
          "https://gw.alicdn.com/bao/uploaded/i1/2210164913358/O1CN01m0zg8T1ag0rYtgLsW_!!0-item_pic.jpg_300x300q90.jpg"
  private val uri2 = // 本地不行了的图片地址
          "https://gw.alicdn.com/bao/uploaded/i4/2201420780093/O1CN01FzVxPC1CYdgJmoQ6p_!!2201420780093.jpg_300x300q90.jpg"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_multi_and_multi_plex_img)
    initViews()
    setListeners()
  }

  private fun initViews() {
    mSimpleDraweeView = findViewById(R.id.multi_img)
    mMultiBtn = findViewById(R.id.multi_btn)
    mMultiplexBtn = findViewById(R.id.multiplex_btn)
    mSaveBtn = findViewById(R.id.multi_save_btn)
    mDeleteBtn = findViewById(R.id.delete_saved_btn)
  }

  private fun setListeners() {
    /**
     * 功能：先展示一个图片，然后后面那个图片会替换先展示的。
     * 主要用在缩略图，如果你网速不好，那么缩略图请求的快，先占上位置再说。
     * 可以试试把highResUri改成一个不存在的地址，这样就只会展示lowResUri了。
     */
    mMultiBtn.setOnClickListener {
      val controller = Fresco.newDraweeControllerBuilder()
              .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
              .setImageRequest(ImageRequest.fromUri(highResUri))
              .setOldController(mSimpleDraweeView.controller)
              .build()
      mSimpleDraweeView.controller = controller
    }
    /**
     * 功能：给一个图片格子多个地址，哪个能用用哪个。
     * 在本例中，request1从本地拿图片，request2从网络拿图片，模拟真实场景：
     *      如果本地有图片，优先用本地的，不从网络请求。
     * 发现一个bug。如果我首次进入应用，先点击这个按钮，出现的是request2的图片，这没问题；
     * 但是我之后点击了mSaveBtn，将本地图片保存到目录里，此时模拟本地图片已经存在的情况，
     * 退出Activity（不是退出应用）重进，还是点击这个按钮，居然还是request2的图片。要知道这个时候
     * 本地的图片已经存在了；
     * 但是如果我此时退出应用重进，获取到的就是本地的图片了。
     * TODO: 看看源码，为什么会有这样的问题。
     * UPDATE: 我猜测，是因为不管什么方式，请求回来Fresco都会缓存，因为第二次进入就没有动画了。
     */
    mMultiplexBtn.setOnClickListener {
      val file = File(filesDir, "picture.jpeg")
      val request1 = ImageRequest.fromUri(getUriFromFile(file))
      val request2 = ImageRequest.fromUri(uri2)
      val controller = Fresco.newDraweeControllerBuilder()
              .setFirstAvailableImageRequests(arrayOf(request1, request2))
              .setOldController(mSimpleDraweeView.controller)
              .build()
      mSimpleDraweeView.controller = controller
    }
    mSaveBtn.setOnClickListener { thread { getImageAndSave() } }
    mDeleteBtn.setOnClickListener {
      val file = File(filesDir, "picture.jpeg")
      if (file.exists()) {
        file.delete()
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "图片不存在", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun getImageAndSave() {
    var connection: HttpURLConnection?
    var inputStream: InputStream?
    try {
      val url = URL(uri1)
      connection = (url.openConnection() as HttpURLConnection).apply {
        connectTimeout = 5000
        requestMethod = "GET"
      }
      if (connection.responseCode == 200) {
        inputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)
        saveImg(this, bitmap, "picture.jpeg")
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  private fun saveImg(context: Context, bitmap: Bitmap, name: String) {
    context.openFileOutput(name, Context.MODE_PRIVATE).use {
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
      it.flush()
    }
  }

  private fun getUriFromFile(file: File): Uri {
    // 给其它应用提供uri才用得着FileProvider，并且要在AndroidManifest.xml里配置。
//        return FileProvider.getUriForFile(this, "com.example.frescodisplaydemo.fileprovider", file)
    return Uri.fromFile(file)
  }


}