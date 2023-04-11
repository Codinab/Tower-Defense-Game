package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import com.example.towerdefense.gameObjects.tower.utils.Projectile
import java.util.concurrent.CopyOnWriteArrayList

class ProjectileList(private val projectiles: CopyOnWriteArrayList<Projectile> = CopyOnWriteArrayList()) :
    MutableList<Projectile> by projectiles {
    
    
    fun draw(canvas: Canvas) = projectiles.forEach { it.draw(canvas) }
    fun update() = forEach { it.update() }
    
    
}
