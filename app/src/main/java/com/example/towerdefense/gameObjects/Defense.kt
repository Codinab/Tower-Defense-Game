package com.example.towerdefense.gameObjects

import android.view.MotionEvent
import com.example.towerdefense.Game
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.Interfaces.Stateful
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

class Defense(collider2D: Collider2D, private val game: Game) : DrawableObject(collider2D), InputEvent,
    Stateful, Positionable {


    override var movable = AtomicBoolean(false)
    override var fixable = AtomicBoolean(false)
    override var layerLevel: Int = 0
    override var lastClickTime: Long = 0L

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        //The defense menu should pop up on click
        //The defense should be movable after the menu allows it
        when (event.action) {
            MotionEvent.ACTION_DOWN -> handleDownEvent(event, position)
            MotionEvent.ACTION_MOVE -> handleMoveEvent(event, position)
            MotionEvent.ACTION_UP -> handleUpEvent(event, position)
        }
        return false
    }

    fun handleUpEvent(event: MotionEvent, position: Vector2f) {
        if (fixable.get()) {
            fixable.set(false)
            movable.set(false)
        }
    }

    private fun handleMoveEvent(event: MotionEvent, position: Vector2f) {
        if (movable.get()) position(position)
    }

    private fun handleDownEvent(event: MotionEvent, position: Vector2f) {
        if (movable.get()) position(position)
        else if (isClicked(position)) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime < 300) {
                movable.set(true)
                fixable.set(false)
            }
            lastClickTime = currentTime
        }
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, collider2D)
    }

    override fun position(position: Vector2f) {
        collider2D.body.position = position
    }

    override fun position(): Vector2f {
        return collider2D.body.position
    }

}