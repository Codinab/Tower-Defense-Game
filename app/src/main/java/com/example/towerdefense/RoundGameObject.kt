package com.example.towerdefense

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f
import java.util.Objects
import java.util.concurrent.Semaphore

class RoundGameObject(radius: Float, rigidbody: Rigidbody2D, override var game: Game) :  Circle(radius, rigidbody), GameObject {

    @Temporary
    private var paint: Paint = Paint()

    override var movable: Boolean = true
    override var fixable: Boolean = false
    override var layerLevel: Int = 0


    @Temporary
    override fun draw(canvas: Canvas?) {
        paint.color = when {
            movable && fixable -> android.graphics.Color.GREEN
            movable -> android.graphics.Color.RED
            else -> android.graphics.Color.WHITE
        }
        canvas?.drawCircle(center.x, center.y, radius, paint)
    }

    override fun update() {
        //TODO("Not yet implemented")
    }

    @Synchronized
    override fun setPosition(position: Vector2f?) {
        if (position == null) return
        body.setTransform(position)
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, this)
    }


    override var lastClickTime: Long = 0
    override var semaphore: Semaphore = Semaphore(1)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        fun position() = Vector2f(event.x, event.y)

        val moveObjectThread = Thread {
            //paint.color = android.graphics.Color.RED
            while (event.action != MotionEvent.ACTION_UP && movable) {
                setPosition(position())
            }
            if (fixable) movable = false
            semaphore.release()
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isClicked(position())) {
                    if (movable && semaphore.tryAcquire()) moveObjectThread.start()
                    else {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime < 300 && game.money >= 2) {
                            game.money -= 2
                            movable = true
                            fixable = false
                        }
                        lastClickTime = currentTime
                    }
                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (movable && semaphore.tryAcquire()) {
                    moveObjectThread.start()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (fixable) movable = false
            }
        }
        return false
    }

}