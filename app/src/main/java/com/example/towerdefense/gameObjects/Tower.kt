package com.example.towerdefense.gameObjects

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.Interfaces.Drawable
import org.joml.Vector2f
import java.util.Objects

class Tower(float: Float, private val collider2D: Collider2D) : GameObject(collider2D), Drawable {
    constructor(collider2D: Collider2D) : this(300f, collider2D)

    override lateinit var drawableObject: DrawableObject
    var towerArea: TowerArea = TowerArea(float, collider2D.body)
    var dps = 10
    var paused = false


    override fun draw(canvas: Canvas) {
        collider2D.draw(canvas)
        if (clicked) towerArea.draw(canvas)
    }

    override fun update() {
        if (handlePause() || movable.get()) return
        super.update()
        applyDamageInArea()
    }

    private fun applyDamageInArea() {
        if (towerArea.inArea.isNotEmpty()) {
            if (currentTime - timeLastDamage > 1000) {
                towerArea.getFirst()?.damage(dps)
                timeLastDamage = currentTime
            }
        }
    }

    private var timeLastDamage = 0L
    private var pauseStartTime: Long = 0
    private var totalPauseTime: Long = 0
    private var currentTime: Long = 0
    private var wasPaused = false

    private fun handlePause(): Boolean {
        return if (paused && !wasPaused) paused_notWasPaused()
        else if (paused && wasPaused) true
        else if (wasPaused) notPaused_wasPaused()
        else notPaused_notWasPaused()
    }

    private fun notPaused_notWasPaused(): Boolean {
        currentTime = System.currentTimeMillis() - totalPauseTime
        return false
    }

    private fun notPaused_wasPaused(): Boolean {
        if (pauseStartTime != 0L) {
            val pauseEndTime = System.currentTimeMillis()
            totalPauseTime += pauseEndTime - pauseStartTime
            pauseStartTime = 0
            wasPaused = false
        }
        currentTime = System.currentTimeMillis() - totalPauseTime
        return false
    }

    private fun paused_notWasPaused(): Boolean {
        if (pauseStartTime == 0L) {
            pauseStartTime = System.currentTimeMillis()
            wasPaused = true
        }
        return true
    }
}