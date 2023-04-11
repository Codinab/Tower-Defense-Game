package com.example.towerdefense.gameObjects.tower

import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.gameObjects.tower.utils.CanonBall
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.angle
import com.example.towerdefense.utility.gameView
import org.joml.Vector2f

class TCanon(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {


    override var timeActionDelay = 1000f
    private var dph: Int = Int.MAX_VALUE
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            val canonBall = CanonBall(Circle(30f, Vector2f(box2D.body.position)), dph)
            canonBall.velocity(10f)
            val enemy = towerArea.getFirst()!!
            val rotation = Vector2f(enemy.position()).sub(box2D.body.position).normalize().angle()
            canonBall.setRotation(rotation)
            gameView!!.surfaceView.projectiles.add(canonBall)
            timeLastAction = TimeController.getGameTime()
        }
    }
    
    override fun upgrade() {
        dph += 10
    }
    
    override fun cost(): Int {
        return 1000
    }

    override fun clone(): Tower {
        return TCanon(radius, box2D.clone() as Box2D)
    }
}