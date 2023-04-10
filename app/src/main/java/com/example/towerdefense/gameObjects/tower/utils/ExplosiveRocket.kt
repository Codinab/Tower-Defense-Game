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

class ExplosiveRocket(var circle: Circle) : GameObject(circle, false, false), Projectile {
    
    private var pause: Boolean = false
    private var timeToLive = 4000L
    private val spawnTime = TimeController.getGameTime()
    override fun update() {
        if (toDelete() || pause) return
        if (explosionArea != null && TimeController.getGameTime() > timeToDestroy) {
            destroy()
            return
        }
        if (explosionArea != null) {
            gameView!!.surfaceView.enemies.forEach { enemy ->
                if (IntersectionDetector2D.intersection(explosionArea!!, enemy.collider2D()))
                    enemy.damage(Int.MAX_VALUE)
            }
            return
        }
        super.update()
        if (TimeController.getGameTime() - spawnTime > timeToLive) {
            if(explosionArea == null) destroy()
        }
        gameView!!.surfaceView.enemies.forEach { enemy ->
            if (IntersectionDetector2D.intersection(circle, enemy.collider2D())) {
                setVelocity(0f)
                generateExplosion()
                destroyInSeconds(15.5f)
            }
        }
    }
    
    private var timeToDestroy : Float = 0f
    private fun destroyInSeconds(d: Float) {
        timeToDestroy = TimeController.getGameTime().toFloat() + d
    }
    
    
    var explosionArea : Circle? = null
    
    private fun generateExplosion() {
        explosionArea = Circle(100f, Vector2f(position()))
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        val bitmap = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete)
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            isDither = true
        }
        //super.draw(canvas)
        if (explosionArea != null) explosionArea!!.draw(canvas)
        else Drawing.drawBitmap(canvas, bitmap, position(),  paint, getRotation())
    }
    
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
    
    override fun pause() {
        pause = true
    }
    
    override fun resume() {
        pause = false
    }
}