package com.example.snake

import android.graphics.Point

class Snake(val gameSize: Int) {
    var length = 4
    var headLocation = Point(gameSize / 2, gameSize / 2)
    var tailLocation: Point
    var lastTailLocation = Point()
    var speed = 4
    val locations = arrayListOf<Point>()
    var direction = Direction.UP
    val onEatFood: () -> Unit = {
        locations.add(lastTailLocation)
        length++
    }

    init {
        locations.apply {
            add(headLocation)
            var p = getMovedPoint(headLocation, Direction.DOWN, gameSize)
            repeat(length - 1) {
                add(p)
                p = getMovedPoint(p, Direction.DOWN, gameSize)
            }
        }
        tailLocation = locations.last()
    }

    fun getBodyLocations(): List<Point> {
        return locations.subList(1, locations.size)
    }

    fun move() {
        val newHead = getMovedPoint(headLocation, direction, gameSize)
        if (length > 0) lastTailLocation = locations.removeLast()
        locations.add(0, newHead)
        headLocation = newHead
        tailLocation = locations.last()
    }


    companion object {
        fun getMovedPoint(point: Point, direction: Direction, gameSize: Int): Point {
            var x = point.x; var y = point.y
            when (direction) {
                Direction.LEFT -> {
                    x = if (point.x - 1 < 0) {
                        gameSize - 1
                    } else {
                        point.x - 1
                    }
                }
                Direction.RIGHT -> {
                    x = if (point.x + 1 >= gameSize) {
                        0
                    } else {
                        point.x + 1
                    }
                }
                Direction.UP -> {
                    y = if (point.y - 1 < 0) {
                        gameSize - 1
                    } else {
                        point.y - 1
                    }
                }
                Direction.DOWN -> {
                    y = if (point.y + 1 >= gameSize) {
                        0
                    } else {
                        point.y + 1
                    }
                }
            }
            return Point(x, y)
        }
    }
}