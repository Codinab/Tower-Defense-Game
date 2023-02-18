package com.example.towerdefense

import android.graphics.Canvas
import android.view.MotionEvent

class GameObjectList(private val gameObjects: MutableList<GameObject> = mutableListOf()) : MutableList<GameObject> by gameObjects {
    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun draw(canvas: Canvas?) {
        gameObjects.forEach { it.draw(canvas) }
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        println("OnTouchEvent")
        return gameObjects.any { it.onTouchEvent(event) }
    }

    fun getGameObjects(): List<GameObject> {
        return gameObjects
    }
}