package com.example.towerdefense.utility.Interfaces

import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import org.joml.Vector2f

interface InputEvent : Removable, Stateful, Positionable, Collisionable {
    var lastClickTime: Long
    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (toDelete()) return false
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> handleDownEvent(event, position)
            MotionEvent.ACTION_MOVE -> handleMoveEvent(event, position)
            MotionEvent.ACTION_UP -> handleUpEvent(event, position)
            else -> false
        }
    }
    
    open fun handleUpEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (fixable.get()) {
            fixable.set(false)
            movable.set(false)
            return true
        }
        return false
    }
    
    private fun handleMoveEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (movable.get()) {
            position(position)
            return true
        }
        return false
    }
    
    open fun handleDownEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (movable.get()) {
            position(position)
            return true
        } else if (isClicked(position)) {
            val currentTime = System.currentTimeMillis()
            lastClickTime = currentTime
            return true
        }
        return false
    }
    
    fun isClicked(position: Vector2f?): Boolean {
        if (position == null) return false
        return IntersectionDetector2D.intersection(position, collider)
    }
}
