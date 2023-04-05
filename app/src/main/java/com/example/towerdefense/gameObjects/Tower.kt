package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import androidx.core.view.allViews
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import org.joml.Vector2f

class Tower(var radius: Float, private val collider2D: Collider2D) : GameObject(collider2D),
    Drawable {
    constructor(collider2D: Collider2D) : this(300f, collider2D)

    private var enemyHit: Enemy? = null
    override lateinit var drawableObject: DrawableObject
    var towerArea: TowerArea = TowerArea(radius, collider2D.body)
    var dps = 10
    var paused = false
    override fun draw(canvas: Canvas) {
        if (clicked) towerArea.draw(canvas)
        collider2D.draw(canvas)
        if (damaged) drawHit(canvas)
    }

    private fun drawHit(canvas: Canvas) {
        Drawing.drawLine(canvas, collider2D.body.position, enemyHit!!.position(), 6f)
    }

    override fun handleUpEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (fixable.get()) {
            fixable.set(false)
            movable.set(false)
            return true

        }
        return false
    }

    override fun update() {
        if (movable.get()) return
        super.update()
        applyDamageInArea()
    }

    private var damaged: Boolean = false
    private var hitAngle = 0f
    private var timeLastDamage = 0L
    private fun applyDamageInArea() {
        if (towerArea.inArea.isNotEmpty()) {
            if (TimeController.getGameTime() - timeLastDamage > 1000) {
                towerArea.toDamage()?.let {
                    enemyHit = it
                    it.damage(dps)
                    damaged = !damaged
                }
                timeLastDamage = TimeController.getGameTime()
            }
        } else resetHitDrawing()
        if (TimeController.getGameTime() - timeLastDamage > 700) resetHitDrawing()
    }

    private fun resetHitDrawing() {
        damaged = false
        hitAngle = 0f
    }

    fun setToDamageType(type: TowerArea.DamageType) {
        towerArea.setToDamageType(type)
    }
    fun getToDamageType(): TowerArea.DamageType {
        return towerArea.getToDamageType()
    }


}