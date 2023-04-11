package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import org.joml.Vector2f

class Camera {
    private var position = Vector2f(0f, 0f)
    private var previousTouchX = 0f
    private var previousTouchY = 0f
    internal var moving = false

    fun cameraPosition(): Vector2f {
        return position
    }

    fun x(): Float {
        return position.x
    }
    fun y(): Float {
        return position.y
    }

    fun updateCanvas(canvas: Canvas) {
        canvas.translate(-position.x, -position.y)
    }

    fun update(event: MotionEvent) {
        position.x -= event.x - previousTouchX
        position.y -= event.y - previousTouchY
        previousTouchX = event.x
        previousTouchY = event.y
        com.example.towerdefense.utility.cameraPosition = position
    }

    fun adjustedPosition(event: MotionEvent): Vector2f {
        return Vector2f(event.x + position.x, event.y + position.y)
    }

    fun touch(event: MotionEvent) {
        moving = true
        val x = event.x
        val y = event.y
        previousTouchX = x
        previousTouchY = y
    }
}