package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import com.example.towerdefense.gameObjects.tower.utils.Projectile
import java.util.Vector
import java.util.concurrent.CopyOnWriteArrayList

class ProjectileList(private val projectiles: Vector<Projectile> = Vector()) :
    MutableList<Projectile> by projectiles {
    
    
    fun draw(canvas: Canvas) {
        synchronized(projectiles) {
            projectiles.forEach { it.draw(canvas) }
        }
    }
    fun update() {
        synchronized(projectiles) {
            projectiles.forEach { it.update() }
        }
    }
    
}
