package com.example.towerdefense.gameObjects.lists

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.gameObjects.GameObject
import org.joml.Vector2f
import java.util.concurrent.CopyOnWriteArrayList

open class GameObjectList(private val gameObjects: CopyOnWriteArrayList<GameObject> = CopyOnWriteArrayList()) :
    MutableList<GameObject> by gameObjects {
    fun update() {
        gameObjects.forEach { it.update() }
    }

    override fun add(gameObject: GameObject): Boolean {
        return gameObjects.add(gameObject)
    }

    override fun remove(gameObject: GameObject): Boolean {
        return gameObjects.remove(gameObject)
    }
    fun draw(canvas: Canvas) {
        gameObjects.forEach { it.draw(canvas) }
    }

    fun getClicked(position: Vector2f?): GameObjectList {
        val clickedGameObjects = GameObjectList()
        gameObjects.forEach {
            if (it.isClicked(position)) {
                clickedGameObjects.add(it)
                return clickedGameObjects
            }
        }
        return clickedGameObjects
    }

    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return gameObjects.any { it.onTouchEvent(event, position) }
    }

    fun getMovable(): GameObjectList {
        val movableGameObjects = GameObjectList()
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