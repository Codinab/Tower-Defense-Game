package com.example.towerdefense.utility

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Box2D
import org.joml.Vector2f

//static class Drawing
class Drawing {
    
    class Animation(private val frames: Array<Bitmap>, var frameTime: Float) {
        private var currentFrame = 0
        private var lastFrameTime = 0L
        
        init {
            //Set frame time to milliseconds
        }
        fun draw(canvas: Canvas, position: Vector2f, paint: Paint, rotation: Float) {
            val elapsedFrameTime = TimeController.getGameTime() - lastFrameTime
            if (elapsedFrameTime >= frameTime) {
                currentFrame = (currentFrame + 1) % frames.size
                
                lastFrameTime = TimeController.getGameTime()
            }
            val frame = frames[currentFrame]
            drawBitmap(canvas, frame, position, paint, rotation)
        }
    }
    
    companion object {

        fun drawLine(canvas: Canvas, start: Vector2f, end: Vector2f, paint: Paint) {
            canvas.drawLine(start.x, start.y, end.x, end.y, paint)
        }

        fun drawLine(canvas: Canvas, start: Vector2f, end: Vector2f) {
            val paint = Paint()
            paint.color = Color.BLACK
            canvas.drawLine(start.x, start.y, end.x, end.y, Paint())
        }

        fun drawLine(canvas: Canvas, start: Vector2f, end: Vector2f, paint: Paint, width: Float) {
            paint.strokeWidth = width
            canvas.drawLine(start.x, start.y, end.x, end.y, paint)
        }

        fun drawLine(canvas: Canvas, start: Vector2f, end: Vector2f, width: Float) {
            val paint = Paint()
            paint.color = Color.BLACK
            paint.strokeWidth = width
            canvas.drawLine(start.x, start.y, end.x, end.y, paint)
        }
        
        fun drawLine(canvas: Canvas, start: Vector2f, rotation: Float, length: Float) {
            val end = Vector2f(start).normalize().mul(length)
            JMath.rotate(end, rotation, start)
            val paint = Paint()
            paint.strokeWidth = 10f
            drawLine(canvas, start, end, paint)
        }

        fun drawText(canvas: Canvas, text: String, position: Vector2f, paint: Paint) {
            canvas.drawText(text, position.x, position.y, paint)
        }
        fun drawText(canvas: Canvas, text: String, position: Vector2f, size: Float) {
            val paint = Paint()
            paint.textSize = size
            paint.color = Color.BLACK
            canvas.drawText(text, position.x, position.y, paint)
        }

        fun drawText(canvas: Canvas, text: String, position: Vector2f) {
            val paint = Paint()
            paint.color = Color.BLACK
            canvas.drawText(text, position.x, position.y, paint)
        }

        fun drawBox2D(canvas: Canvas, box2D: Box2D, paint: Paint) {
            canvas.save()
            canvas.rotate(box2D.body.rotation, box2D.body.position.x, box2D.body.position.y)
            canvas.drawRect(
                box2D.body.position.x - box2D.size.x / 2,
                box2D.body.position.y - box2D.size.y / 2,
                box2D.body.position.x + box2D.size.x / 2,
                box2D.body.position.y + box2D.size.y / 2,
                paint
            )
            canvas.restore()
        }
        fun drawBox2D(canvas: Canvas, box2D: Box2D) {
            val paint = Paint()
            paint.color = Color.BLACK
            drawBox2D(canvas, box2D, paint)
        }
    
        fun drawBitmap(canvas: Canvas, bitmap: Bitmap, position: Vector2f, paint: Paint, rotation: Float) {
            val x = position.x - bitmap.width * 0.5f
            val y = position.y - bitmap.height * 0.5f
            canvas.save()
            canvas.rotate(rotation, position.x, position.y)
            canvas.drawBitmap(bitmap, x, y, paint)
            canvas.restore()
        }
    
    
        fun drawHealthBar(canvas: Canvas, leftBottomHealthBarPos: Vector2f, rightBottomHealthBarPos: Vector2f, health: Int, maxHealth: Int) {
            val width = 10f
            val start = Vector2f(leftBottomHealthBarPos).add(0f, -width)
            val end = Vector2f(rightBottomHealthBarPos).add(0f, -width)

            val remainingHealth = Paint()
            remainingHealth.color = Color.GREEN
            val missingHealth = Paint()
            missingHealth.color = Color.RED

            val healthPercentage = health.toFloat() / maxHealth.toFloat()
            val healthBarEnd = Vector2f(start).lerp(end, healthPercentage)

            drawLine(canvas, start, healthBarEnd, remainingHealth, width)
            drawLine(canvas, healthBarEnd, end, missingHealth, width)
        }
    }
}
