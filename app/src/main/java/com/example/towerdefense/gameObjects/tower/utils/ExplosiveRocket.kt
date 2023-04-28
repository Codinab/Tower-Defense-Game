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
import com.example.towerdefense.utility.KMath.Companion.angle
import com.example.towerdefense.utility.textures.Animation
import org.joml.Vector2f

class ExplosiveRocket(var circle: Circle, var enemy: Enemy) : GameObject(circle, false, false), Projectile {
    
    private var timeToLive = 4000f
    private val spawnTime = TimeController.getGameTime()
    private var animation: Animation = Animation(explosiveRocketFrames, 100f)
    
    override fun update() {
        //If the explosion has ended, destroy the object
        println("1")
        if (TimeController.getGameTime() > explosionEndTime) destroy()
        
        println("2")
        //If the object is to be deleted or the game is paused, return
        if (toDelete() || TimeController.isPaused()) return
        
        super.update()
        
        println("3")
        if (explosionCreated) {
            explosionDamage()
            return
        }
        println("4")
        
        //If the time to live has passed and the object has not exploded, generate an explosion
        //if (TimeController.getGameTime() > spawnTime + timeToLive && !explosion) generateExplosion()
        
        //If the object has to be deleted, and it has not exploded, generate an explosion
        //if (enemy.toDelete()) generateExplosion()
        //If the object has not exploded, rotate towards the enemy
        setRotation(Vector2f(enemy.position()).sub(circle.body.position).normalize().angle())
        
        if (!explosionCreated) detectCollisions()
    }
    
    private fun explosionDamage() {
        println("Explosion damage")
        gameView!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider2D())) {
                it.damage(100)
            }
        }
    }
    
    private var explosionEndTime: Float = spawnTime + timeToLive
    
    //Detects collisions with enemies, if the object has exploded damages the enemy, if it collided with it generates an explosion
    private fun detectCollisions() {
        gameView!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider2D())) {
                generateExplosion()
                return
            }
        }
    }
    
    //Sets the time to destroy the object
    private fun destroyInSeconds(d: Float) {
        explosionEndTime = TimeController.getGameTime() + d
    }
    
    private var explosionCreated = false
    
    private fun generateExplosion() {
        velocity(100f)
        explosionCreated = true
        circle.radius = circle.radius * 3
        collider2D().update()
        velocity(0f)
        destroyInSeconds(0.5f)
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        //super.draw(canvas)
        if (explosionCreated) drawExplosion(canvas)
        else animation.draw(canvas, circle.body.position, getRotation())
    }
    
    private fun drawExplosion(canvas: Canvas) {
        circle.draw(canvas)
    }
    
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
    
    companion object {
        private val explosiveRocketFrames =
            arrayOf(
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete),
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete2),
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete3)
            )
    }
    
}