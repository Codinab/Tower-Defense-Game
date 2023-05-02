package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.utility.money
import org.joml.Vector2f
import java.util.concurrent.CopyOnWriteArrayList

class EnemyList(private val enemies: CopyOnWriteArrayList<Enemy> = CopyOnWriteArrayList()) :
    MutableList<Enemy> by enemies {

    fun update() {
        this.enemies.forEach {
            it.update()
            if (it.toDelete()) {
                money.addAndGet(10)
            }
        }
    }


    override fun add(element: Enemy): Boolean {
        return enemies.add(element)
    }

    override fun remove(element: Enemy): Boolean {
        return enemies.remove(element)
    }
    fun draw(canvas: Canvas) {
        enemies.forEach { it.draw(canvas) }
    }

    fun getAll(): List<Enemy> {
        return enemies
    }

    override fun toString(): String {
        var string = "Enemies: {"
        enemies.forEach { string += "$it " }
        string += "}"
        return string
    }
    
}