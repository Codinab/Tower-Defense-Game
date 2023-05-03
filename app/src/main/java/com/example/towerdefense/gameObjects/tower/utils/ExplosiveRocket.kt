package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.KMath.Companion.angle
import com.example.towerdefense.utility.textures.Animation
import org.joml.Vector2f

class ExplosiveRocket(var circle: Circle, var enemy: Enemy) : GameObject(circle, false, false), Projectile {
    
    private var timeToLive = 1500L
    private val spawnTime = TimeController.getGameTime()
    private var animation: Animation = Animation(explosiveRocketFrames, 100f)
    
    override fun update() {
        //If the explosion has ended
        if (TimeController.getGameTime() > explosionEndTime) destroy()
    
        // If the targeted enemy has already been destroyed
        if (enemy.toDelete() && !explosionCreated) destroy()
    
        // If the projectile has lived past its time to live
        if (TimeController.getGameTime() > spawnTime + timeToLive && !explosionCreated) destroy()
        
        //If the object is to be deleted or the game is paused, return
        if (toDelete() || TimeController.isPaused()) return
        
        super.update()
    
        // If the explosion has been created, damage enemies.
        if (explosionCreated) return explosionDamage()
    
        // Rotate the projectile to face the enemy.
        setRotation(Vector2f(enemy.position()).sub(circle.body.position).normalize().angle())
    
        // Detect collisions with enemies.
        if (!explosionCreated) detectCollisions()
    }
    
    private fun explosionDamage() {
        println("Explosion damage")
        gameView!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider())) {
                it.damage(100)
            }
        }
    }
    
    private var explosionEndTime: Long = Long.MAX_VALUE
    
    //Detects collisions with enemies, if the object has exploded damages the enemy, if it collided with it generates an explosion
    private fun detectCollisions() {
        gameView!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider())) {
                generateExplosion()
                return
            }
        }
    }
    
    //Sets the time to destroy the object
    private fun destroyInMiliseconds(time: Long) {
        explosionEndTime = TimeController.getGameTime() + time
    }
    
    private var explosionCreated = false
    
    private fun generateExplosion() {
        velocity(100f)
        explosionCreated = true
        circle.radius = circle.radius * 3
        collider2D().update()
        velocity(0f)
        destroyInMiliseconds(500L)
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
    
    companion object {
        private val explosiveRocketFrames =
            arrayOf(
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete),
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete2),
                BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.cohete3)
            )
    }
    
}