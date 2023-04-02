package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import org.joml.Vector2f

class Camera {
    private var cameraPosition = Vector2f(0f, 0f)
    var previousTouchX = 0f
    var previousTouchY = 0f
    var moving = false

    fun cameraPosition(): Vector2f {
        return cameraPosition
    }

    fun x(): Float {
        return cameraPosition.x
    }
    fun y(): Float {
        return cameraPosition.y
    }

    fun updateCanvas(canvas: Canvas) {
        canvas.translate(-cameraPosition.x, -cameraPosition.y)
    }

    fun update(event: MotionEvent) {
        cameraPosition.x -= event.x - previousTouchX
        cameraPosition.y -= event.y - previousTouchY
        previousTouchX = event.x
        previousTouchY = event.y
    }

    fun adjustedPosition(event: MotionEvent): Vector2f {
        return Vector2f(event.x + cameraPosition.x, event.y + cameraPosition.y)
    }

    fun touch(event: MotionEvent) {
        moving = true
        val x = event.x
        val y = event.y
        previousTouchX = x
        previousTouchY = y
    }
}