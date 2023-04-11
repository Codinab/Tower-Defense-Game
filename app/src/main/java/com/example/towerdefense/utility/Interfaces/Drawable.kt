package com.example.towerdefense.utility.Interfaces

import android.graphics.Canvas
import com.example.towerdefense.gameObjects.DrawableObject

interface Drawable {
    var drawableObject: DrawableObject
    fun draw(canvas: Canvas)
}