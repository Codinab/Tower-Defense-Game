package com.example.towerdefense

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.content.res.ResourcesCompat
import java.util.*
import kotlin.system.exitProcess


class GameView(mainActivity: MainActivity, game: Game) : View(mainActivity) {
    var UPDATE_MILLIS = 30
    var textPaint = Paint()
    var healthPaint = Paint()
    var TEXT_SIZE = 120
    var game = game


    companion object {
        var SCREEN_WIDTH = 0
        var SCREEN_HEIGHT = 0
    }

    var windowManager = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)

    val SCREEN_WIDTH = windowManager.defaultDisplay.width
    val SCREEN_HEIGHT = windowManager.defaultDisplay.height
    var rectBackGround = Rect(0, 0, SCREEN_HEIGHT, SCREEN_WIDTH)
    //var tower = BitmapFactory.decodeResource(resources, R.drawable.tower) as Bitmap

    var runnable = Runnable {
        run { invalidate() }
    }
    init {
        textPaint.setColor(Color.rgb(255, 165, 0))
        textPaint.setTextSize(TEXT_SIZE.toFloat())
        textPaint.setTextAlign(Paint.Align.LEFT)
        //textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.test))
        healthPaint.setColor(Color.rgb(20, 255, 20))

    }

    var random = Random()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var health = game.health
        var money = game.money

        //canvas?.drawBitmap(tower, null, rectBackGround, null)
        canvas?.drawText("Money: $money", 0f, 100f, textPaint)
        canvas?.drawText("Health: $health", 0f, 200f, textPaint)


        canvas?.drawRect(0f, 300f, 100f, 400f, healthPaint)
        postDelayed(runnable, UPDATE_MILLIS.toLong())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Update canva

        if (event?.action == MotionEvent.ACTION_DOWN) {
            game.health--
            if (game.health <= 0) {
                game.money *= 2
                game.saveToBinaryFile(context)
                var newGame = Game.fromBinaryFile(game.fileName, context)!!
                var gameView = GameView(context as MainActivity, newGame)
                (context as MainActivity).setContentView(gameView)
            }
        }
        return true
    }


}