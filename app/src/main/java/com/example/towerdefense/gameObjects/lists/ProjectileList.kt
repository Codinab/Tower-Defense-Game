package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import com.example.towerdefense.gameObjects.tower.utils.CanonBall
import java.util.concurrent.CopyOnWriteArrayList

class ProjectileList(private val canonBalls: CopyOnWriteArrayList<CanonBall> = CopyOnWriteArrayList()) :
    MutableList<CanonBall> by canonBalls {
    
    
    fun draw(canvas: Canvas) = canonBalls.forEach { it.draw(canvas) }
    fun pause() = forEach { it.pause() }
    fun resume() = forEach { it.resume() }
    fun update() = forEach { it.update() }
    
    
}
