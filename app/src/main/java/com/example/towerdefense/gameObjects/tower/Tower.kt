package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import org.joml.Vector2f

abstract class Tower(var radius: Float, private val collider2D: Collider2D) : GameObject(collider2D),
    Drawable, java.io.Serializable {
    constructor(radius: Float, collider2D: Collider2D, dph: Int, hitDelay: Float) : this(
        radius,
        collider2D
    ) {
    }
    
    protected var timeLastAction: Long = 0L
    protected abstract var timeActionDelay: Float
    override lateinit var drawableObject: DrawableObject
    protected var level: Int = 1
    
    var towerArea: TowerArea = TowerArea(radius, collider2D.body)
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        collider2D.draw(canvas)
    }
    
    override fun update() {
        if (movable.get() || TimeController.isPaused()) return
        super.update()
        applyDamageInArea()
    }
    
    protected abstract fun applyDamageInArea()
    
    fun setToDamageType(type: TowerArea.DamageType) = towerArea.setToDamageType(type)
    
    fun getToDamageType(): TowerArea.DamageType = towerArea.getToDamageType()
    
    
    override fun handleDownEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (movable.get()) {
            gameView!!.showTowerButtons()
            position(position)
            return true
        } else if (isClicked(position)) {
            towerClicked = if (towerClicked == this) null else this
            if (towerClicked == null) {
                gameView!!.hideTowerButtons()
            } else if (towerClicked == this) {
                gameView!!.showTowerButtons()
            }
            val currentTime = System.currentTimeMillis()
            lastClickTime = currentTime
            return true
        }
        return false
    }
    
    override fun handleUpEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (fixable.get()) {
            fixable.set(false)
            movable.set(false)
            return true
        }
        return false
    }
    
    fun readyToDamage(): Boolean {
        return TimeController.getGameTime() - timeLastAction > timeActionDelay && towerArea.isNotEmpty() && !TimeController.isPaused() && !toDelete() && !movable.get()
    }
    
    
    abstract fun buildCost(): Int
    abstract fun upgrade()
    abstract fun upgradeCost(): Int
    abstract fun upgradeInfo(): String
    abstract fun clone(): Tower
    override fun toString(): String {
        return "Tower( radius=$radius, collider2D=$collider2D towerArea=$towerArea )"
    }
    
    fun updateArea(enemies: EnemyList) {
        towerArea.updateArea(enemies)
    }
}