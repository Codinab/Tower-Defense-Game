package com.example.towerdefense.gameObjects

import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.Direction2D
import com.example.towerdefense.utility.Road
import com.example.towerdefense.utility.Road.Companion.toVector2f
import org.joml.Vector2f

class Enemy(collider2D: Collider2D, private val road: Road) : GameObject(collider2D)
{
    constructor(collider2D: Collider2D, road: Road, health: Int) : this(collider2D, road)
    {
        this.health = health
    }
    constructor(collider2D: Collider2D, drawableObject: DrawableObject, road: Road) : this(collider2D, road)
    {
        this.drawableObject = drawableObject
    }
    constructor(collider2D: Collider2D, drawableObject: DrawableObject, road: Road, health: Int) : this(collider2D, drawableObject, road)
    {
        this.health = health
    }

    private var positionFrom : Vector2f
    private var positionTo : Vector2f
    private var health : Int = 100
    private var toDelete : Boolean = false
    var paused : Boolean = true


    init {
        movable.set(false)
        fixable.set(false)
        positionFrom = road.startVector.toVector2f()
        setPosition(positionFrom)
        setVelocity(3f)
        positionTo = road.getNextCorner(positionFrom)
        setRotation(road.getFirstDirection().toAngle())

        println(road.getRoadCorners())
    }

    fun getDelete() : Boolean
    {
        return toDelete
    }
    fun damage(damage : Int)
    {
        health -= damage
        if (health <= 0)
        {
            toDelete = true
        }
        println(" $health ")
    }

    override fun update() {
        if (paused) return
        super.update()
        if (positionTo.distanceSquared(getPosition()) < getVelocity())
        {
            println(road.getAllDirections())
            positionFrom = positionTo
            positionTo = road.getNextCorner(positionFrom)
            val direction = road.getRoadDirection(positionFrom)
            if (direction == Direction2D.UNDEFINED || toDelete) {
                setVelocity(0f)
            }
            setRotation(road.getRoadDirection(positionFrom).toAngle())
        }
    }

    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return false
    }

    override fun toString(): String {
        return "Enemy(position=${getPosition()}, velocity=${getVelocity()}, rotation=${getRotation()})"
    }


}