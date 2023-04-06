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
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.money
import com.example.towerdefense.utility.towerClicked
import org.joml.Vector2f

@SuppressLint("ViewConstructor")
class TowerSpawner(context: Context, box2D: Box2D, var modelTower: Tower) :
    GameObjectView(context, box2D) {

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
                if (money.getAndAdd(-modelTower.cost()) < modelTower.cost() ||
                    gameView!!.surfaceView.movableTowers.isNotEmpty()
                ) return false.also {
                    money.getAndAdd(
                        modelTower.cost()
                    )
                }
                val tower = modelTower.clone()

                gameView!!.surfaceView.towers.add(tower)
                gameView!!.surfaceView.movableTowers.add(tower)

                lastTower = tower
                towerClicked = tower
                true
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
    
}