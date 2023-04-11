package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.textures.Animation
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f

class ExplosiveRocket(var circle: Circle, var enemy: Enemy) : GameObject(circle, false, false), Projectile {
    
    private var timeToLive = 4000f
    private val spawnTime = TimeController.getGameTime()
    private var animation : Animation
    
    init {
        val frame1 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete)
        val frame2 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete2)
        val frame3 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete3)
        val frames = arrayOf(frame1, frame2, frame3)
        animation = Animation(frames, 100f)
    }
    override fun update() {
        if (toDelete() || TimeController.isPaused()) return
        super.update()
        if (TimeController.getGameTime() > spawnTime + timeToLive && !explosion) {
            generateExplosion()
        }
        if (TimeController.getGameTime() > timeToDestroy) {
            destroy()
        }
        if (enemy.toDelete()) generateExplosion()
        else circle.body.rotation = Vector2f(enemy.position()).sub(circle.body.position).normalize().angle()
        gameView!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider2D())) {
                if (!explosion) generateExplosion()
                else it.damage(100)
            }
        }
    }
    
    private var timeToDestroy: Float = Float.MAX_VALUE
    private fun destroyInSeconds(d: Float) {
        timeToDestroy = TimeController.getGameTime().toFloat() + d
    }
    
    private var explosion = false
    
    private fun generateExplosion() {
        velocity(100f / gameVelocity)
        explosion = true
        circle.radius = circle.radius * 3
        collider2D().update()
        velocity(0f)
        destroyInSeconds(15.5f)
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        //super.draw(canvas)
        if (explosion) drawExplosion(canvas)
        else animation.draw(canvas, circle.body.position, getRotation())
    }
    
    private fun drawExplosion(canvas: Canvas) {
        circle.draw(canvas)
    }
    
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
    

}