package com.example.gcglfz

import android.util.Log

class Student(
  val id: Int,
  val potential: Map<EventType, Int>,
  val expectedScore: Int,  // 期望的总分，达到这个分就不排队了
  val time: Map<EventType, Int>,  // 测每个项目需要的时间
  val expectedLineLength: Int,
  var status: StuStatus,
  var currentEvent: EventType,  // 正在进行的项目
  var finishedEvent: MutableMap<EventType, Boolean>,
  var currentScore: Int,   // 已经得了多少分
) {

  // 下一个要选的项目
  var nextEvent: EventType = EventType.NO_EVENT

  val isInEvent: Boolean
    get() = status == StuStatus.IN_EVENT

  // 一个同学在做出选择时的回调
  fun onMakeDecision(events: List<TestEvent>) {
    val unFinishedEvents = events.filter { finishedEvent[it.type] == false }
    val eventScoreMap = mutableMapOf<EventType, Double>()
    unFinishedEvents.forEach {
      // 成常人排队就是这么想的：前面每个人花的时间都差不多，不能以上帝视角来看。
      val lineScore = BASIC_SCORE - it.currStudents.size * 4
      val preferenceScore = (potential[it.type])?.toDouble()?.div(it.totalScore)?.times(BASIC_SCORE) ?: 80.0
      eventScoreMap[it.type] = lineScore.toDouble() + preferenceScore
      Log.d("track", "项目[${it.name}]得分：${eventScoreMap[it.type]}, 队伍长度分: $lineScore, 偏好分: $preferenceScore")
    }
    val sortedEvents = unFinishedEvents.sortedWith { e1, e2 ->
      (eventScoreMap[e1.type]?.let { eventScoreMap[e2.type]?.minus(it) })?.toInt() ?: 0
    }
    nextEvent = sortedEvents.takeIf { it.isNotEmpty() }?.first()?.type ?: EventType.NO_EVENT
    Log.d("track", "同学${id}选择的项目是：${Events.getName(nextEvent)}\n===================")
  }

  private fun avgPotentialInCurrEvents(currEvents: List<TestEvent>): Int {
    var sum = 0
    currEvents.forEach { sum += potential[it.type] ?: 0 }
    return sum / currEvents.size
  }

  // 被放到缓存里的回调
  fun onInCache() {
    status = StuStatus.IN_CACHE
  }

  // 正在排队时的回调
  fun onInLine(event: TestEvent) {
    status = StuStatus.IN_LINE
    currentEvent = event.type
  }

  // 刚轮到自己测试时的回调
  fun onInEvent(event: TestEvent) {
    status = StuStatus.IN_EVENT
    event.currLeftTime = time[event.type] ?: 0
  }

  // 每个单位时间正在进行Event的学生的回调
  fun onDoEvent(event: TestEvent) {
    event.currLeftTime--
  }

  // 学生完成一个Event的那一刻的回调
  fun onFinishEvent(event: TestEvent, score: Int) {
    currentScore += score
    finishedEvent[event.type] = true
  }

  fun onLeaveEvent(event: TestEvent) {
    status = if (currentScore >= expectedScore) StuStatus.ALL_FINISH else StuStatus.PRE_IN_CACHE
  }

  companion object {
    private const val BASIC_SCORE = 100 // 打分的时候的基准分
  }
}

enum class StuStatus {
  INITIAL, PRE_IN_CACHE, IN_CACHE, IN_LINE, IN_EVENT, ALL_FINISH
}

/**
 * 学生选择一个队伍的顾虑：
 * 1. 期望总分，达到了就不排队了；
 * 2. 队伍的长度，如果队伍超过期望的长度，优先级靠后
 * 3. 难度/擅长程度：如果很擅长（>= threshold），选擅长的；如果不擅长，选难度最低的
 *     1. 队伍长度 2. 是莽还是怂 3. 根据莽还是怂 选择 -> 给还没完成的项目排序，选择第一名 4. 自己完成项目的时间
 */