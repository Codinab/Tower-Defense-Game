package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.*
import org.joml.Vector2f
import java.io.Serializable
import kotlin.math.pow

class Enemy(collider2D: Collider2D, private val road: Road) : GameObject(collider2D), Serializable
{
    private var positionFrom : Vector2f
    private var positionTo : Vector2f

    private var health : Int = 100
    private var maxHealth : Int = health

    var paused : Boolean = false
    init {
        movable.set(false)
        fixable.set(false)
        positionFrom = road.startVector.toVector2f()
        position(positionFrom)
        setVelocity(3f)
        positionTo = road.getNextCorner(positionFrom)
        setRotation(road.getFirstDirection().toAngle())
    }

    fun damage(damage : Int)
    {
        health -= damage
        if (health <= 0)
        {
            destroy()
        }
    }

    override fun update() {
        if (paused || toDelete()) return
        super.update()
        if (positionTo.distanceSquared(position()) < getVelocity().pow(2))
        {
            positionFrom = positionTo
            positionTo = road.getNextCorner(positionFrom)
            val direction = road.getRoadDirection(positionFrom)
            if (direction == Direction2D.UNDEFINED || toDelete()) {
                setVelocity(0f)
                gameHealth.getAndAdd(-1)
                destroy()
            }
            setRotation(road.getRoadDirection(positionFrom).toAngle())
        }
    }

    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        super.draw(canvas)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.textSize = 50f
        val topLeft = position().sub(collider2D().layoutSize().mul(0.5f))
        val topRight = Vector2f(topLeft).add(collider2D().layoutSize().x, 0f)

        Drawing.drawHealthBar(canvas, topLeft, topRight, health, maxHealth)
    }

    fun distanceToNextCornerSquared() : Float
    {
        return positionTo.distanceSquared(position())
    }

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return false
    }

    override fun toString(): String {
        return "Enemy(position=${position()}, velocity=${getVelocity()}, rotation=${getRotation()})"
    }

    fun getHealth() : Int
    {
        return health
    }
    fun getMaxHealth() : Int
    {
        return maxHealth
    }
    fun setHealth(health : Int)
    {
        this.health = health
        this.maxHealth = health
    }
}