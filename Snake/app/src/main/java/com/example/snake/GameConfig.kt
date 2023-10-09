package com.example.snake

import android.graphics.Color

enum class EntityType {
    GRID, // 地图网格
    FOOD, // 食物
    HEAD, // 蛇头
    BODY  // 蛇身
}

enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

data class GameEntity(var type: EntityType)

// 根据实体的类型得到它们的颜色
fun getColorByEntity(type: EntityType) = when (type) {
    EntityType.GRID -> Color.BLUE
    EntityType.FOOD -> Color.YELLOW
    EntityType.HEAD -> Color.RED
    EntityType.BODY -> Color.GRAY
}