package com.example.towerdefense.utility.textures

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.towerdefense.utility.TimeController
import org.joml.Vector2f

class Animation(private val frames: Array<Bitmap>, var frameTime: Float) {
    private var currentFrame = 0
    private var lastFrameTime = 0L
    
    init {
        //Set frame time to milliseconds
    }
    
    fun draw(canvas: Canvas, position: Vector2f, paint: Paint, rotation: Float) {
        val elapsedFrameTime = TimeController.getSinceAppStart() - lastFrameTime
        if (elapsedFrameTime >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size
            
            lastFrameTime = TimeController.getSinceAppStart()
        }
        val frame = frames[currentFrame]
        Drawing.drawBitmap(canvas, frame, position, paint, rotation)
    }
    
    fun draw(canvas: Canvas, position: Vector2f, rotation: Float) {
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            isDither = true
        }
        draw(canvas, position, paint, rotation)
    }
    fun draw(canvas: Canvas, position: Vector2f, paint: Paint) {
        draw(canvas, position, paint, 0f)
    }
    
    fun draw(canvas: Canvas, position: Vector2f) {
        val paint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
            isDither = true
        }
        draw(canvas, position, paint)
    }
    
    fun updateFrameTime(frameTime: Float) {
        this.frameTime = frameTime
    }
}