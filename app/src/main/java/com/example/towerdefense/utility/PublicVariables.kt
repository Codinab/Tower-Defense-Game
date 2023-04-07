package com.example.towerdefense.utility

import com.example.towerdefense.GameView
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.atan2

var fps = false

var screenSize = Vector2i(0, 0)
var cameraPosition = Vector2f(0f, 0f)
var gameView : GameView? = null
var towerClicked : Tower? = null

var money = AtomicInteger(1000)
var gameHealth = AtomicInteger(1000)
var gameVelocity = 1

fun Vector2i.toVector2f(): Vector2f {
    return Vector2f(this.x.toFloat(), this.y.toFloat())
}
fun Vector2f.toVector2i(): Vector2i {
    return Vector2i(this.x.toInt(), this.y.toInt())
}

fun Vector2f.angle(): Float {
    return atan2(this.y.toDouble(), this.x.toDouble()).toFloat().toDegrees()
}

private fun Float.toDegrees(): Float {
    return this * 180 / Math.PI.toFloat()
}
