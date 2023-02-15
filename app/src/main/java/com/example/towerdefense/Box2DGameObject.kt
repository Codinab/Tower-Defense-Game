package com.example.towerdefense

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
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
    var creator = false

    override fun draw(canvas: Canvas?) {
        @Temporary
        paint.color = when {
            IntersectionDetector2D.intersection(this, game.gameObjectCreator) -> android.graphics.Color.BLUE
            movable && fixable -> android.graphics.Color.GREEN
            movable -> android.graphics.Color.RED
            else -> android.graphics.Color.WHITE
        }
        if (creator) {
            paint.color = android.graphics.Color.YELLOW
        }
        canvas?.save()
        canvas?.rotate(body.rotation, getRawPosition().x, getRawPosition().y)
        canvas?.drawRect(toRectF(), paint)
        canvas?.restore()
    }

    override fun update() {
        body.update()
    }

    override fun setPosition(position: Vector2f) {
        body.setTransform(position)
    }

    override fun setOffset(offset: Vector2f) {
        this.offset = offset
    }

    override fun addVelocity(velocity: Vector2f) {
        body.addVelocity(velocity);
    }

    override fun getVelocity(): Vector2f {
        return body.velocity
    }

    override fun setVelocity(velocity: Vector2f) {
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

    fun getRawPosition(): Vector2f {
        return body.position
    }

    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, this)
    }

    override var lastClickTime: Long = 0
    override var semaphore: Semaphore = Semaphore(1)



    fun toRectF() : RectF {
        return RectF(getRawPosition().x - halfSize.x, getRawPosition().y - halfSize.y, getRawPosition().x + halfSize.x, getRawPosition().y + halfSize.y)
    }

}