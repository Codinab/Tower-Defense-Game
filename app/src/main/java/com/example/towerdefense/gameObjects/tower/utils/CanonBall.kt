package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.Drawing
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import org.joml.Vector2f

class CanonBall(var circle: Circle, private var damage : Int) : GameObject(circle, false, false) {
    
    private var pause: Boolean = false
    private var toDelete = false
    private var timeToLive = 4000L
    private val spawnTime = TimeController.getGameTime()
    private var damagedEnemies = ArrayList<Enemy>()
    override fun update() {
        if (toDelete || pause) return
        super.update()
        if (TimeController.getGameTime() - spawnTime > timeToLive) {
            destroy()
        }
        gameView!!.surfaceView.enemies.forEach { enemy ->
            if (IntersectionDetector2D.intersection(circle, enemy.collider2D())) {
                if (!damagedEnemies.contains(enemy)) {
                    enemy.damage(damage)
                    damagedEnemies.add(enemy)
                }
            }
        }
    }

    override fun draw(canvas: Canvas) {
        if (toDelete) return
        //super.draw(canvas)
        //Draw cohete.png in drawable
        val bitmap = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete)
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            isDither = true
        }
        Drawing.drawBitmap(canvas, bitmap, position(),  paint, getRotation())
    }

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
    fun pause() {
        pause = true
    }
    fun resume() {
        pause = false
    }
}