package com.example.gcglfz

import com.example.gcglfz.Events.basicHealthEvent
import com.example.gcglfz.Events.jumpEvent
import com.example.gcglfz.Events.liftEvent
import com.example.gcglfz.Events.runEvent
import java.util.LinkedList
import java.util.Queue

/**
 * 每一帧要做的事情：
 * 1. 判断是否有学生被插入 -> 缓存里是否有学生
 * 2. 如果有被插入的学生，将它们分配到队列里
 * 3. 如果队头的学生不处于体测状态(currEvent != NO_EVENT)，置为当前的体测项目
 * 3. 依次消费队头的学生，每个人time--
 * 4. 判断队头的学生是否已经完成项目
 * 5. 对完成的学生计算分数，对学生完成的项目进行更新（总分，完成情况）
 * 6. 让完成的学生离队 -> 缓存里
 * TODO: 体测的整个流程，前端实现，对数据的统计
 */
class TestGame {

  val events = mutableListOf<TestEvent>()

  private val cache: Queue<Student> = LinkedList()

  val finishedStudents = arrayListOf<Student>()

  fun initGame() {
    initEvents()
  }

  fun performGameCycle() {
    arrangeStudents()
    doEvent()
  }

  fun insertNewStudent() {
    val student = generateStudent()
    enCache(student)
  }

  private fun initEvents() {
    events.add(basicHealthEvent)
    events.add(runEvent)
    events.add(jumpEvent)
    events.add(liftEvent)
  }



  private val haveStuInCache: Boolean
    get() = !cache.isEmpty()

  private fun arrangeStudents() {
    while (haveStuInCache) {
      val stu = cache.poll() ?: break
      stu.onMakeDecision(events)
      if (stu.nextEvent == EventType.NO_EVENT) {
        // 做完所有项目还不及格的人
        finishedStudents.add(stu)
      }
      enQueue(stu, getEventByType(stu.nextEvent))
    }
  }

  private fun doEvent() {
    for (event in events) {
      val head = if (event.currStudents.isNotEmpty()) event.currStudents.element() else null
      head?.let {
        if (!it.isInEvent) it.onInEvent(event)
        it.onDoEvent(event)
        if (event.currLeftTime == 0) {
          val score = generateScore(it, event)
          it.onFinishEvent(event, score)
          it.onLeaveEvent(event)
          val theHead = event.currStudents.poll()
          event.addFlag(STU_LINE_FLAG_POLL)
          event.adapter.notifyItemRemoved(0)
          if (it.status == StuStatus.PRE_IN_CACHE && it === theHead) {
            enCache(it)
          } else if (it.status == StuStatus.ALL_FINISH && it === theHead) {
            finishedStudents.add(it)
          }
        }
      }
    }
  }

  private fun enCache(stu: Student) {
    cache.offer(stu)
    stu.onInCache()
  }

  private fun enQueue(stu: Student, event: TestEvent?) {
    event?.apply {
      currStudents.offer(stu)
      addFlag(STU_LINE_FLAG_OFFER)
      adapter.notifyItemInserted(currStudents.size - 1)
      stu.onInLine(this)
    }
  }

  private fun getEventByType(type: EventType) = when (type) {
    EventType.BASIC_HEALTH -> basicHealthEvent
    EventType.RUN -> runEvent
    EventType.JUMP -> jumpEvent
    EventType.LIFT_SELF -> liftEvent
    else -> null
  }

  companion object {

    const val STU_LINE_FLAG_OFFER = 1

    const val STU_LINE_FLAG_POLL = 1 shl 1

    val TestEvent.hasStuIn: Boolean
      get() = stuChangeFlag and STU_LINE_FLAG_OFFER != 0

    val TestEvent.hasStuOut: Boolean
      get() = stuChangeFlag and STU_LINE_FLAG_POLL != 0

    fun TestEvent.addFlag(flag: Int) {
      stuChangeFlag = stuChangeFlag or flag
    }

    @Volatile
    var nextStuId = 1
    fun generateStudent(): Student {
      /**
       * potential是随机的（正常发挥，超长发挥，失常发挥），time和potential有函数关系
       * @see getTimeByEvent
       */
      val potential = mutableMapOf<EventType, Int>().apply {
        put(EventType.BASIC_HEALTH, getRandomPotentialByEvent(EventType.BASIC_HEALTH))
        put(EventType.RUN, getRandomPotentialByEvent(EventType.RUN))
        put(EventType.JUMP, getRandomPotentialByEvent(EventType.JUMP))
        put(EventType.LIFT_SELF, getRandomPotentialByEvent(EventType.LIFT_SELF))
      }
      val time = mutableMapOf<EventType, Int>().apply {
        put(EventType.RUN, getTimeByEvent(EventType.RUN, potential))
        put(EventType.BASIC_HEALTH, getTimeByEvent(EventType.BASIC_HEALTH, potential))
        put(EventType.JUMP, getTimeByEvent(EventType.JUMP, potential))
        put(EventType.LIFT_SELF, getTimeByEvent(EventType.LIFT_SELF, potential))
      }
      val expectedScore = if (lowChance(20)) (61..100).random() else (50..60).random()
      val expectedLineLength = Int.MAX_VALUE
      return Student(
        id = nextStuId++,
        potential = potential,
        expectedScore = expectedScore,
        time = time,
        expectedLineLength = expectedLineLength,
        status = StuStatus.INITIAL,
        currentEvent = EventType.NO_EVENT,
        finishedEvent = mutableMapOf(
          EventType.BASIC_HEALTH to false,
          EventType.RUN to false,
          EventType.JUMP to false,
          EventType.LIFT_SELF to false
        ),
        currentScore = 0
      )
    }

    private fun getTimeByEvent(type: EventType, potential: Map<EventType, Int>): Int {
      if (!potential.contains(type)) return -1
      return when (type) {
        EventType.RUN -> getNegativeCorrelationTime(runEvent, potential, type).toInt()
        EventType.BASIC_HEALTH -> getCasualTime(basicHealthEvent).toInt()
        EventType.JUMP -> getCasualTime(jumpEvent).toInt()
        EventType.LIFT_SELF -> getPositiveCorrelationTime(liftEvent, potential, type).toInt()
        else -> -1
      }
    }

    // 正相关的时间计算：potential越高，项目耗时越长。比如引体向上
    private fun getPositiveCorrelationTime(event: TestEvent, potential: Map<EventType, Int>, type: EventType)
      = event.basicTime * (potential[type]!! / (event.totalScore).toDouble())

    // 负相关的时间计算：potential越高，耗时越低，比如800，1000
    private fun getNegativeCorrelationTime(event: TestEvent, potential: Map<EventType, Int>, type: EventType)
      = event.basicTime * ((event.totalScore).toDouble() / potential[type]!!)

    // 跳远，身高体重每个人的时间都差不多，直接一样
    private fun getCasualTime(event: TestEvent) = event.basicTime

    // 通过测试项目得到一个同学的一个潜力值，这个是随机的。
    private fun getRandomPotentialByEvent(type: EventType) = when (type) {
      EventType.BASIC_HEALTH -> {
        if (lowChance(8)) (5..9).random()
        (10..15).random()
      }
      EventType.RUN -> {
        if (lowChance(3)) (30..40).random()
        if (lowChance(10)) (15..23).random()
        (24..30).random()
      }
      EventType.JUMP -> {
        if (lowChance(5)) (18..20).random()
        (12..17).random()
      }
      EventType.LIFT_SELF -> {
        if (lowChance(20)) (20..25).random()
        (5..19).random()
      }
      else -> -1
    }

    // howLow越大，命中的概率越高，倒霉/幸运的概率越高，模拟发挥超长/失常的那批人
    private fun lowChance(howLow: Int): Boolean {
      val num = (1..100).random()
      return num !in howLow..100
    }

    fun generateScore(stu: Student, event: TestEvent): Int {
      val baseScore = stu.potential[event.type]
      return baseScore?.let {
        val diff = if (lowChance(20)) (-10..10).random() else (-5..5).random()
        if (baseScore + diff > event.totalScore) event.totalScore
        else if (baseScore + diff < 0) 0
        else baseScore + diff
      } ?: 0
    }
  }
}