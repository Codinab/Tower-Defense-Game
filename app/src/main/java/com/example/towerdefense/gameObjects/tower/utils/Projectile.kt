package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.Canvas

interface Projectile {
    fun draw(canvas: Canvas)
    fun update()
    fun pause()
    fun resume()
    fun toDelete() : Boolean
}