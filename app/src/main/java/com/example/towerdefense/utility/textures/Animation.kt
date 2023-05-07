package com.example.towerdefense.utility.textures

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.example.towerdefense.utility.TimeController
import org.joml.Vector2f

class Animation(private val frames: Array<Bitmap>, var frameTime: Float, var loop: Boolean = true, size: Int? = null) {
    private var currentFrame = 0
    private var lastFrameTime = 0L
    private var isFirstDraw = true
    
    init {
        if (size != null) {
            for (i in frames.indices) {
                frames[i] = Bitmap.createScaledBitmap(frames[i], size, size, false)
            }
        }
    }
    
    fun draw(canvas: Canvas, position: Vector2f, paint: Paint, rotation: Float) {
        val timeFunction =
            if (loop) TimeController.getSinceGameStart() else TimeController.getGameTime()
        
        if (isFirstDraw) {
            lastFrameTime = timeFunction
            isFirstDraw = false
        }
        
        val elapsedFrameTime = timeFunction - lastFrameTime
        if (elapsedFrameTime >= frameTime) {
            if (loop) {
                currentFrame = (currentFrame + 1) % frames.size
            } else {
                if (currentFrame < frames.size - 1) {
                    currentFrame += 1
                }
            }
            lastFrameTime = timeFunction
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