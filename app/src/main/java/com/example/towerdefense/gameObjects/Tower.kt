package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import org.joml.Vector2f
import kotlin.math.max

class Tower(var radius: Float, private val collider2D: Collider2D) : GameObject(collider2D),
    Drawable {
    constructor(collider2D: Collider2D) : this(300f, collider2D)

    private var enemyHit: Enemy? = null
    private var lastHit = false
    override lateinit var drawableObject: DrawableObject
    var towerArea: TowerArea = TowerArea(radius, collider2D.body)
    var dph = 1
    var paused = false
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        collider2D.draw(canvas)
        drawHit(canvas)
    }

    private fun drawHit(canvas: Canvas) {
        if (enemyHit == null) return
        if (TimeController.getGameTime() - timeLastDamage > hitDelay * 0.9) return
        if (enemyHit!!.getHealth() <= 0 && !lastHit) return
        lastHit = false
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

    private var timeLastDamage = 0L
    var hitDelay = 100f
    private fun applyDamageInArea() {
        if (towerArea.inArea.isNotEmpty()) {
            if (TimeController.getGameTime() - timeLastDamage > hitDelay) {
                towerArea.toDamage()?.let {
                    enemyHit = it
                    it.damage(dph)
                    if (it.getHealth() <= 0) lastHit = true
                }
                timeLastDamage = TimeController.getGameTime()
            }
        }
    }

    fun setToDamageType(type: TowerArea.DamageType) {
        towerArea.setToDamageType(type)
    }

    override fun handleDownEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (movable.get()) {
            position(position)
            return true
        } else if (isClicked(position)) {
            towerClicked = if (towerClicked == this) null else this
            val currentTime = System.currentTimeMillis()
            lastClickTime = currentTime
            return true
        }
        return false
    }

    fun getToDamageType(): TowerArea.DamageType {
        return towerArea.getToDamageType()
    }

    fun upgrade() {
        val dpsTmp = dph.toFloat()
        dph = max((dpsTmp * 1.1f).toInt(), dph + 2)
    }

    private var toDestroy = false
    fun destroy() {
        toDestroy = true
    }

    fun getDestroyed(): Boolean {
        return toDestroy
    }


}