package com.example.gcglfz

import java.util.Queue


// 测试项目的基类，代表每一个项目
class TestEvent(
  val type: EventType,
  val name: String,
  val difficulty: Int,   // 0 - 100
  val totalScore: Int,
  var currStudents: Queue<Student>,
  val basicTime: Double,  // 测试这个项目满分的时间
  var currLeftTime: Int = 0,  // 当前正在测这个项目的同学，还剩下的时间
  var stuChangeFlag: Int = 0
) {
  lateinit var adapter: StudentAdapter
}

enum class EventType {
  NO_EVENT, BASIC_HEALTH, RUN, JUMP, LIFT_SELF
}