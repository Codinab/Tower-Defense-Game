package com.example.towerdefense.utility

import org.joml.Vector2f
import org.joml.Vector2i

var screenSize = Vector2i(0, 0)
var cameraPosition = Vector2f(0f, 0f)
var fps = true

var lastResumeTime = System.currentTimeMillis()
var lastPauseTime = System.currentTimeMillis()
val timeSinceStart = System.currentTimeMillis()

fun getDeltaTime(): Float {
    return (System.currentTimeMillis() - lastResumeTime) / 1000f
}
