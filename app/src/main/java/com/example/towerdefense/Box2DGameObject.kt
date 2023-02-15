package com.example.towerdefense

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f
import java.util.concurrent.Semaphore

class Box2DGameObject(size : Vector2f, body: Rigidbody2D, override var game: Game) :  Box2D(size, body), GameObject {
    override var movable: Boolean = true
    override var fixable: Boolean = false
    override var toDestroy: Boolean = false
    override var layerLevel: Int = 0

    @Temporary
    var paint = Paint()

    override fun draw(canvas: Canvas?) {
        paint.color = android.graphics.Color.YELLOW
        canvas?.drawRect(toRectF(), paint)
    }

    override fun update() {
        if (body.position.x > 1600) setVelocity(Vector2f(-4f, 0f))
        if (body.position.x < 100) setVelocity(Vector2f(4f, 0f))
        body.update()
    }

    override fun setPosition(position: Vector2f) {
        body.setTransform(position)
    }

    override fun addVelocity(velocity: Vector2f) {
        body.addVelocity(velocity);
    }

    override fun setVelocity(velocity: Vector2f) {
        body.velocity = velocity
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, this)
    }

    override var lastClickTime: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    override var semaphore: Semaphore
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    fun toRectF() : RectF {
        return RectF(center.x - halfSize.x, center.y - halfSize.y, center.x + halfSize.x, center.y + halfSize.y)
    }

}