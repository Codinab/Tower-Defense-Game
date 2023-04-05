package com.example.towerdefense.utility

import com.example.towerdefense.GameView
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger

var screenSize = Vector2i(0, 0)
var cameraPosition = Vector2f(0f, 0f)
var fps = true
var gameView : GameView? = null

var lastResumeTime = System.currentTimeMillis()
var lastPauseTime = System.currentTimeMillis()
val timeSinceStart = System.currentTimeMillis()
fun getDeltaTime(): Float {
    return (System.currentTimeMillis() - lastResumeTime) / 1000f
}

var money = AtomicInteger(1000)
