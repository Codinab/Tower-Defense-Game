package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import kotlin.math.max

open class TLaser(radius: Float, private val box2D: Box2D) : Tower(radius, box2D),
    Drawable {
    
    constructor(position: Vector2f) : this(300f, Box2D(Vector2f(100f, 100f), position))
    
    
    
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
        Drawing.drawLine(canvas, position(), enemyHit!!.position(), 6f)
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
    
    override fun clone(): Tower = TLaser(radius, box2D.clone() as Box2D)
    override fun toString(): String {
        return "Tower(radius=$radius, collider2D=$box2D, enemyHit=$enemyHit, lastHit=$lastHit, towerArea=$towerArea, dph=$dph, timeLastDamage=$timeLastAction, hitDelay=$timeActionDelay)"
    }
    
    
}