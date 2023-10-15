package com.example.snake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlin.random.Random

class GameView : View, Runnable {

    private val gameSize = 14               // 地图的长宽
    private var screenHeight = 0            // 屏幕高度
    private var screenWidth = 0             // 屏幕宽度


    private val map = arrayListOf<ArrayList<GameEntity>>()
    private val snake = Snake(gameSize)
    private var foodLocation = Point()

    private var ateCount = 0

    private val thread = Thread(this)
    private var gameStart = false

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        for (y in 0 ..< gameSize) {
            val entityList = arrayListOf<GameEntity>()
            for (x in 0 ..< gameSize) {
                entityList.add(GameEntity(EntityType.GRID))
            }
            map.add(entityList)
        }
        updateSnakeLoc()
        refreshFood()
    }

    private fun randomGenerateFood() {
        var foodLoc = generateFoodLoc()
        while (isInSnake(foodLoc)) {
            foodLoc = generateFoodLoc()
        }
        foodLocation = foodLoc
    }

    private fun generateFoodLoc() = Point(Random.nextInt(gameSize), Random.nextInt(gameSize))

    private fun refreshFood() {
        randomGenerateFood()
        map[foodLocation.y][foodLocation.x].type = EntityType.FOOD
    }

    private fun isInSnake(point: Point): Boolean {
        for (snakeBodyLoc in snake.locations) {
            if (point.x == snakeBodyLoc.x && point.y == snakeBodyLoc.y) return true
        }
        return false
    }

    private fun initPaint(x: Int, y: Int) {
        mPaint.style =
            if (map[y][x].type == EntityType.GRID) Paint.Style.STROKE
            else Paint.Style.FILL
        mPaint.color = getColorByEntity(map[y][x].type)
        if (isSnakeHead(x, y)) {
            mPaint.style = Paint.Style.FILL
            mPaint.color = getColorByEntity(EntityType.HEAD)
        }
    }

    private fun isSnakeHead(x: Int, y: Int) = x == snake.headLocation.x && y == snake.headLocation.y

    private fun updateSnakeLoc() {
        map[snake.lastTailLocation.y][snake.lastTailLocation.x].type = EntityType.GRID
        map[snake.headLocation.y][snake.headLocation.x].type = EntityType.HEAD
        for (body in snake.getBodyLocations()) {
            map[body.y][body.x].type = EntityType.BODY
        }
    }

    private fun judgeEat() {
        if (snake.headLocation.x == foodLocation.x && snake.headLocation.y == foodLocation.y) {
            snake.onEatFood.invoke()
            refreshFood()
        }
        if (eatSelf()) {
            gameStart = false
            post {
                Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun eatSelf(): Boolean {
        for (body in snake.getBodyLocations()) {
            if (snake.headLocation.x == body.x && snake.headLocation.y == body.y) {
                return true
            }
        }
        return false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w
        screenHeight = h
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val blockWidth = screenWidth / gameSize
        val blockHeight = screenHeight / gameSize

        for (y in 0 ..< gameSize) {
            for (x in 0 ..< gameSize) {
                val left = x * blockWidth.toFloat()
                val right = (x + 1f) * blockWidth
                val top = y * blockHeight.toFloat()
                val bottom = (y + 1f) * blockHeight
                initPaint(x, y)
                canvas.drawRect(left, top, right, bottom, mPaint)
            }
        }
    }

    fun turn(direction: Direction) {
        snake.direction = direction
    }

    fun startGame() {
        gameStart = true
        thread.start()
    }

    override fun run() {
        while (gameStart) {
            snake.move()
            judgeEat()
            updateSnakeLoc()
            postInvalidate()
            Thread.sleep(50)
        }
    }
}