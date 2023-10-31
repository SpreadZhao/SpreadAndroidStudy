package com.example.frescodisplaydemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.frescodisplaydemo.demos.AutoSizeActivity
import com.example.frescodisplaydemo.demos.CircleAndCornerActivity
import com.example.frescodisplaydemo.demos.GifActivity
import com.example.frescodisplaydemo.demos.GrayScaleActivity
import com.example.frescodisplaydemo.demos.JPEGImgActivity
import com.example.frescodisplaydemo.demos.ModifyImgActivity
import com.example.frescodisplaydemo.demos.MultiAndMultiPlexImgActivity
import com.example.frescodisplaydemo.demos.MultipleScaleTypeActivity
import com.example.frescodisplaydemo.demos.RetryAndFailActivity
import com.example.frescodisplaydemo.demos.ShowProgressBarActivity

class MainActivity : AppCompatActivity() {

    private lateinit var showProgressBarBtn: Button
    private lateinit var circleCornerBtn: Button
    private lateinit var multipleScaleBtn: Button
    private lateinit var gifBtn: Button
    private lateinit var multiMultiplexBtn: Button
    private lateinit var mModifyImgBtn: Button
    private lateinit var mAutoSizeBtn: Button
    private lateinit var mJPEGBtn: Button
    private lateinit var mFailAndRetryBtn: Button
    private lateinit var mGreyBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findButtons()
        initViews()
    }

    private fun findButtons() {
        showProgressBarBtn = findViewById(R.id.progress_bar_btn)
        circleCornerBtn = findViewById(R.id.circle_corner_btn)
        multipleScaleBtn = findViewById(R.id.multiple_scale_type_btn)
        gifBtn = findViewById(R.id.gif)
        multiMultiplexBtn = findViewById(R.id.multi_multiplex_btn)
        mModifyImgBtn = findViewById(R.id.modify_img_btn)
        mAutoSizeBtn = findViewById(R.id.auto_size_btn)
        mJPEGBtn = findViewById(R.id.jpeg_btn)
        mFailAndRetryBtn = findViewById(R.id.retry_btn)
        mGreyBtn = findViewById(R.id.grey_btn)
    }

    private fun initViews() {
        showProgressBarBtn.setOnClickListener { naviToActivity<ShowProgressBarActivity>() }
        circleCornerBtn.setOnClickListener { naviToActivity<CircleAndCornerActivity>() }
        multipleScaleBtn.setOnClickListener { naviToActivity<MultipleScaleTypeActivity>() }
        gifBtn.setOnClickListener { naviToActivity<GifActivity>() }
        multiMultiplexBtn.setOnClickListener { naviToActivity<MultiAndMultiPlexImgActivity>() }
        mModifyImgBtn.setOnClickListener { naviToActivity<ModifyImgActivity>() }
        mAutoSizeBtn.setOnClickListener { naviToActivity<AutoSizeActivity>() }
        mJPEGBtn.setOnClickListener { naviToActivity<JPEGImgActivity>() }
        mFailAndRetryBtn.setOnClickListener { naviToActivity<RetryAndFailActivity>() }
        mGreyBtn.setOnClickListener { naviToActivity<GrayScaleActivity>() }
    }

    private inline fun <reified T> naviToActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}