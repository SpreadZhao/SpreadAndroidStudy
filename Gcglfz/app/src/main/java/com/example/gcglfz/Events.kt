package com.example.gcglfz

import java.util.LinkedList

object Events {
  val basicHealthEvent = TestEvent(
    EventType.BASIC_HEALTH, "身高体重", 10, 15, LinkedList(), 30.0
  )
  val runEvent = TestEvent(
    EventType.RUN, "800/1000米", 90, 40, LinkedList(), 210.0
  )
  val jumpEvent = TestEvent(
    EventType.JUMP, "立定跳远", 60, 20, LinkedList(), 50.0
  )
  val liftEvent = TestEvent(
    EventType.LIFT_SELF, "引体向上", 100, 25, LinkedList(), 60.0
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