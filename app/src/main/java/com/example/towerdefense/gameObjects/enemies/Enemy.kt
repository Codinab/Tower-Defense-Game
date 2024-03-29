package com.example.towerdefense.gameObjects.enemies

import android.graphics.*
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.textures.Animation
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.io.Serializable

class Enemy(private var collider2D: Collider2D, private val road: Road, private val context: GameActivity) : Positionable, Serializable, Drawable,
    Movable {
    private var health: Int = 1
    private var maxHealth: Int = health
    private var animation: Animation
    
    private val enemyFrames = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.slime_f1),
        BitmapFactory.decodeResource(context.resources, R.drawable.slime_f2),
    )
    constructor(road: Road, context: GameActivity) : this(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road, context)
    
    init {
        
        setRotation(road.getFirstDirection().toAngle())
        position(road.getStart())
        
        animation = Animation(enemyFrames, 100f)
        velocity(velocity() + context.getEnemiesSpeed())
        health *= context.getDifficulty()
    }
    
    fun damage(damage: Int) {
        health -= damage
        if (health <= 0) {
            destroy()
            context.gameView()!!.money.addAndGet(10)
        }
    }
    
    override fun velocity(velocity: Float) {
        super.velocity(velocity)
        animation.updateFrameTime(500f / velocity)
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
        context.gameView()!!.gameHealth.getAndAdd(-1)
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
    
    
}