package com.example.towerdefense.gameObjects

import android.graphics.*
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.R
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.textures.Animation
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.io.Serializable
import kotlin.math.pow

class Enemy(collider2D: Collider2D, private val road: Road) : GameObject(collider2D), Serializable {
    private var health: Int = 100
    private var maxHealth: Int = health
    private var animation: Animation
    
    init {
        movable.set(false)
        fixable.set(false)
        
        setRotation(road.getFirstDirection().toAngle())
        position(road.getStart())
        
        val frame1 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f1)
        val frame2 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f2)
        val frame3 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f3)
        val frame4 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f4)
        val frame5 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f5)
        val frame6 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f6)
        val frame7 = BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slime_f7)
        
        
        val frames = arrayOf(frame1, frame2, frame3, frame4, frame5, frame6, frame7)
        animation = Animation(frames, 100f)
        velocity(9f)
    }
    
    fun damage(damage: Int) {
        health -= damage
        if (health <= 0) {
            destroy()
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
        
        
        val direction2D = road.getDirection(position(), corner, velocity())
        if (!JMath.compare(direction2D.toAngle(),getRotation(), 0.1f)) corner++
        setRotation(direction2D.toAngle())
        if (direction2D == Direction2D.UNDEFINED || toDelete()) {
            velocity(0f)
            gameHealth.getAndAdd(-1)
            destroy()
        }
        
    }
    
    override fun draw(canvas: Canvas) {
        if (toDelete()) return
        
        //super.draw(canvas)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.textSize = 50f
        val topLeft = position().sub(collider2D().layoutSize().mul(0.5f))
        val topRight = Vector2f(topLeft).add(collider2D().layoutSize().x, 0f)
        
        Drawing.drawHealthBar(canvas, topLeft, topRight, health, maxHealth)
        animation.draw(canvas, position())
    }
    
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        return false
    }
    
    override fun isClicked(position: Vector2f?): Boolean {
        return false
    }
    
    override fun toString(): String {
        return "Enemy(position=${position()}, velocity=${velocity()}, rotation=${getRotation()})"
    }
    
    override var toDelete: Boolean = false
    fun getHealth(): Int {
        return health
    }
    
    fun getMaxHealth(): Int {
        return maxHealth
    }
    
    fun setHealth(health: Int) {
        this.health = health
        this.maxHealth = health
    }
    
    fun distanceToNextCornerSquared(): Float {
        return road.distanceToNextCornerSquared(position(), corner)
    }
    
    
}