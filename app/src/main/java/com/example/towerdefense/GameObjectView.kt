package com.example.towerdefense

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import org.joml.Vector2f

open class GameObjectView(context: Context, viewGroup: ViewGroup, var collider2D: Collider2D) : View(context) {

    var lastClickTime: Long = 0
    var movable: Boolean = true
    var fixable: Boolean = true
    init {
        val layoutSize = collider2D.layoutSize()
        layoutParams = ViewGroup.LayoutParams(layoutSize.x.toInt(), layoutSize.y.toInt())
        setPosition(collider2D.body.position)
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutSize = collider2D.layoutSize()
        setMeasuredDimension(layoutSize.x.toInt(), layoutSize.y.toInt())
    }

    open fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> handleDownEvent(event, position)
            MotionEvent.ACTION_MOVE -> handleMoveEvent(event, position)
            MotionEvent.ACTION_UP -> handleUpEvent(event, position)
        }
        return movable || fixable
    }

    fun handleDownEvent(event: MotionEvent, position: Vector2f) {
        println("${this} DOWN")
        if (movable) setPosition(Vector2f(position).sub(this.width / 2.toFloat(), this.height / 2.toFloat()))
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 300) {
            movable = true
            fixable = true
        }
        lastClickTime = currentTime

    }

    fun handleUpEvent(event: MotionEvent, position: Vector2f) {
        println("${this} UP")
        if (fixable) {
            fixable = false
            movable = false
        }
    }

    fun handleMoveEvent(event: MotionEvent, position: Vector2f) {
        println("${this} MOVE")
        if (movable) setPosition(Vector2f(position).sub(this.width / 2.toFloat(), this.height / 2.toFloat()))
    }


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var text = ""
    var textSize = 20f
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (text != "") {
            paint.color = Color.BLACK
            paint.textSize = textSize
            canvas.drawText(text, 0f, 50f, paint)
        }

    }



    fun setPosition(position: Vector2f) {
        x = position.x
        y = position.y
        collider2D.body.position = position
    }

    fun getPosition(): Vector2f {
        return collider2D.body.position
    }
    fun isClicked(vector2f: Vector2f): Boolean {
        return IntersectionDetector2D.intersection(vector2f, collider2D)
    }

    fun update() {

    }
}
