package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.Direction2D
import com.example.towerdefense.utility.Drawing
import com.example.towerdefense.utility.Road
import com.example.towerdefense.utility.Road.Companion.toVector2f
import com.example.towerdefense.utility.gameHealth
import org.joml.Vector2f

class Enemy(collider2D: Collider2D, private val road: Road) : GameObject(collider2D)
{
    private var positionFrom : Vector2f
    private var positionTo : Vector2f

    private var health : Int = 100
    private var maxHealth : Int = 100

    private var toDelete : Boolean = false
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

    fun getDelete() : Boolean
    {
        return toDelete
    }
    fun damage(damage : Int)
    {
        health -= damage
        println(health)
        if (health <= 0)
        {
            toDelete = true
        }
    }

    override fun update() {
        if (paused) return
        super.update()
        if (positionTo.distanceSquared(position()) < getVelocity())
        {
            positionFrom = positionTo
            positionTo = road.getNextCorner(positionFrom)
            val direction = road.getRoadDirection(positionFrom)
            if (direction == Direction2D.UNDEFINED || toDelete) {
                setVelocity(0f)
                gameHealth.getAndAdd(-1)
                toDelete = true
            }
            setRotation(road.getRoadDirection(positionFrom).toAngle())
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.textSize = 50f
        val topLeft = position().sub(collider2D().layoutSize().mul(0.5f))
        val topRight = Vector2f(topLeft).add(collider2D().layoutSize().x, 0f)

        Drawing.drawHealthBar(canvas, topLeft, topRight, health, maxHealth)
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
    fun setHealth(health : Int)
    {
        this.health = health
        this.maxHealth = health
    }
}