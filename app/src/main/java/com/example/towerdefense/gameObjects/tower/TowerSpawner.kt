package com.example.towerdefense.gameObjects.tower

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.towerdefense.GameObjectView
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Updatable
import org.joml.Vector2f

@SuppressLint("ViewConstructor")
class TowerSpawner(context: Context, box2D: Box2D, var modelTower: Tower) :
    GameObjectView(context, box2D), Updatable {
    
    constructor(context: Context, position: Vector2f, modelTower: Tower) : this(
        context,
        Box2D(Vector2f(DEF_WIDTH, DEF_HEIGHT), Rigidbody2D(position)),
        modelTower
    )
    
    companion object {
        const val DEF_HEIGHT = 120f
        const val DEF_WIDTH = 120f
        const val PADDING = 20f
    }
    
    init {
        val layoutSize = box2D.layoutSize()
        layoutParams = ViewGroup.LayoutParams(layoutSize.x.toInt(), layoutSize.y.toInt())
        setPosition(getPosition())
        setBackgroundColor(Color.TRANSPARENT)
    }
    
    private var lastTower: Tower? = null
    var damageType = TowerArea.DamageType.FIRST
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (lastTower != null && lastTower!!.movable.get()) {
            return false
        }
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (money.getAndAdd(-modelTower.buildCost()) < modelTower.buildCost() ||
                    gameView!!.surfaceView.movableTower != null
                ) return false.also {
                    money.getAndAdd(
                        modelTower.buildCost()
                    )
                }
                val tower = modelTower.clone()
                
                gameView!!.surfaceView.towers.add(tower)
                gameView!!.surfaceView.movableTower = tower
                gameView!!.hideTowerButtons()
    
                lastTower = tower
                towerClicked = tower
                true
            }
            MotionEvent.ACTION_MOVE -> {
                /*if (lastTower!= null && IntersectionDetector2D.intersection(collider2D.clone().body.position.add(cameraPosition), lastTower!!.collider2D())) {
                    lastTower!!.hoverAboveDelete()
                } else if (lastTower!= null) {
                    lastTower!!.notHoverAboveDelete()
                }*/
                    true
            }
            MotionEvent.ACTION_UP -> {
                if (lastTower!= null && IntersectionDetector2D.intersection(collider2D.clone().body.position.add(cameraPosition), lastTower!!.collider())) {
                    lastTower!!.destroy()
                    return true
                }
                return false
            }
            else -> false
        }
    }
    
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), Paint().apply {
            color = Color.CYAN
        })
    }
    
    fun damageType(damageType: TowerArea.DamageType) {
        this.damageType = damageType
    }
    
    enum class SpawnerPosition(val vector2f: Vector2f) {
        TOP_LEFT(Vector2f(screenSize.x - DEF_WIDTH * 2 - PADDING, DEF_HEIGHT)),
        TOP_RIGHT(Vector2f(screenSize.x - DEF_WIDTH, DEF_HEIGHT)),
        MIDDLE_LEFT(Vector2f(screenSize.x - DEF_WIDTH * 2 - PADDING, DEF_HEIGHT * 2 + PADDING)),
        MIDDLE_RIGHT(Vector2f(screenSize.x - DEF_WIDTH, DEF_HEIGHT * 2 + PADDING)),
        BOTTOM_LEFT(Vector2f(screenSize.x - DEF_WIDTH * 2 - PADDING, DEF_HEIGHT * 3 + PADDING * 2)),
        BOTTOM_RIGHT(Vector2f(screenSize.x - DEF_WIDTH, DEF_HEIGHT * 3 + PADDING * 2))
    }
    
    override fun update() {
    
    }
    
}