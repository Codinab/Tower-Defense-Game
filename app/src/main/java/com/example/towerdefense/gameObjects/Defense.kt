package com.example.towerdefense.gameObjects

import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Positionable
import org.joml.Vector2f

abstract class Defense(var collider2D: Collider2D) : Drawable(), InputEvent, Positionable {
    
    private val paint = Paint()
    protected abstract val bitmapDrawable : BitmapDrawable
    
    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }
    override fun setColorFilter(p0: ColorFilter?) {
        paint.colorFilter = p0
    }
    
    override fun getOpacity(): Int {
        return paint.alpha
    }
    
    override var lastClickTime: Long = 0L
    
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        //The defense menu should pop up on click
        //The defense should be movable after the menu allows it
        TODO("Not yet implemented")
    }
    
    override fun isClicked(position: Vector2f?): Boolean {
        return IntersectionDetector2D.intersection(position, collider2D)
    }
    
    override fun setPosition(position: Vector2f) {
        collider2D.body.position = position
    }
    
    override fun getPosition(): Vector2f {
        return collider2D.body.position
    }
    
}