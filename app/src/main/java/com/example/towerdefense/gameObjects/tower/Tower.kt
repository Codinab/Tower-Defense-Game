package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.GameObject
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

abstract class Tower(var radius: Float, private val collider2D: Collider2D) : Movable,
    Drawable, java.io.Serializable, InputEvent {
    
    override var lastClickTime: Long = 0L
    
    protected var timeLastAction: Long = 0L
    protected abstract var timeActionDelay: Float
    protected var level: Int = 1
    
    var towerArea: TowerArea = TowerArea(radius, collider2D.body)
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        if(!fixable.get() && movable.get()) {
            Drawing.drawBox2D(canvas, collider() as Box2D, Paint().apply { color = Color.RED })
        } else if (movable.get()) {
            Drawing.drawBox2D(canvas, collider() as Box2D, Paint().apply { color = Color.GREEN })
        } else {
            Drawing.drawBox2D(canvas, collider() as Box2D, Paint())
        }
    }
    
    override fun update() {
        if (movable.get() || TimeController.isPaused()) return
        super.update()
        applyDamageInArea()
    }
    
    protected abstract fun applyDamageInArea()
    
    fun setToDamageType(type: TowerArea.DamageType) = towerArea.setToDamageType(type)
    
    fun nextToDamageType() = towerArea.nextDamageType()
    
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
    
    override fun handleMoveEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (movable.get()) {
            position(position)
            fixable.set(true)
            gameView!!.surfaceView.towers.filterNot { it == this }.forEach {
                if(IntersectionDetector2D.intersection(collider(), it.collider())) {
                    fixable.set(false)
                    println("Collision with ${it.javaClass.simpleName}")
                }
            }
            for (positionBox2D in gameView!!.surfaceView.road.getPositionBox2Ds()) {
                if(IntersectionDetector2D.intersection(collider(), positionBox2D)) {
                    fixable.set(false)
                }
            }
            if(gameView!!.surfaceView.road.distanceToRoad(position()) > 200000f)
                fixable.set(false)
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
    
    override var fixable: AtomicBoolean = AtomicBoolean(true)
    override var movable: AtomicBoolean = AtomicBoolean(true)
    override fun collider(): Collider2D {
        return collider2D
    }
    
    override var toDelete: Boolean = false
    override var layerLevel: Int = 2
    
    override fun toString(): String {
        return "Tower( radius=$radius, collider2D=${collider()} towerArea=$towerArea )"
    }
    
    fun updateArea(enemies: EnemyList) {
        towerArea.updateArea(enemies)
    }
}