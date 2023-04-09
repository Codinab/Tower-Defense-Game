/*
package com.example.towerdefense.gameObjects.tower

import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.gameObjects.tower.utils.CanonBall
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.angle
import com.example.towerdefense.utility.gameView
import org.joml.Vector2f

class TRocketLauncher(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {
    override var timeActionDelay: Float = 1000f
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            val canonBall = CanonBall(Circle(30f, Vector2f(box2D.body.position)), dph)
            canonBall.setVelocity(10f)
            val enemy = towerArea.getFirst()!!
            val rotation = Vector2f(enemy.position()).sub(box2D.body.position).normalize().angle()
            canonBall.setRotation(rotation)
            gameView!!.surfaceView.projectiles.add(canonBall)
            timeLastAction = TimeController.getGameTime()
        }
    }
    
    override fun upgrade() {
        TODO("Not yet implemented")
    }
    
    override fun cost(): Int {
        TODO("Not yet implemented")
    }
    
    override fun clone(): Tower {
        TODO("Not yet implemented")
    }
}*/
