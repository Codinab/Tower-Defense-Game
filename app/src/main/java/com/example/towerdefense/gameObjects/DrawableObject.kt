package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.example.towerdefense.Game
import com.example.towerdefense.Physics2d.primitives.Collider2D

abstract class DrawableObject(open var collider2D: Collider2D, private val game: Game) : Drawable() {

    private val paint = Paint()
    protected abstract val bitmapDrawable: BitmapDrawable?

    open fun update() {
    }

    override fun draw(p0: Canvas) {
        //Draw a debug rectangle of 100 by 100
        paint.color = android.graphics.Color.CYAN
        p0.drawRect(collider2D.body.position.x-50, collider2D.body.position.y-50, collider2D.body.position.x+50, collider2D.body.position.y+50, paint)
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