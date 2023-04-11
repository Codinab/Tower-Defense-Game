package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.textures.Drawing
import kotlin.math.max

open class TLaser(radius: Float, private val collider2D: Collider2D) : Tower(radius, collider2D),
    Drawable {
    constructor(radius: Float, collider2D: Collider2D, dph: Int, hitDelay: Float) : this(
        radius,
        collider2D
    ) {
        this.dph = dph
        this.timeActionDelay = hitDelay
    }
    
    private var enemyHit: Enemy? = null
    private var lastHit = false
    override var timeActionDelay: Float = 100f
    override lateinit var drawableObject: DrawableObject
    open var dph = 1
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        drawHit(canvas)
    }
    
    private fun drawHit(canvas: Canvas) {
        if (enemyHit == null) return
        if (TimeController.getGameTime() - timeLastAction > timeActionDelay * 0.9) return
        if (enemyHit!!.getHealth() <= 0 && !lastHit) return
        lastHit = false
        Drawing.drawLine(canvas, collider2D.body.position, enemyHit!!.position(), 6f)
    }
    
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            towerArea.toDamage()?.let {
                enemyHit = it
                it.damage(dph)
                if (it.getHealth() <= 0) lastHit = true
                timeLastAction = TimeController.getGameTime()
            }
        }
    }
    
    override fun buildCost(): Int {
        return 100
    }
    
    override fun upgrade() {
        val dpsTmp = dph.toFloat()
        dph = max((dpsTmp * 1.1f).toInt(), dph + 2)
    }
    
    override fun upgradeCost(): Int = 100
    override fun upgradeInfo(): String {
        return "Damage per second: $dph"
    }
    
    override fun clone(): Tower = TLaser(radius, collider2D.clone(), dph, timeActionDelay)
    override fun toString(): String {
        return "Tower(radius=$radius, collider2D=$collider2D, enemyHit=$enemyHit, lastHit=$lastHit, towerArea=$towerArea, dph=$dph, timeLastDamage=$timeLastAction, hitDelay=$timeActionDelay)"
    }
    
    
}