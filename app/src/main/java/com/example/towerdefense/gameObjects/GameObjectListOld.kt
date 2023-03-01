package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import org.joml.Vector2f

class GameObjectListOld(private val gameObjects: MutableList<GameObject> = mutableListOf()) : MutableList<GameObject> by gameObjects {
    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun draw(canvas: Canvas?) {
        gameObjects.forEach { it.draw(canvas) }
    }

    fun getClicked(position: Vector2f?): GameObjectListOld {
        val clickedGameObjects = GameObjectListOld()
        gameObjects.forEach { if (it.isClicked(position)) clickedGameObjects.add(it) }
        return clickedGameObjects
    }

    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return gameObjects.any { it.onTouchEvent(event,position) }
    }

    fun getMovable(): GameObjectListOld {
        val movableGameObjects = GameObjectListOld()
        gameObjects.forEach { if (it.movable.get()) movableGameObjects.add(it) }
        return movableGameObjects
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }

    override fun toString(): String {
        var string = ""
        gameObjects.forEach { string += it.toString() + " " }
        return string
    }


}