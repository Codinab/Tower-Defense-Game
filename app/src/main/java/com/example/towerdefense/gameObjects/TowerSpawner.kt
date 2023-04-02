package com.example.towerdefense.gameObjects

import android.view.MotionEvent
import com.example.towerdefense.GameSurfaceView
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f

class TowerSpawner(box2D: Box2D, private val gameSurfaceView: GameSurfaceView) : GameObject(box2D, false, false) {

    var areaRadius = 300f
    private var lastTower: Tower? = null
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (lastTower != null && lastTower!!.movable.get()) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val tower = Tower(areaRadius, Box2D(Vector2f(50f, 50f), Rigidbody2D(Vector2f(position))))
                gameSurfaceView.towers.add(tower)
                lastTower = tower
                return true
            }

        }
        return false
    }

}