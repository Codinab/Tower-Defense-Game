package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.towerdefense.Game
import com.example.towerdefense.GameView
import com.example.towerdefense.Physics2d.primitives.Collider2D

open class DrawableObject(var collider2D: Collider2D) : Drawable() {
    constructor(collider2D: Collider2D,  bitmapDrawable: BitmapDrawable) : this(collider2D) {
        this.bitmapDrawable = bitmapDrawable
    }

    private val paint = Paint()
    public var bitmapDrawable: BitmapDrawable? = null

    open fun update() {
        collider2D.body.update()
    }

    override fun draw(p0: Canvas) {
        if (bitmapDrawable != null) {
            bitmapDrawable!!.draw(p0)
        } else {
            //Draw a debug rectangle of 100 by 100
            paint.color = android.graphics.Color.BLACK
            paint.alpha = 200
            p0.drawRect(collider2D.body.position.x-50, collider2D.body.position.y-50, collider2D.body.position.x+50, collider2D.body.position.y+50, paint)
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(p0: ColorFilter?) {
        paint.colorFilter = p0
    }

    override fun getOpacity(): Int {
        return paint.alpha
    }
}