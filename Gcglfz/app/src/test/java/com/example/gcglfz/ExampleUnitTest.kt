package com.example.gcglfz

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    val game = TestGame()
    game.initGame()
    repeat(100) {
      val student = TestGame.generateStudent()
      student.onMakeDecision(game.events)
      println("学生选择的项目是：${student.nextEvent}")
    }
  }
}