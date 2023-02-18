package com.example.towerdefense

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import org.joml.Vector2f
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean

interface GameObject {

    var game: Game
    var movable: AtomicBoolean
    var fixable: AtomicBoolean
    var toDestroy: Boolean
    var layerLevel: Int
    fun draw(canvas: Canvas?)
    fun update()
    fun setPosition(position: Vector2f)
    fun setOffset(offset: Vector2f)
    fun addVelocity(velocity: Float)
    fun getVelocity(): Float
    fun setVelocity(velocity: Float)
    fun setAngularVelocity(angularVelocity: Float)
    fun getAngularVelocity(): Float
    fun addAngularVelocity(angularVelocity: Float)
    fun setRotation(rotation: Float)
    fun getRotation(): Float
    fun addRotation(rotation: Float)
    fun maxX(): Float
    fun minX(): Float
    fun maxY(): Float
    fun minY(): Float
    fun getPosition(): Vector2f
    fun isClicked(position: Vector2f?): Boolean

    var lastClickTime: Long
    var semaphore: Semaphore

    var moveObjectThread: MoveGameObjectThread
    fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN && onTouchEvent_ACTION_DOWN(event)) return true
        if (event.action == MotionEvent.ACTION_MOVE && onTouchEvent_ACTION_MOVE(event)) return true
        if (event.action == MotionEvent.ACTION_UP && onTouchEvent_ACTION_UP(event)) return true

        return movable.get() || fixable.get()
    }

    fun onTouchEvent_ACTION_UP(event: MotionEvent): Boolean {
        if (movable.get() &&
                IntersectionDetector2D.intersection(this, game.gameObjectCreator)) {
            movable.set(false)
            game.gameObjectListToRemove.add(this)
            game.money += 10
            return true
        }
        if (fixable.get() && movable.get()) {
            movable.set(false)
            return true
        }
        return false
    }

    fun onTouchEvent_ACTION_DOWN(event: MotionEvent): Boolean {
        if (isClicked(Vector2f(event.x, event.y))) {
            if (movable.get() && semaphore.tryAcquire()) moveObjectThread.start()
            else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 300 && game.money >= 2) {
                    game.money -= 2
                    movable.set(true)
                    fixable.set(false)
                }
                lastClickTime = currentTime
            }
            return true
        }
        return false
    }

    fun onTouchEvent_ACTION_MOVE(event: MotionEvent): Boolean {
        if (movable.get()) {
            try {
                semaphore.tryAcquire()
                moveObjectThread.start()
                return true
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return false
    }
}

@Target(
    AnnotationTarget.EXPRESSION, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.SOURCE)
annotation class Temporary