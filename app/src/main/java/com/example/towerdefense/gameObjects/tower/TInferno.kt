package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.textures.Drawing
import com.example.towerdefense.utility.TimeController
import org.joml.Vector2f
import java.lang.Float.max
import java.lang.Float.min

//Work in progress
class TInferno(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {
    constructor(position: Vector2f) : this(300f, Box2D(Vector2f(120f, 120f), position))
    
    init {
        setToDamageType(TowerArea.DamageType.MOST_HEALTH)
    }
    
    private var dphInferno = 0.1f
    private var dphLog = this.dphInferno
    override var timeActionDelay: Float = 100f
    
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        Drawing.drawText(canvas, dphLog.toString(), Vector2f(box2D.body.position).sub(box2D.halfSize).sub(0f, 10f), 20f)
        if (!movable.get()) rayAnimation(canvas)
    }
    
    private var angleAnimation = 0f
    private fun rayAnimation(canvas: Canvas) {
        val center = box2D.body.position
        val halfWidth = box2D.halfSize.x
        val halfHeight = box2D.halfSize.y
        val vectors = listOf(
            Vector2f(center.x + halfWidth, center.y),
            Vector2f(center.x - halfWidth, center.y),
            Vector2f(center.x, center.y + halfHeight),
            Vector2f(center.x, center.y - halfHeight)
        )
        
        val velocity = min((dphLog / dphInferno), 20f)
        angleAnimation += velocity
        
        rotateVectors(
            vectors, center,
            angleAnimation
        )
        
        
        drawCircles(canvas, vectors)
        drawRays(canvas, vectors, velocity)
        
        if (towerArea.isEmpty()) return
        
        drawTowerDamage(canvas)
        drawRayParts(canvas, vectors)
    }
    
    private fun rotateVectors(vectors: List<Vector2f>, center: Vector2f, angle: Float) {
        vectors.forEach { JMath.rotate(it, angle, center) }
    }
    
    private fun drawRays(canvas: Canvas, vectors: List<Vector2f>, velocity: Float) {
        val width = min(velocity, 10f)
        Drawing.drawLine(
            canvas,
            vectors[0],
            vectors[1],
            Paint().also { it.color = Color.YELLOW },
            width
        )
        Drawing.drawLine(
            canvas,
            vectors[2],
            vectors[3],
            Paint().also { it.color = Color.YELLOW },
            width
        )
    }
    
    private fun drawCircles(canvas: Canvas, vectors: List<Vector2f>) =
        vectors.forEach { drawCircleAnimation(canvas, it, 10f) }
    
    
    private fun drawTowerDamage(canvas: Canvas) {
        val enemy = towerArea.toDamage() ?: return
        drawCircleAnimation(canvas, enemy.position(), 20f)
    }
    
    private fun drawRayParts(canvas: Canvas, vectors: List<Vector2f>) {
        vectors.forEach { rayPartAnimation(canvas, it) }
    }
    
    private fun rayPartAnimation(canvas: Canvas, v1: Vector2f) {
        if (towerArea.toDamage() == null) return
        Drawing.drawLine(
            canvas,
            v1,
            towerArea.toDamage()!!.position(),
            Paint().also { it.color = Color.YELLOW },
            3f
        )
    }
    
    private fun drawCircleAnimation(canvas: Canvas, position: Vector2f, radius: Float) {
        val paint = Paint().also {
            it.color = Color.YELLOW
            it.alpha = 200
        }
        canvas.drawCircle(position.x, position.y, radius, paint)
    }
    
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            towerArea.toDamage()?.let {
                it.damage(dphLog.toInt())
                dphLog *= 1.03f
                if (it.health() <= 0) it.destroy()
            }
            timeLastAction = TimeController.getGameTime()
        } else if (towerArea.isEmpty()) dphLog = max(dphInferno, dphLog.div(1.001f))
        
    }
    
    override fun buildCost(): Int {
        return 100
    }
    
    override fun upgrade() {
        super.upgrade()
        dphInferno *= 1.5f
    }
    
    override fun upgradeCost(): Int {
        return 100
    }
    
    override fun upgradeInfo(): String {
        return "Damage per second: $dphInferno"
    }
    
    override fun clone(): Tower {
        return TInferno(radius, box2D.clone() as Box2D)
    }
    
}