package com.example.towerdefense.gameObjects

import android.graphics.drawable.BitmapDrawable
import com.example.towerdefense.Game
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.Direction2D
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.Road.Companion.toVector2f
import org.joml.Vector2f
import org.joml.Vector2i

class Enemy(override var collider2D: Collider2D, private val game: Game) :
    DrawableObject(collider2D, game), Movable, Positionable {

    override val bitmapDrawable: BitmapDrawable? = null
    var health = 100f
    var previousPosition = game.road.getStart().toVector2f()
    var nextPosition = game.road.getNextPosition(previousPosition.toVector2i()).first.toVector2f()

    init {
        setPosition(previousPosition)
        setRotation(game.road.getRoadDirection(previousPosition.toVector2i()).toAngle())
    }

    override fun update() {
        super.update()

        collider2D.body.update()

        if (isAtNextPosition()) {
            previousPosition = nextPosition
            nextPosition = game.road.getNextPosition(nextPosition.toVector2i()).first.toVector2f()
            setRotation(game.road.getRoadDirection(nextPosition.toVector2i()).toAngle())
        }
        println("Enemy position: ${getPosition()}" + " previousPosition: $previousPosition" + " nextPosition: $nextPosition")
    }

    private fun isAtNextPosition(): Boolean {
        return getPosition().distance(nextPosition) < collider2D.body.velocity
    }


    override fun addVelocity(velocity: Float) {
        collider2D.body.addVelocity(velocity)
    }

    override fun getVelocity(): Float {
        return collider2D.body.velocity
    }

    override fun setVelocity(velocity: Float) {
        collider2D.body.velocity = velocity
    }

    override fun setAngularVelocity(angularVelocity: Float) {
        collider2D.body.angularVelocity = angularVelocity
    }

    override fun getAngularVelocity(): Float {
        return collider2D.body.angularVelocity
    }

    override fun addAngularVelocity(angularVelocity: Float) {
        collider2D.body.addAngularVelocity(angularVelocity)
    }

    override fun setRotation(rotation: Float) {
        collider2D.body.rotation = rotation
    }

    override fun getRotation(): Float {
        return collider2D.body.rotation
    }

    override fun addRotation(rotation: Float) {
        collider2D.body.addRotation(rotation)
    }

    override fun setPosition(position: Vector2f) {
        collider2D.body.position = position
    }

    override fun getPosition(): Vector2f {
        return collider2D.body.position
    }
}

private fun Vector2f.toVector2i(): Vector2i {
    return Vector2i(x.toInt(), y.toInt())
}
