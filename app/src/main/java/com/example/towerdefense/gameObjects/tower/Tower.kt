package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

abstract class Tower(var radius: Float, private val box2D: Box2D) : Movable,
    Drawable, java.io.Serializable, InputEvent {
    
    override var lastClickTime: Long = 0L
    
    protected var timeLastAction: Long = 0L
    protected abstract var timeActionDelay: Float
    protected var level: Int = 1
    
    var towerArea: TowerArea = TowerArea(radius, box2D.body)
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        drawPositionable(canvas)
        Drawing.drawBox2D(canvas, collider(), Paint())
    }
    
    fun drawPositionable(canvas: Canvas) {
        val box2D = box2D.clone() as Box2D
        box2D.size.mul(1.1f)
        if(!fixable.get() && movable.get()) {
            Drawing.drawBox2D(canvas, box2D, Paint().apply { color = Color.RED; alpha = 100  })
        } else if (movable.get()) {
            Drawing.drawBox2D(canvas, box2D, Paint().apply { color = Color.GREEN; alpha = 100 })
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
            if(gameView!!.surfaceView.road.getPositionableAreaBox2Ds(3)
                .any { IntersectionDetector2D.intersection(collider(), it)}) {
                fixable.set(true)
                println("Placeable in Area")
            } else
                fixable.set(false)
                
            
            gameView!!.surfaceView.towers.filterNot { it == this }.forEach {
                if(IntersectionDetector2D.intersection(collider(), it.collider())) {
                    fixable.set(false)
                    println("Collision with ${it.javaClass.simpleName}")
                }
            }
            for (positionBox2D in gameView!!.surfaceView.road.getPositionBox2Ds()) {
                if(IntersectionDetector2D.intersection(collider(), positionBox2D)) {
                    fixable.set(false)
                    println("Collision with ${positionBox2D.javaClass.simpleName}")
                }
            }
            
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
    override fun collider(): Box2D {
        return box2D
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