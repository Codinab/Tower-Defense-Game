package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.Canvas
import com.example.towerdefense.utility.Interfaces.Removable

interface Projectile : Removable {
    fun draw(canvas: Canvas)
    fun update()
}