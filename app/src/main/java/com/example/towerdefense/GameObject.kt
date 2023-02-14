package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.widget.Toast
import java.util.concurrent.Semaphore

class GameObject(context: Context, private var positionX: Double, private var positionY: Double) :
    java.io.Serializable, android.view.View(context) {
    private var paint: Paint
    var movable = true
    var z = 0
    var size = 40f

    init {
        paint = Paint()
    }

    override fun draw(canvas: Canvas?) {
        paint.color = if (movable) android.graphics.Color.RED else android.graphics.Color.WHITE
        canvas?.drawCircle(positionX.toFloat(), positionY.toFloat(), size, paint)
        super.draw(canvas)
    }

    fun isClicked(x: Double, y: Double): Boolean {
        return (x - positionX) * (x - positionX) + (y - positionY) * (y - positionY) < 100 * 100
    }

    fun update() {
        return
    }

    fun setPosition(x: Double, y: Double) {
        positionX = x
        positionY = y
    }


    private var lastClickTime: Long = 0
    private var semaphore = Semaphore(1)
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val moveObjectThread = Thread {
            //paint.color = android.graphics.Color.RED
            while (event.action != MotionEvent.ACTION_UP) {
                setPosition(event.x.toDouble(), event.y.toDouble())
            }
            movable = false
            semaphore.release()
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isClicked(event.x.toDouble(), event.y.toDouble())) {
                    if (movable && semaphore.tryAcquire()) moveObjectThread.start()
                    else {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime < 300) {
                            movable = true
                        }
                        lastClickTime = currentTime
                    }
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (movable && semaphore.tryAcquire()) moveObjectThread.start()
            }
        }
        return false
    }

    /* val x = event?.x?.toDouble()
        val y = event?.y?.toDouble()
        var gameObjectSelected : GameObject? = null
        for (gameObject in gameObjectList) {
            if (gameObject.isClicked(x!!, y!!)) {
                gameObjectSelected = gameObject
                break
            }
        }

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (gameObjectSelected == null) {
                    gameObjectSelected = GameObject(context, x!!, y!!)
                    gameObjectList.add(gameObjectSelected)
                }

                if (gameObjectSelected.movable) {
                    objectMovedThread = Thread {
                        while (event.action != MotionEvent.ACTION_UP) {
                            gameObjectSelected.setPosition(event.x.toDouble(), event.y.toDouble())
                        }
                    }
                    objectMovedThread!!.start()
                }
            }
            MotionEvent.ACTION_UP -> {
                gameObjectSelected?.setPosition(event.x.toDouble(), event.y.toDouble())
                objectMovedThread?.interrupt()
            }
        }*/

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (positionX != other.positionX) return false
        if (positionY != other.positionY) return false
        if (paint != other.paint) return false
        if (movable != other.movable) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = positionX.hashCode()
        result = 31 * result + positionY.hashCode()
        result = 31 * result + paint.hashCode()
        result = 31 * result + movable.hashCode()
        result = 31 * result + z
        return result
    }


}