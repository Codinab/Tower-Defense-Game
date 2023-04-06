package com.example.towerdefense

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.utility.money
import org.joml.Vector2f
import java.util.concurrent.CopyOnWriteArrayList

class EnemyList(private val enemies: CopyOnWriteArrayList<Enemy> = CopyOnWriteArrayList()) :
    MutableList<Enemy> by enemies {

    fun update() : ArrayList<Enemy>{
        val enemies = ArrayList<Enemy>()
        this.enemies.forEach {
            it.update()
            if (it.getDelete()) {
                money.addAndGet(10)
                enemies.add(it)
                this.enemies.remove(it)
            }
        }
        return enemies
    }


    override fun add(enemy: Enemy): Boolean {
        return enemies.add(enemy)
    }

    override fun remove(enemy: Enemy): Boolean {
        return enemies.remove(enemy)
    }
    fun draw(canvas: Canvas) {
        enemies.forEach { it.draw(canvas) }
    }



    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return enemies.any { it.onTouchEvent(event, position) }
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