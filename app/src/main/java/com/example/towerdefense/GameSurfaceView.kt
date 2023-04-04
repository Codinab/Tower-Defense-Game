package com.example.towerdefense

import GameObjectList
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
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

class GameSurfaceView(context: Context, private val gameView: GameView) : SurfaceView(context) {

    var gameObjectList = GameObjectList()
    var movableTowers = GameObjectList()
    var towers = Vector<Tower>()
    var towerSpawners = Vector<TowerSpawner>()
    var gameLoop: GameLoop? = null
    var isRunning = false
    private var road: Road

    init {

        val multiVector = MultiVector(Vector2i(0, 0))
        multiVector.addLine(Vector2i(0, 0), Direction2D.DOWN, 10)
        multiVector.addLine(Vector2i(0, 10), Direction2D.RIGHT, 10)
        multiVector.addLine(Vector2i(10, 10), Direction2D.UP, 10)
        road = Road(Vector2i(0, 0), multiVector)
        val enemy = Enemy(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road)
        gameObjectList.add(enemy)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return

        //towerSpawners.forEach { it.draw(canvas) }

        camera.updateCanvas(canvas)

        gameObjectList.draw(canvas)
        towers.forEach { it.draw(canvas) }

        road.draw(canvas)

        if (fps) drawUPS(canvas)
        if (fps) drawFPS(canvas)
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!gameView.isRunning()) {
            gameView.start()
            gameView.gamePause()
            val towerSpawner =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 100f))
                    ), this, context, gameView
                )

            addTowerSpawner(towerSpawner)

            val towerSpawner2 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 210f))
                    ), this, context, gameView
                )
            towerSpawner2.towerDPS = 20
            addTowerSpawner(towerSpawner2)

            val towerSpawner3 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 320f))
                    ), this, context, gameView
                )
            towerSpawner3.towerDPS = 30
            addTowerSpawner(towerSpawner3)

            val towerSpawner4 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 100f, 430f))
                    ), this, context, gameView
                )
            towerSpawner4.towerDPS = 40
            addTowerSpawner(towerSpawner4)

            val towerSpawner5 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 100f))
                    ), this, context, gameView
                )
            towerSpawner5.towerDPS = 50
            addTowerSpawner(towerSpawner5)

            val towerSpawner6 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 210f))
                    ), this, context, gameView
                )
            towerSpawner6.towerDPS = 60
            addTowerSpawner(towerSpawner6)

            val towerSpawner7 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 320f))
                    ), this, context, gameView
                )
            towerSpawner7.towerDPS = 70
            addTowerSpawner(towerSpawner7)

            val towerSpawner8 =
                TowerSpawner(
                    Box2D(
                        Vector2f(100f, 100f),
                        Rigidbody2D(Vector2f(screenSize.x.toFloat() - 210f, 430f))
                    ), this, context, gameView
                )
            towerSpawner8.towerDPS = 80
            addTowerSpawner(towerSpawner8)

            Thread {
                while (gamePaused()) {
                    Thread.sleep(100)
                }
                while (true) {
                    if (gamePaused()) {
                        Thread.sleep(100)
                        continue
                    }
                    Thread.sleep(5000)
                    val enemy = Enemy(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road)
                    gameObjectList.add(enemy)
                }
            }.start()

        }

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
        if (gamePaused == null) gamePaused = false
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

    fun update() {
        updateGameObjects()
        enemyInTowerRange()
    }


    private fun updateGameObjects() {
        towerSpawners.forEach() { it.update() }
        towers.forEach() { it.update() }
        gameObjectList.update().forEach { towers.forEach { tower -> tower.towerArea.remove(it) } }
        movableTowers.forEach { movableTower ->
            if (!movableTower.movable.get()) movableTowers.remove(movableTower)
            var fixable = true
            towers.forEach { tower ->
                println("Compare movable: ${movableTower.position()} with ${tower.position()}")
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
            "UPS: $averageUPS", 100f + camera.x(), 100f + camera.y(), paint
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
}