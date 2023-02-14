package com.example.towerdefense

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import org.joml.Vector2f
import java.util.concurrent.Semaphore

interface GameObject {

    var game: Game
    var movable: Boolean
    var fixable: Boolean
    var layerLevel : Int
    fun draw(canvas: Canvas?)
    fun update()
    fun setPosition(position: Vector2f?)
    fun isClicked(position: Vector2f?): Boolean

    var lastClickTime: Long
    var semaphore: Semaphore

    fun onTouchEvent(event: MotionEvent): Boolean

}

annotation class Temporary