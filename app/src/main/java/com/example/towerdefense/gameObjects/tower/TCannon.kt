package com.example.towerdefense.gameObjects.tower

import android.graphics.*
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.tower.utils.CannonBall
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.KMath.Companion.angle
import com.example.towerdefense.utility.KMath.Companion.anglePositionToTarget
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f

class TCannon(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {
    constructor(position: Vector2f) : this(300f, Box2D(Vector2f(150f, 150f), Rigidbody2D(position)))
    
    override var timeActionDelay = 1000f
    private var dph: Int = 1
    
    private var sizeCanonBall: Float = 30f
    private var textureResized = Bitmap.createScaledBitmap(texture, texture.width * 2, texture.height * 2, false)
    
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            shootBigCanonBall()
            if(level > 4) shootSmallCanonBalls()
            timeLastAction = TimeController.getGameTime()
        }
    }
    
    private fun calculateRotation(enemyPosition: Vector2f): Float {
        return Vector2f(enemyPosition).sub(box2D.body.position).angle()
    }
    
    private fun createCanonBall(position: Vector2f, rotation: Float): CannonBall {
        val circle = Circle(sizeCanonBall / 4, position)
        val cannonBall = CannonBall(circle, 1)
        cannonBall.velocity(20f)
        cannonBall.setRotation(rotation)
        return cannonBall
    }
    
    private fun addCanonBallsToProjectiles(cannonBalls: List<CannonBall>) {
        gameView!!.surfaceView.projectiles.addAll(cannonBalls)
    }
    
    
    private fun shootSmallCanonBalls() {
        val enemy = towerArea.toDamage()!!
        val rotation = calculateRotation(enemy.position())
        val canonBalls = listOf(
            createCanonBall(Vector2f(box2D.body.position), rotation + 10f),
            createCanonBall(Vector2f(box2D.body.position), rotation - 10f),
            createCanonBall(Vector2f(box2D.body.position), rotation + 20f),
            createCanonBall(Vector2f(box2D.body.position), rotation - 20f)
        )
        addCanonBallsToProjectiles(canonBalls)
    }
    
    private fun shootBigCanonBall() {
        val cannonBall = CannonBall(Circle(sizeCanonBall, Vector2f(box2D.body.position)), dph)
        cannonBall.velocity(10f)
        val enemy = towerArea.toDamage()!!
        val rotation = Vector2f(enemy.position()).sub(box2D.body.position).normalize().angle()
        cannonBall.setRotation(rotation)
        gameView!!.surfaceView.projectiles.add(cannonBall)
    }
    
    override fun buildCost(): Int {
        return 1000
    }
    
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        val paint = Paint()
        drawPositionable(canvas)
        //Color for each level more green
        paint.color = Color.rgb(0, 255 / 6 * level, 0)
        //Drawing.drawBox2D(canvas, box2D, paint)
        angleAnimation(canvas)
    }
    
    private var angle = 0f
    private fun angleAnimation(canvas: Canvas) {
        if (TimeController.getGameTime() > timeLastAction + timeActionDelay / 2) angle = towerArea.angle()
        else if (gameView!!.surfaceView.enemies.isEmpty())
            angle = anglePositionToTarget(position(), gameView!!.surfaceView.road.getStart())
        Drawing.drawBitmap(canvas, textureResized, position(), angle)
    }
    
    override fun upgrade() {
        when(level) {
            1 -> {
                towerArea.radius *= 2
            }
            2 -> {
                dph = 2
            }
            3 -> {
                timeActionDelay /= 2
                dph
            }
            5 -> {
                sizeCanonBall *= 2
                dph *= 2
            }
        }
        if (level < 6) level++
    }
    
    override fun upgradeCost(): Int {
        return when(level) {
            1 -> 400
            2 -> 700
            3 -> 1400
            4 -> 2000
            5 -> 3000
            else -> 0
        }
    }
    
    override fun upgradeInfo(): String {
        return when(level) {
            1 -> "Bigger area of vision"
            2 -> "Double damage"
            3 -> "Faster shooting"
            4 -> "Add small cannon balls"
            5 -> "Bigger cannon balls"
            else -> "Max level"
        }
    }
    
    override var layerLevel: Int = 5
    override fun clone(): Tower {
        return TCannon(radius, box2D.clone() as Box2D)
    }
    
    companion object {
        private val texture = BitmapFactory.decodeResource(
            gameView!!.context.resources,
            com.example.towerdefense.R.drawable.cannon
        )
    }
}
