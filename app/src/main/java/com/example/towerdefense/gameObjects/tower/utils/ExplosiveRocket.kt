package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.KMath.Companion.anglePositionToTarget
import com.example.towerdefense.utility.textures.Animation

class ExplosiveRocket(private var circle: Circle, var enemies: ArrayList<Enemy>, private var damage: Int, private val context: GameActivity) : Projectile, Drawable, Movable, Positionable {
    
    private var timeToLive = 1500L
    private val spawnTime = TimeController.getGameTime()
    
    private val explosiveRocketFrames =
        arrayOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.cohete),
            BitmapFactory.decodeResource(context.resources, R.drawable.cohete2),
            BitmapFactory.decodeResource(context.resources, R.drawable.cohete3)
        )
    private val explosionFrames =
        arrayOf(
            BitmapFactory.decodeResource(context.resources, R.drawable.boom1),
            BitmapFactory.decodeResource(context.resources, R.drawable.boom2),
            BitmapFactory.decodeResource(context.resources, R.drawable.boom3),
            BitmapFactory.decodeResource(context.resources, R.drawable.boom4),
        )
    
    private var rocketAnimation: Animation = Animation(explosiveRocketFrames, 100f)
    private var explosionAnimation: Animation = Animation(explosionFrames, 100f, false, circle.radius.toInt() * 5)
    
    @RequiresApi(Build.VERSION_CODES.N)
    override fun update() {
        //If the explosion has ended
        if (TimeController.getGameTime() > explosionEndTime) destroy()
        
        //Update enemy list
        enemies.removeIf { it.toDelete() }
        
        
        if (enemies.isEmpty()) destroy()
    
        // If the targeted enemy has already been destroyed
        if (!toDelete() && enemies.isEmpty() && !explosionCreated) generateExplosion()
    
        // If the projectile has lived past its time to live
        if (TimeController.getGameTime() > spawnTime + timeToLive && !explosionCreated) destroy()
        
        //If the object is to be deleted or the game is paused, return
        if (toDelete() || TimeController.isPaused()) return
        
        super.update()
    
        // If the explosion has been created, damage enemies.
        if (explosionCreated) return explosionDamage()
    
        // Rotate the projectile to face the enemy.
        setRotation(anglePositionToTarget(enemies.first().position(),circle.body.position))
    
        // Detect collisions with enemies.
        if (!explosionCreated) detectCollisions()
    }
    
    private var damagedEnemies = ArrayList<Enemy>()
    private fun explosionDamage() {
        context.gameView()!!.surfaceView.enemies.forEach {
            if (IntersectionDetector2D.intersection(circle, it.collider())) {
                if (!damagedEnemies.contains(it)) {
                    it.damage(damage)
                    damagedEnemies.add(it)
                }
            }
        }
    }
    
    private var explosionEndTime: Long = Long.MAX_VALUE
    
    //Detects collisions with enemies, if the object has exploded damages the enemy, if it collided with it generates an explosion
    private fun detectCollisions() {
        context.gameView()!!.surfaceView.enemies.forEach {
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
        collider().update()
        velocity(0f)
        destroyInMiliseconds(500L)
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        //super.draw(canvas)
        if (explosionCreated) drawExplosion(canvas)
        else rocketAnimation.draw(canvas, circle.body.position, getRotation())
    }
    
    private fun drawExplosion(canvas: Canvas) {
        explosionAnimation.draw(canvas, circle.body.position, (0..360).random().toFloat())
    }
    
    override fun collider(): Collider2D {
        return circle
    }
    
    override var toDelete: Boolean = false
}