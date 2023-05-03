package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f

class CanonBall(private var circle: Circle, private var damage : Int) : GameObject(circle, false, false), Projectile {
    
    private var timeToLive = 4000L
    private val spawnTime = TimeController.getGameTime()
    private var damagedEnemies = ArrayList<Enemy>()
    private var health = circle.radius.toInt() / 8

    private val textureResized: Bitmap = Bitmap.createScaledBitmap(texture, circle.radius.toInt() * 2, circle.radius.toInt() * 2, false)
    override fun collider(): Collider2D {
        return circle
    }
    
    override fun update() {
        if (toDelete() || TimeController.isPaused()) return
        super.update()
        if (TimeController.getGameTime() - spawnTime > timeToLive) {
            destroy()
        }
        gameView!!.surfaceView.enemies.forEach { enemy ->
            if (IntersectionDetector2D.intersection(circle, enemy.collider())) {
                if (!damagedEnemies.contains(enemy)) {
                    enemy.damage(damage)
                    health--
                    damagedEnemies.add(enemy)
                }
            }
        }
        if (health <= 0) destroy()
    }

    private var texture_rotation = 0f
    private var texture_direction_rotation = true
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        Drawing.drawBitmap(canvas, textureResized, position(), texture_rotation)
        //rotate the texture back and forth to simulate a spinning effect
        if (texture_direction_rotation) {
            texture_rotation += 2f
            if (texture_rotation > 2f) texture_direction_rotation = false
        } else {
            texture_rotation -= 10f
            if (texture_rotation < 2f) texture_direction_rotation = true
        }
        
        
        
        
    }
    
    companion object {
        private val texture: Bitmap = BitmapFactory
            .decodeResource(gameView!!.resources, R.drawable.cannon_bullet)
    }
}