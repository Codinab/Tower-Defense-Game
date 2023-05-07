package com.example.towerdefense.gameObjects.enemies

import android.graphics.*
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.Interfaces.Stateful
import com.example.towerdefense.utility.textures.Animation
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.io.Serializable
import java.util.concurrent.atomic.AtomicBoolean

class Enemy(private var collider2D: Collider2D, private val road: Road) : Positionable, Serializable, Drawable,
    Movable {
    private var health: Int = 1
    private var maxHealth: Int = health
    private var animation: Animation
    
    constructor(road: Road) : this(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road)
    
    init {
        
        setRotation(road.getFirstDirection().toAngle())
        position(road.getStart())
        
        animation = Animation(enemyFrames, 100f)
        velocity(9f)
    }
    
    fun damage(damage: Int) {
        health -= damage
        if (health <= 0) {
            destroy()
            money.addAndGet(10)
        }
    }
    
    override fun velocity(v: Float) {
        super.velocity(v)
        animation.updateFrameTime(500f / v)
    }
    
    private var corner = 0
    override fun update() {
        if (TimeController.isPaused() || toDelete()) return
        
        super.update()
        
        val direction2D = updateDirection()
        
        if (direction2D == Direction2D.UNDEFINED) gameDamage()
    }
    
    fun corner(): Int {
        return corner
    }
    
    private fun gameDamage() {
        gameHealth.getAndAdd(-1)
        destroy()
    }
    
    override fun collider(): Collider2D {
        return collider2D
    }
    
    private fun updateDirection(): Direction2D {
        val direction2D = road.getDirection(position(), corner, velocity())
        if (!JMath.compare(direction2D.toAngle(), getRotation(), 0.1f)) corner++
        setRotation(direction2D.toAngle())
        return direction2D
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        
        //super.draw(canvas)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.textSize = 50f
        val topLeft = position().sub(collider().layoutSize().mul(0.5f))
        val topRight = Vector2f(topLeft).add(collider().layoutSize().x, 0f)
        
        Drawing.drawHealthBar(canvas, topLeft, topRight, health, maxHealth)
        animation.draw(canvas, position())
    }
    
    override fun toString(): String {
        return "Enemy(position=${position()}, velocity=${velocity()}, rotation=${getRotation()})"
    }
    
    override var toDelete: Boolean = false
    override fun destroy() {
        super.destroy()
        velocity(0f)
    }
    
    fun health(): Int {
        return health
    }
    
    fun getMaxHealth(): Int {
        return maxHealth
    }
    
    fun health(health: Int) {
        this.health = health
        this.maxHealth = health
    }
    
    fun distanceToNextCornerSquared(): Float {
        return road.distanceToNextCornerSquared(position(), corner)
    }
    
    companion object {
        private val enemyFrames = arrayOf(
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f1),
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f2),
            /*BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f3),
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f4),
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f5),
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f6),
            BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f7)*/
        )
    }
    
}