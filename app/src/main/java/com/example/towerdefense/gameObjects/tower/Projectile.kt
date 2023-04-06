package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import org.joml.Vector2f

class Projectile(var circle: Circle, private var damage : Int) : GameObject(circle, false, false) {

    private var toDelete = false
    private var timeToLive = 4000L
    private val spawnTime = TimeController.getGameTime()
    private var enemyHits = mutableListOf<Enemy>()
    override fun update() {
        if (toDelete) return
        super.update()
        if (TimeController.getGameTime() - spawnTime > timeToLive) {
            destroy()
        }
        gameView!!.surfaceView.enemies.forEach { enemy ->
            if (IntersectionDetector2D.intersection(circle, enemy.collider2D())) {
                enemyHits.add(enemy)
                enemy.damage(damage)
            }
        }
    }

    override fun draw(canvas: Canvas) {
        if (toDelete) return
        super.draw(canvas)
    }

    private fun destroy() {
        toDelete = true
        gameView!!.surfaceView.projectiles.remove(this)
    }

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
}