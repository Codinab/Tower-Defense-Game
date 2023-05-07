package com.example.towerdefense.gameObjects.tower

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.KMath.Companion.anglePositionToTarget
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import kotlin.math.max

open class TLaser(radius: Float, private val box2D: Box2D) : Tower(radius, box2D),
    Drawable {
    
    constructor(position: Vector2f) : this(300f, Box2D(Vector2f(100f, 100f), Rigidbody2D(position)))
    
    
    
    private var enemyHit: Enemy? = null
    private var lastHit = false
    override var timeActionDelay: Float = 300f
    open var dph = 1
    private var textureResized = Bitmap.createScaledBitmap(texture, box2D.size.x.toInt() * 2, (box2D.size.y * 2.5).toInt(), false)
    
    override fun draw(canvas: Canvas) {
        //super.draw(canvas)
        if (towerClicked == this) towerArea.draw(canvas)
        drawPositionable(canvas)
        Drawing.drawBitmap(canvas, textureResized, position().sub(0f, box2D.size.y))
        drawHit(canvas)
    }
    
    private fun drawHit(canvas: Canvas) {
        if (enemyHit == null) return
        if (TimeController.getGameTime() - timeLastAction > timeActionDelay * 0.9) return
        if (enemyHit!!.health() <= 0 && !lastHit) return
        lastHit = false
        
        //Drawing.drawLine(canvas, position(), enemyHit!!.position(), 6f)
        //Use hitTexture now
        val distance = position().distance(enemyHit!!.position())
        val resizedHitTexture = Bitmap.createScaledBitmap(hitTexture, distance.toInt(), 100, false)
        val angle = anglePositionToTarget(position(), enemyHit!!.position())
        val randomX = (0..100).random().toFloat()
        val randomY = (0..100).random().toFloat()
        val positionTowerTop = position().sub(0f, (box2D.size.y * 3).toFloat()).add(randomX, randomY)
        val middle = positionTowerTop.add(enemyHit!!.position()).div(2f)
        Drawing.drawBitmap(canvas, resizedHitTexture, middle, angle)
        //val resizedTexture
        //Drawing.drawBitmap(canvas, hitTexture, position().sub(0f, box2D.size.y), enemyHit!!.position(), hitTexture.width.toFloat())
        
    }
    
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            towerArea.toDamage()?.let {
                enemyHit = it
                it.damage(dph)
                if (it.health() <= 0) lastHit = true
                timeLastAction = TimeController.getGameTime()
            }
        }
    }
    
    override fun buildCost(): Int {
        return 1000
    }
    
    override fun upgrade() {
        super.upgrade()
        val dpsTmp = dph.toFloat()
        dph = max((dpsTmp * 1.1f).toInt(), dph + 2)
        if (timeActionDelay > 100f) timeActionDelay -= 50f
    }
    
    override fun upgradeCost(): Int = 3000
    override fun upgradeInfo(): String {
        return "Damage per second: $dph"
    }
    
    override fun clone(): Tower = TLaser(radius, box2D.clone() as Box2D)
    override fun toString(): String {
        return "Tower(radius=$radius, collider2D=$box2D, enemyHit=$enemyHit, lastHit=$lastHit, towerArea=$towerArea, dph=$dph, timeLastDamage=$timeLastAction, hitDelay=$timeActionDelay)"
    }
    
    override var layerLevel: Int = 10
    
    companion object {
        private val texture = BitmapFactory.decodeResource(
            gameView!!.context.resources,
            com.example.towerdefense.R.drawable.tlaser
        )
        private val hitTexture = BitmapFactory.decodeResource(
            gameView!!.context.resources,
            com.example.towerdefense.R.drawable.thunder
        )
    }
}