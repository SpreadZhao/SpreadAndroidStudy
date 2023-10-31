package com.example.frescodisplaydemo.demos

import android.graphics.PointF
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.R
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView

class MultipleScaleTypeActivity : AppCompatActivity() {

  private var mType = 0
  private lateinit var mExplain: Array<String>
  private lateinit var mSpinner: Spinner
  private lateinit var mTextView: TextView

  //    private lateinit var mButton: Button
  private lateinit var mSimpleDraweeView: SimpleDraweeView
  private val uri = Uri.parse("https://img-blog.csdnimg.cn/img_convert/f80734ef5a1c9b3dc1dc514af1b7eb84.png")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Fresco.initialize(this)
    setContentView(R.layout.activity_multiple_scale_type)

    initViews()
    initData()

  }

  private fun updateHierarchy() {
    val builder = GenericDraweeHierarchyBuilder(resources)
    val hierarchy = when (mType) {
      0 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER).build()
      1 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP).build()
      2 -> {
        val point = PointF(0f, 0f)
        builder.setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP)
                .setActualImageFocusPoint(point)
                .build()
      }

      3 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).build()
      4 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER).build()
      5 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_START).build()
      6 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_END).build()
      7 -> builder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY).build()
      8 -> builder.setActualImageScaleType(null).build()
      else -> builder.build()
    }
    mSimpleDraweeView.hierarchy = hierarchy
  }

  private fun initViews() {
    mSimpleDraweeView = findViewById(R.id.scale_type_image)
    mSpinner = findViewById(R.id.scale_type_spinner)
//        mButton = findViewById(R.id.scale_type_btn)
    mTextView = findViewById(R.id.scale_type_text)
  }

  private fun initData() {
    val items = resources.getStringArray(R.array.crop_type)
    mExplain = resources.getStringArray(R.array.crop_type_explain)
    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
    mSpinner.adapter = adapter
    mSpinner.onItemSelectedListener = object : OnItemSelectedListener {
      override fun onItemSelected(
              parent: AdapterView<*>?,
              view: View?,
              position: Int,
              id: Long
      ) {
        mType = position
        mTextView.text = mExplain[position]
        // TODO: 更新hierarchy和setImageURI必须都执行，不然图片会消失。看看为什么。
        updateHierarchy()
        mSimpleDraweeView.setImageURI(uri)
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
      }
    }
  }


}