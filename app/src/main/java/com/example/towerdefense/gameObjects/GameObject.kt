package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.utility.Interfaces.*
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

open class GameObject(private var collider2D: Collider2D) : InputEvent, Movable, Positionable,
    Stateful, java.io.Serializable, Removable {
    constructor(collider2D: Collider2D, movable: Boolean, fixable: Boolean) : this(collider2D) {
        this.movable.set(movable)
        this.fixable.set(fixable)
    }

    override var lastClickTime: Long = 0L
    fun collider2D(): Collider2D {
        return collider2D
    }

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
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

    override fun isClicked(position: Vector2f?): Boolean {
        if (position == null) return false
        return IntersectionDetector2D.intersection(position, collider2D)
    }

    override fun addVelocity(velocity: Float) {
        collider2D.body.velocity += velocity
    }

    override fun velocity(): Float {
        return collider2D.body.velocity
    }

    override fun velocity(velocity: Float) {
        collider2D.body.velocity = velocity
    }

    override fun setAngularVelocity(angularVelocity: Float) {
        collider2D.body.angularVelocity = angularVelocity
    }

    override fun getAngularVelocity(): Float {
        return collider2D.body.angularVelocity
    }

    override fun addAngularVelocity(angularVelocity: Float) {
        collider2D.body.angularVelocity += angularVelocity
    }

    override fun setRotation(rotation: Float) {
        collider2D.body.rotation = rotation
    }

    override fun getRotation(): Float {
        return collider2D.body.rotation
    }

    override fun addRotation(rotation: Float) {
        collider2D.body.rotation += rotation
    }

    override fun update() {
        if (toDelete()) return
        collider2D.update()
    }

    override fun position(position: Vector2f) {
        collider2D.body.position = position
    }

    override fun position(): Vector2f {
        return Vector2f(collider2D.body.position)
    }

    open fun draw(canvas: Canvas) {
        if (toDelete()) return
        collider2D.draw(canvas)
    }

    override var movable: AtomicBoolean = AtomicBoolean(true)
    override var fixable: AtomicBoolean = AtomicBoolean(true)
    override var layerLevel: Int = 0

    override fun toString(): String {
        return "GameObject(position=${position()}, velocity=${velocity()}, rotation=${getRotation()})"
    }
    
    override var toDelete: Boolean = false
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (collider2D != other.collider2D) return false
        if (lastClickTime != other.lastClickTime) return false
        if (movable != other.movable) return false
        if (fixable != other.fixable) return false
        if (layerLevel != other.layerLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = collider2D.hashCode()
        result = 31 * result + lastClickTime.hashCode()
        result = 31 * result + movable.hashCode()
        result = 31 * result + fixable.hashCode()
        result = 31 * result + layerLevel
        return result
    }
    
}
