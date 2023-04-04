package com.example.towerdefense.utility

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import org.joml.Vector2f

//static class Drawing
class Drawing {
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
    }
}
