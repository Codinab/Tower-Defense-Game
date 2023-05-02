package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.utility.Interfaces.*
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

open class GameObject(private var collider2D: Collider2D) : Positionable,
    Stateful, java.io.Serializable, Removable, Movable, InputEvent {
    constructor(collider2D: Collider2D, movable: Boolean, fixable: Boolean) : this(collider2D) {
        this.movable.set(movable)
        this.fixable.set(fixable)
    }
    
    override lateinit var collider: Collider2D
    init {
        this.collider = collider2D
    }

    fun collider2D(): Collider2D {
        return collider2D
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
    
    override var lastClickTime: Long = 0L
    
    override var toDelete: Boolean = false
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (collider2D != other.collider2D) return false
        if (movable != other.movable) return false
        if (fixable != other.fixable) return false
        if (layerLevel != other.layerLevel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = collider2D.hashCode()
        result = 31 * result + movable.hashCode()
        result = 31 * result + fixable.hashCode()
        result = 31 * result + layerLevel
        return result
    }
    
}
