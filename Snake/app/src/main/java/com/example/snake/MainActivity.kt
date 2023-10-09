package com.example.snake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameView = findViewById(R.id.game)
        val upBtn = findViewById<Button>(R.id.up_btn)
        val downBtn = findViewById<Button>(R.id.down_btn)
        val leftBtn = findViewById<Button>(R.id.left_btn)
        val rightBtn = findViewById<Button>(R.id.right_btn)
        upBtn.setOnClickListener(this)
        downBtn.setOnClickListener(this)
        leftBtn.setOnClickListener(this)
        rightBtn.setOnClickListener(this)
        gameView.startGame()
    }

    /**
     * TODO: 当行进方向为上、下时，只能左右转弯；
     *       当行进方向为左、右时，只能上下转弯
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.up_btn -> gameView.turn(Direction.UP)
            R.id.down_btn -> gameView.turn(Direction.DOWN)
            R.id.left_btn -> gameView.turn(Direction.LEFT)
            R.id.right_btn -> gameView.turn(Direction.RIGHT)
        }
    }
}