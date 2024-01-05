package com.example.gcglfz

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gcglfz.TestGame.Companion.hasStuIn
import com.example.gcglfz.TestGame.Companion.hasStuOut

class MainActivity : AppCompatActivity() {

  private lateinit var insertStudentBtn: Button
  private lateinit var nextFrameBtn: Button
  private lateinit var manyFrameBtn: Button
  private lateinit var insertManyStuBtn: Button
  private lateinit var currTimeView: TextView
  private lateinit var autoTimeBtn: Button
  private lateinit var resultBtn: Button

  private lateinit var mHealthQueue: RecyclerView
  private lateinit var mRunQueue: RecyclerView
  private lateinit var mJumpQueue: RecyclerView
  private lateinit var mLiftQueue: RecyclerView
  private lateinit var mHealthAdapter: StudentAdapter
  private lateinit var mRunAdapter: StudentAdapter
  private lateinit var mJumpAdapter: StudentAdapter
  private lateinit var mLiftAdapter: StudentAdapter

  private var currTime = 0

  private val game = Events.game

  private val handler = Handler(Looper.getMainLooper())

  private val autoTimeFlowTask = object : Runnable {
    override fun run() {
      game.performGameCycle()
      currTime++
      refreshUI()
      handler.postDelayed(this, 100)
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
    game.initGame()
    initViews()
    initRecyclerViews()
    insertStudentBtn.setOnClickListener {
      game.insertNewStudent()
    }
    insertManyStuBtn.setOnClickListener {
      repeat(50) { game.insertNewStudent() }
    }
    nextFrameBtn.setOnClickListener {
      game.performGameCycle()
      currTime++
      refreshUI()
    }
    manyFrameBtn.setOnClickListener {
      repeat(50) { game.performGameCycle(); currTime++ }
      refreshUI()
    }
    autoTimeBtn.setOnClickListener {
      handler.post(autoTimeFlowTask)
    }
    resultBtn.setOnClickListener {
      val intent = Intent(this, ResultActivity::class.java)
      startActivity(intent)
    }
  }

  private fun initViews() {
    insertStudentBtn = findViewById(R.id.insert_student)
    nextFrameBtn = findViewById(R.id.next_frame)
    manyFrameBtn = findViewById(R.id.many_frame)
    insertManyStuBtn = findViewById(R.id.insert_many_student)
    mHealthQueue = findViewById(R.id.queue_health)
    mRunQueue = findViewById(R.id.queue_run)
    mJumpQueue = findViewById(R.id.queue_jump)
    mLiftQueue = findViewById(R.id.queue_lift)
    currTimeView = findViewById(R.id.curr_time)
    autoTimeBtn = findViewById(R.id.auto_time)
    currTimeView.text = currTime.toString()
    resultBtn = findViewById(R.id.result_btn)
  }

  private fun initRecyclerViews() {
    val healthLayoutManager = LinearLayoutManager(this)
    val runLayoutManager = LinearLayoutManager(this)
    val jumpLayoutManager = LinearLayoutManager(this)
    val liftLayoutManager = LinearLayoutManager(this)
    mHealthAdapter = StudentAdapter(Events.basicHealthEvent)
    mRunAdapter = StudentAdapter(Events.runEvent)
    mJumpAdapter = StudentAdapter(Events.jumpEvent)
    mLiftAdapter = StudentAdapter(Events.liftEvent)

    Events.basicHealthEvent.adapter = mHealthAdapter
    Events.runEvent.adapter = mRunAdapter
    Events.jumpEvent.adapter = mJumpAdapter
    Events.liftEvent.adapter = mLiftAdapter

    mHealthQueue.layoutManager = healthLayoutManager
    mRunQueue.layoutManager = runLayoutManager
    mJumpQueue.layoutManager = jumpLayoutManager
    mLiftQueue.layoutManager = liftLayoutManager

    mHealthQueue.adapter = mHealthAdapter
    mRunQueue.adapter = mRunAdapter
    mJumpQueue.adapter = mJumpAdapter
    mLiftQueue.adapter = mLiftAdapter

  }

  private fun refreshUI() {

//    mHealthAdapter.updateNewList(Events.basicHealthEvent.currStudents)
//    mRunAdapter.updateNewList(Events.runEvent.currStudents)
//    mJumpAdapter.updateNewList(Events.jumpEvent.currStudents)
//    mLiftAdapter.updateNewList(Events.liftEvent.currStudents)
    currTimeView.text = currTime.toString()
  }

  private fun checkAdapter(adapter: StudentAdapter) {
    if (adapter.event.hasStuIn && adapter.event.hasStuOut) {

    }
  }

}

/**
 * 对于每一个人，他选择项目之前要做的：
 * 1. 看自己的分数是否达到自己希望的分数。
 * 2. 没达到，他会看一看自己最擅长的项目有多擅长。
 * 3. 如果很擅长，选
 */