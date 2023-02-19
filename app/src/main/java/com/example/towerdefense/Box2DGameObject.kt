package com.example.towerdefense

import android.graphics.*
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean

class Box2DGameObject(size : Vector2f, body: Rigidbody2D, override var game: Game) :  Box2D(size, body), GameObject {
    override var movable: AtomicBoolean = AtomicBoolean(true)
    override var fixable: AtomicBoolean = AtomicBoolean(false)
    override var toDestroy: Boolean = false
    override var layerLevel: Int = 0

    @Temporary
    var paint = Paint()
    var creator = false

    override fun draw(canvas: Canvas?) {
        @Temporary
        paint.color = when {
            IntersectionDetector2D.intersection(this, game.gameObjectCreator) -> Color.BLUE
            movable.get() && fixable.get() -> Color.GREEN
            movable.get() -> Color.RED
            else -> Color.WHITE
        }
        if (creator) {
            paint.color = Color.YELLOW
        }
        canvas?.save()
        canvas?.rotate(body.rotation, position.x, position.y)

        canvas?.drawRect(toRectF(), paint)
        //val image = BitmapFactory.decodeResource(game.context.resources, R.drawable.sense)

        ////Rect() of the source image 512x512
        //var rect = Rect(0, 0, 116, 168)
        //canvas?.drawBitmap(image, rect, Rect(0, 0, 512, 512), paint)

        canvas?.restore()
    }

    var big = false

    override fun update() {
        if (!big) {
            addSize(Vector2f(2f, 2f))
        } else {
            addSize(Vector2f(-2f, -2f))
        }
        if (size.x > 200) {
            big = true
        }
        if (size.x < 100) {
            big = false
        }
        body.update()
    }
    override fun setPosition(position: Vector2f) {
        body.setTransform(position)
    }

    override fun setOffset(offset: Vector2f) {
        this.offset = offset
    }

    override fun addVelocity(velocity: Float) {
        body.addVelocity(velocity)
    }

    override fun getVelocity(): Float {
        return body.velocity
    }

    override fun setVelocity(velocity: Float) {
        body.velocity = velocity
    }

    override fun setAngularVelocity(angularVelocity: Float) {
        rigidbody.angularVelocity = angularVelocity
    }

    override fun getAngularVelocity(): Float {
        return rigidbody.angularVelocity
    }

    override fun addAngularVelocity(angularVelocity: Float) {
        rigidbody.angularVelocity += angularVelocity
    }

    override fun setRotation(rotation: Float) {
        rigidbody.rotation = rotation
    }

    override fun getRotation(): Float {
        return rigidbody.rotation
    }

    override fun addRotation(rotation: Float) {
        rigidbody.rotation += rotation
    }

    override fun maxX(): Float {
        return maxOf(vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x)
    }

    override fun minX(): Float {
        return minOf(vertices[0].x, vertices[1].x, vertices[2].x, vertices[3].x)
    }

    override fun maxY(): Float {
        return maxOf(vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y)
    }

    override fun minY(): Float {
        return minOf(vertices[0].y, vertices[1].y, vertices[2].y, vertices[3].y)
    }

    override fun getPosition(): Vector2f {
        return body.position
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, this)
    }

    override var lastClickTime: Long = 0
    private fun toRectF() : Rect {
        return Rect((position.x - halfSize.x).toInt(),
            (position.y - halfSize.y).toInt(),
            (position.x + halfSize.x).toInt(), (position.y + halfSize.y).toInt()
        )
    }

}