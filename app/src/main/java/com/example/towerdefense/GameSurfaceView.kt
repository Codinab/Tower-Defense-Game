package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.Toast
import java.util.PriorityQueue

class GameSurfaceView(context: Context) : SurfaceView(context) {

    var toDraw = PriorityQueue<Drawable>()

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return

        toDraw.forEach {
            it.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Toast.makeText(context, "SurfaceView TouchEvent", Toast.LENGTH_SHORT).show()


        return super.onTouchEvent(event)
    }
}