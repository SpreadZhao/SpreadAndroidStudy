package com.example.gcglfz

import java.util.ArrayDeque

object Events {
  val basicHealthEvent = TestEvent(
    EventType.BASIC_HEALTH, "身高体重", 10, 15, ArrayDeque(), 30.0
  )
  val runEvent = TestEvent(
    EventType.RUN, "800/1000米", 90, 40, ArrayDeque(), 210.0
  )
  val jumpEvent = TestEvent(
    EventType.JUMP, "立定跳远", 60, 20, ArrayDeque(), 50.0
  )
  val liftEvent = TestEvent(
    EventType.LIFT_SELF, "引体向上", 100, 25, ArrayDeque(), 60.0
  )
  fun getName(type: EventType) = when (type) {
    EventType.RUN -> runEvent.name
    EventType.BASIC_HEALTH -> basicHealthEvent.name
    EventType.JUMP -> jumpEvent.name
    EventType.LIFT_SELF -> liftEvent.name
    else -> "NO_EVENT"
  }
  val game = TestGame()
}