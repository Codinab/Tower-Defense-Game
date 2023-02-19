package com.example.towerdefense

import android.graphics.Canvas
import android.graphics.Paint
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean

class CircleGameObject(radius: Float, body: Rigidbody2D, override var game: Game) :  Circle(radius, body), GameObject {

    @Temporary
    var paint: Paint = Paint()
    var creator: Boolean = false


    override var movable: AtomicBoolean = AtomicBoolean(true)
    override var fixable: AtomicBoolean = AtomicBoolean(false)
    override var toDestroy: Boolean = false
    override var layerLevel: Int = 0


    override fun draw(canvas: Canvas?) {
        @Temporary
        paint.color = when {
            IntersectionDetector2D.intersection(this, game.gameObjectCreator) -> android.graphics.Color.BLUE
            movable.get() && fixable.get() -> android.graphics.Color.GREEN
            movable.get() -> android.graphics.Color.RED
            else -> android.graphics.Color.WHITE
        }
        if (creator) {
            paint.color = android.graphics.Color.YELLOW
        }
        canvas?.drawCircle(onTouchEventPosition.x, onTouchEventPosition.y, radius, paint)
    }

    override fun update() {
        body.update()
    }

    override fun addVelocity(velocity: Float) {
        body.addVelocity(velocity);
    }

    override fun getVelocity(): Float {
        return body.velocity
    }

    override fun setVelocity(velocity: Float) {
        body.velocity = velocity
    }

    override fun setAngularVelocity(angularVelocity: Float) {
        body.angularVelocity = angularVelocity
    }

    override fun getAngularVelocity(): Float {
        return body.angularVelocity
    }

    override fun addAngularVelocity(angularVelocity: Float) {
        body.angularVelocity += angularVelocity
    }

    override fun setRotation(rotation: Float) {
        body.rotation = rotation
    }

    override fun getRotation(): Float {
        return body.rotation
    }

    override fun addRotation(rotation: Float) {
        body.rotation += rotation
    }

    override fun maxX(): Float {
        return body.position.x + radius
    }

    override fun minX(): Float {
        return body.position.x - radius
    }

    override fun maxY(): Float {
        return body.position.y + radius
    }

    override fun minY(): Float {
        return body.position.y - radius
    }

    override fun getPosition(): Vector2f {
        return body.position
    }

    override fun setPosition(position: Vector2f) {
        body.setTransform(position)
    }

    override fun setOffset(offset: Vector2f) {
        this.offset = offset
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, this)
    }


    override var lastClickTime: Long = 0
}