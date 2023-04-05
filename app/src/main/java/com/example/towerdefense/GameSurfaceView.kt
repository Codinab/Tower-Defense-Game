package com.example.towerdefense

import GameObjectList
import TowerList
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.*
import com.example.towerdefense.utility.*
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.*

open class GameSurfaceView(context: Context, private val gameView: GameView) : SurfaceView(context),
    SurfaceHolder.Callback {

    var gameObjectList = GameObjectList()
    var movableTowers = GameObjectList()
    var towers = TowerList()
    var towerSpawners = Vector<TowerSpawner>()
    var gameLoop: GameLoop? = null
    var isRunning = false
    private var road: Road

    init {

        val multiVector = MultiVector(Vector2i(0, 0))
        multiVector.addLine(Direction2D.RIGHT, 10)
        multiVector.addLine(Direction2D.DOWN, 2)
        multiVector.addLine(Direction2D.RIGHT, 2)
        multiVector.addLine(Direction2D.UP, 20)
        road = Road(Vector2i(0, 0), multiVector)


    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return

        //towerSpawners.forEach { it.draw(canvas) }

        camera.updateCanvas(canvas)
        road.draw(canvas)

        gameObjectList.draw(canvas)
        towers.forEach { it.draw(canvas) }

        if (fps) drawUPS(canvas)
        if (fps) drawFPS(canvas)
        drawMoney(canvas)
        drawGameHealth(canvas)
        drawGameTime(canvas)
    }

    val camera = Camera()


    override fun onTouchEvent(event: MotionEvent): Boolean {
        val adjustedPosition = camera.adjustedPosition(event)
        val movable = gameObjectList.getMovable()
        val clicked = gameObjectList.getClicked(adjustedPosition)

        if (!isRunning) return false

        towerSpawners.forEach() {
            if (it.isClicked(adjustedPosition)) if (it.onTouchEvent(
                    event, adjustedPosition
                )
            ) return true
        }
        towers.forEach() {
            if (it.isClicked(adjustedPosition) || it.movable.get()) if (it.onTouchEvent(
                    event, adjustedPosition
                )
            ) return true
        }

        //Check if the child view is clicked
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (clicked.isNotEmpty()) clicked[0].onTouchEvent(event, adjustedPosition)
                else camera.touch(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (movable.isNotEmpty()) return movable[0].onTouchEvent(event, adjustedPosition)
                else if (clicked.isNotEmpty()) return true
                else if (camera.moving) camera.update(event)
            }
            MotionEvent.ACTION_UP -> {
                if (movable.isNotEmpty()) return movable[0].onTouchEvent(event, adjustedPosition)
                camera.moving = false
            }
        }
        return true
    }

    fun initTowerSpawners() {
        val towerSpawner =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 100f))
                ), this, context, gameView
            )
        towerSpawner.damageType = TowerArea.DamageType.LEAST_HEALTH
        addTowerSpawner(towerSpawner)

        val towerSpawner2 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 210f))
                ), this, context, gameView
            )
        towerSpawner2.towerDPS = 2
        addTowerSpawner(towerSpawner2)

        val towerSpawner3 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 320f))
                ), this, context, gameView
            )
        towerSpawner3.towerDPS = 3
        addTowerSpawner(towerSpawner3)

        val towerSpawner4 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 430f))
                ), this, context, gameView
            )
        towerSpawner4.towerDPS = 4
        addTowerSpawner(towerSpawner4)

        val towerSpawner5 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 100f))
                ), this, context, gameView
            )
        towerSpawner5.towerDPS = 5
        addTowerSpawner(towerSpawner5)

        val towerSpawner6 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 210f))
                ), this, context, gameView
            )
        towerSpawner6.towerDPS = 6
        addTowerSpawner(towerSpawner6)

        val towerSpawner7 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 320f))
                ), this, context, gameView
            )
        towerSpawner7.towerDPS = 7
        addTowerSpawner(towerSpawner7)

        val towerSpawner8 =
            TowerSpawner(
                Box2D(
                    Vector2f(100f, 100f),
                    Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 430f))
                ), this, context, gameView
            )
        towerSpawner8.towerDPS = 8
        addTowerSpawner(towerSpawner8)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addTowerSpawner(towerSpawner: TowerSpawner) {
        towerSpawners.add(towerSpawner)
        gameView.addView(towerSpawner)
        towerSpawner.requestLayout()
        gameView.invalidate()
        towerSpawner.setOnTouchListener() { _, it ->
            towerSpawner.onTouchEvent(it, camera.adjustedPosition(it))
            performClick()
        }
    }


    private var gamePaused: Boolean? = null
    fun gamePaused(): Boolean {
        if (gamePaused == null) gamePaused = true
        return gamePaused!!
    }

    fun gamePause() {
        gamePaused = true
        towers.forEach { it.paused = true }
        gameObjectList.forEach { if (it is Enemy) it.paused = true }
    }

    fun gameResume() {
        gamePaused = false
        towers.forEach { it.paused = false }
        gameObjectList.forEach { if (it is Enemy) it.paused = false }
    }

    private var timeLastSpawn = 0L
    var health = 10
    fun update() {
        if (gameHealth.get() <= 0) gameEnd()
        updateGameObjects()
        enemyInTowerRange()

        if (TimeController.getGameTime() - timeLastSpawn > 1000) {
            gameObjectList.add(
                Enemy(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road).apply {
                    this.setHealth(health)
                }
            )
            timeLastSpawn = TimeController.getGameTime()
            health = (health * 1.1).toInt()
        }
    }

    private fun gameEnd() {
        gameView.gamePause()
    }


    private fun updateGameObjects() {
        towerSpawners.forEach() { it.update() }
        towers.forEach() { it.update() }
        gameObjectList.update().forEach { towers.forEach { tower -> tower.towerArea.remove(it) } }

        movableTowers.forEach { movableTower ->
            if (!movableTower.movable.get()) movableTowers.remove(movableTower)
            var fixable = true
            towers.forEach { tower ->
                if (movableTower != tower) {
                    if (IntersectionDetector2D.intersection(
                            movableTower.collider2D(),
                            tower.collider2D()
                        )
                    ) {
                        fixable = false
                        return@forEach
                    }
                }
            }
            if (fixable) movableTower.fixable.set(true)
            else movableTower.fixable.set(false)
        }
    }

    private fun enemyInTowerRange() {
        towers.forEach { tower ->
            gameObjectList.forEach { enemy ->
                if (enemy is Enemy) {
                    tower.towerArea.isInside(enemy)
                }
            }
        }
    }

    fun drawUPS(canvas: Canvas?) {
        gameLoop ?: return
        val averageUPS = gameLoop!!.averageUPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "UPS: $averageUPS", 100f + camera.x(), 300f + camera.y(), paint
        )
    }

    fun drawFPS(canvas: Canvas?) {
        gameLoop ?: return
        val averageFPS = gameLoop!!.averageFPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "FPS: $averageFPS", 100f + camera.x(), 200f + camera.y(), paint
        )
    }

    fun drawMoney(canvas: Canvas?) {
        val color = Color.CYAN
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "Money: $money", 100f + camera.x(), 100f + camera.y(), paint
        )
    }

    private fun drawGameTime(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 50f
        canvas.drawText("Time: ${TimeController.getGameTime() / 1000} ${TimeController.getSinceAppStart() / 1000}", camera.x() + 100, camera.y() + 200f, paint)
    }

    private fun drawGameHealth(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 50f
        canvas.drawText("Health: $gameHealth", camera.x() + 100f, camera.y() + 300f, paint)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }
}