package com.example.towerdefense.gameObjects

import android.content.Context
import android.content.SyncRequest
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.towerdefense.GameObjectView
import com.example.towerdefense.GameSurfaceView
import com.example.towerdefense.GameView
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.utility.money
import org.joml.Vector2f

class TowerSpawner(
    box2D: Box2D,
    private val gameSurfaceView: GameSurfaceView,
    context: Context,
    gameView: GameView,
) :
    GameObjectView(context, gameView, box2D) {

    init {
        val layoutSize = box2D.layoutSize()
        layoutParams = ViewGroup.LayoutParams(layoutSize.x.toInt(), layoutSize.y.toInt())
        setPosition(getPosition())
        setBackgroundColor(Color.TRANSPARENT)
    }

    var areaRadius = 300f
    var towerDimensions = Vector2f(100f, 100f)
    var towerDPS = 10
    private var lastTower: Tower? = null
    var damageType = TowerArea.DamageType.FIRST
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
        if (lastTower != null && lastTower!!.movable.get()) {
            return false
        }
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (money.getAndAdd(-100) < 100) return false
                val tower =
                    Tower(areaRadius, Box2D(towerDimensions, Rigidbody2D(Vector2f(position))))
                tower.dps = towerDPS
                tower.setToDamageType(damageType)
                gameSurfaceView.towers.add(tower)
                gameSurfaceView.movableTowers.add(tower)
                lastTower = tower
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