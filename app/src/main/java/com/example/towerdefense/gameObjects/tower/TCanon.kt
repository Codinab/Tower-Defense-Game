package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.gameObjects.tower.utils.CanonBall
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f

class TCanon(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {


    override var timeActionDelay = 1000f
    private var dph: Int = 1
    private var sizeCanonBall: Float = 30f

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
    
    private fun createCanonBall(position: Vector2f, rotation: Float): CanonBall {
        val circle = Circle(sizeCanonBall / 4, position)
        val canonBall = CanonBall(circle, 1)
        canonBall.velocity(20f)
        canonBall.setRotation(rotation)
        return canonBall
    }
    
    private fun addCanonBallsToProjectiles(canonBalls: List<CanonBall>) {
        gameView!!.surfaceView.projectiles.addAll(canonBalls)
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
        val canonBall = CanonBall(Circle(sizeCanonBall, Vector2f(box2D.body.position)), dph)
        canonBall.velocity(10f)
        val enemy = towerArea.toDamage()!!
        val rotation = Vector2f(enemy.position()).sub(box2D.body.position).normalize().angle()
        canonBall.setRotation(rotation)
        gameView!!.surfaceView.projectiles.add(canonBall)
    }
    
    override fun buildCost(): Int {
        return 1000
    }
    
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        val paint = Paint()
        //Color for each level more green
        paint.color = Color.rgb(0, 255 / 6 * level, 0)
        Drawing.drawBox2D(canvas, box2D, paint)
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
    
    override fun clone(): Tower {
        return TCanon(radius, box2D.clone() as Box2D)
    }
}