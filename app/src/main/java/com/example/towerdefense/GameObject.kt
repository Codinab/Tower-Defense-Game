package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

class GameObject(context: Context, private var positionX: Double, private var positionY: Double){
    private var paint: Paint
    var movable = true
    var creator = false
    var z = 0

    init {
        paint = Paint()
        paint.color = android.graphics.Color.WHITE
    }

    fun draw(canvas : Canvas) {
        canvas.drawCircle(positionX.toFloat(), positionY.toFloat(), 100f, paint)
    }

    fun isClicked(x: Double, y: Double): Boolean {
        return (x - positionX) * (x - positionX) + (y - positionY) * (y - positionY) < 100 * 100
    }

    fun update() {
    }

    fun setPosition(x: Double, y: Double) {
        positionX = x
        positionY = y
    }

    fun onTouchEvent(event: MotionEvent) {
        setPosition(event.x.toDouble(), event.y.toDouble())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (positionX != other.positionX) return false
        if (positionY != other.positionY) return false
        if (paint != other.paint) return false
        if (movable != other.movable) return false
        if (creator != other.creator) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = positionX.hashCode()
        result = 31 * result + positionY.hashCode()
        result = 31 * result + paint.hashCode()
        result = 31 * result + movable.hashCode()
        result = 31 * result + creator.hashCode()
        result = 31 * result + z
        return result
    }


}