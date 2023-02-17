package com.example.towerdefense

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import org.intellij.lang.annotations.Language
import org.joml.Vector2f
import java.util.concurrent.Semaphore

interface GameObject {

    var game: Game
    var movable: Boolean
    var fixable: Boolean
    var toDestroy : Boolean
    var layerLevel : Int
    fun draw(canvas: Canvas?)
    fun update()
    fun getRawPosition(): Vector2f
    fun setPosition(position: Vector2f)
    fun setOffset(offset: Vector2f)
    fun addVelocity(velocity: Float)
    fun getVelocity() : Float
    fun setVelocity(velocity: Float)
    fun setAngularVelocity(angularVelocity: Float)
    fun getAngularVelocity() : Float
    fun addAngularVelocity(angularVelocity: Float)
    fun setRotation(rotation: Float)
    fun getRotation() : Float
    fun addRotation(rotation: Float)
    fun maxX() : Float
    fun minX() : Float
    fun maxY() : Float
    fun minY() : Float
    fun getPosition() : Vector2f
    fun isClicked(position: Vector2f?): Boolean

    var lastClickTime: Long
    var semaphore: Semaphore
    fun onTouchEvent(event: MotionEvent): Boolean {
        fun position() = Vector2f(event.x, event.y)

        if (!isClicked(position())) return false

        val moveObjectThread = Thread {
            //paint.color = android.graphics.Color.RED
            while (event.action != MotionEvent.ACTION_UP && movable) {
                setPosition(position())
            }
            if (fixable) movable = false
            semaphore.release()
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isClicked(position())) {
                    if (movable && semaphore.tryAcquire()) moveObjectThread.start()
                    else {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime < 300 && game.money >= 2) {
                            game.money -= 2
                            movable = true
                            fixable = false
                        }
                        lastClickTime = currentTime
                    }
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (movable && semaphore.tryAcquire()) {
                    moveObjectThread.start()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!fixable && movable && IntersectionDetector2D.intersection(this, game.gameObjectCreator)) {
                    movable = false
                    game.gameObjectListToRemove.add(this)
                    game.money += 10
                }
                if (fixable) movable = false
                moveObjectThread.interrupt()
            }
        }
        return false
    }
}
@Target(AnnotationTarget.EXPRESSION, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION
)
@Retention(AnnotationRetention.SOURCE)
annotation class Temporary