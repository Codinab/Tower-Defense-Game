package com.example.towerdefense.gameObjects

import GameObjectView
import android.graphics.Canvas
import android.view.MotionEvent
import org.joml.Vector2f

class GameObjectList(private val gameObjects: MutableList<GameObjectView> = mutableListOf()) : MutableList<GameObjectView> by gameObjects{
    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun draw(canvas: Canvas?) {
        gameObjects.forEach { it.draw(canvas) }
    }
    fun getGameObjects(): List<GameObjectView> {
        return gameObjects
    }

    override fun toString(): String {
        var string = ""
        gameObjects.forEach { string += it.toString() + " " }
        return string
    }

//    fun getMovable(): GameObjectList {
//        val movableGameObjects = GameObjectList()
//        gameObjects.forEach { if (it.movable.get()) movableGameObjects.add(it) }
//        return movableGameObjects
//    }

    fun getClicked(adjustedPosition: Vector2f): GameObjectList {
            TODO("Not yet implemented")
    }
}