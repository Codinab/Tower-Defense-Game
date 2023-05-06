package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class TowerList(private val towers: Vector<Tower> = Vector()) :
    MutableList<Tower> by towers {
    
    fun update() {
        synchronized(towers) {
            towers.forEach { it.update() }
        }
    }
    
    override fun add(element: Tower): Boolean {
        synchronized(towers) {
            return towers.add(element).also { towers.sortWith(TowerPriority) }
        }
    }
    
    override fun remove(element: Tower): Boolean {
        synchronized(towers) {
            
            return towers.remove(element).also { towers.sortWith(TowerPriority) }
        }
    }
    
    fun draw(canvas: Canvas) {
        synchronized(towers) {
            val sortedTowers = towers.sortedWith(TowerPriority)
            sortedTowers.forEach {
                it.draw(canvas)
            }
        }
    }
    
    fun getTowers(): List<Tower> = towers
    
    
    fun updateAreas(enemies: EnemyList) = this.forEach { it.updateArea(enemies) }
    
    
    override fun toString(): String {
        var string = "com.example.towerdefense.gameObjects.lists.TowerList: {"
        towers.forEach { string += "$it " }
        string += "}"
        return string
    }
    
    object TowerPriority : Comparator<Tower> {
        override fun compare(t1: Tower, t2: Tower): Int {
            return t1.layerLevel - t2.layerLevel
        }
    }
}
