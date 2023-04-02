package com.example.towerdefense

import GameObjectList
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.*
import com.example.towerdefense.utility.Direction2D
import com.example.towerdefense.utility.MultiVector
import com.example.towerdefense.utility.Road
import org.joml.Vector2f
import org.joml.Vector2i

class GameSurfaceView(context: Context) : SurfaceView(context) {

    var gameObjectList = GameObjectList()
    var towers = ArrayList<Tower>()
    var towerSpawners = ArrayList<TowerSpawner>()
    var gameLoop: GameLoop? = null
    var isRunning = false
    private var road: Road
    init {

        val towerSpawner = TowerSpawner(Box2D(Vector2f(100f, 100f), Rigidbody2D(Vector2f(500f, 500f))), this)
        towerSpawners.add(towerSpawner)

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

        camera.updateCanvas(canvas)

        gameObjectList.draw(canvas)
        towerSpawners.forEach() { it.draw(canvas) }
        towers.forEach() { it.draw(canvas) }

        road.draw(canvas)

        drawUPS(canvas)
        drawFPS(canvas)
    }

    private val camera = Camera()
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val adjustedPosition = camera.adjustedPosition(event)
        val movable = gameObjectList.getMovable()
        val clicked = gameObjectList.getClicked(adjustedPosition)

        if (!isRunning) return false

        towerSpawners.forEach() {
            if(it.isClicked(adjustedPosition)) if (it.onTouchEvent(event, adjustedPosition)) return true
        }
        towers.forEach() {
            if(it.isClicked(adjustedPosition) || it.movable.get()) if (it.onTouchEvent(event, adjustedPosition)) return true
        }

        //Check if the child view is clicked
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("Clicked")
                if (clicked.isNotEmpty()) clicked[0].onTouchEvent(event, adjustedPosition)
                else camera.touch(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (movable.isNotEmpty()) return movable[0].onTouchEvent(event, adjustedPosition)
                else if (clicked.isNotEmpty()) return true
                else if(camera.moving) camera.update(event)
            }
            MotionEvent.ACTION_UP -> {
                println("Up")
                if (movable.isNotEmpty())
                    return movable[0].onTouchEvent(event, adjustedPosition)
                camera.moving = false
            }
        }
        return true
    }

    fun gamePause() {
        towers.forEach { it.paused = true }
        gameObjectList.forEach { if (it is Enemy) it.paused = true }
    }

    fun gameResume() {
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