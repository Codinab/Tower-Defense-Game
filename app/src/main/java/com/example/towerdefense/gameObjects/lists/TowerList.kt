package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import java.util.concurrent.CopyOnWriteArrayList

class TowerList(private val towers: CopyOnWriteArrayList<Tower> = CopyOnWriteArrayList()) :
    MutableList<Tower> by towers {
    
    fun update() = towers.forEach { it.update() }
    
    override fun add(element: Tower): Boolean {
        return towers.add(element)
    }
    
    override fun remove(element: Tower): Boolean {
        return towers.remove(element)
    }
    
    fun draw(canvas: Canvas) = towers.forEach { it.draw(canvas) }
    
    fun getClicked(position: Vector2f?): TowerList {
        val clickedTowers = TowerList()
        towers.forEach {
            if (it.isClicked(position)) {
                clickedTowers.add(it)
                return clickedTowers
            }
        }
        return clickedTowers
    }
    
    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return towers.any { it.onTouchEvent(event, position) }
    }
    
    fun getMovable(): TowerList {
        val movableTowers = TowerList()
        towers.forEach { if (it.movable.get()) movableTowers.add(it) }
        return movableTowers
    }
    
    fun getTowers(): List<Tower> = towers
    
    
    fun updateAreas(enemies: EnemyList) = this.forEach { it.updateArea(enemies) }
    
    
    override fun toString(): String {
        var string = "com.example.towerdefense.gameObjects.lists.TowerList: {"
        towers.forEach { string += "$it " }
        string += "}"
        return string
    }
}
