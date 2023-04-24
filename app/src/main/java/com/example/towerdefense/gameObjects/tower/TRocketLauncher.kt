package com.example.towerdefense.gameObjects.tower

import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.gameObjects.tower.utils.ExplosiveRocket
import com.example.towerdefense.utility.KMath.Companion.aim
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.angle
import com.example.towerdefense.utility.gameView
import org.joml.Vector2f

class TRocketLauncher(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {
    override var timeActionDelay: Float = 1000f
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            val enemy = towerArea.toDamage()!!
            val explosiveRocket = ExplosiveRocket(Circle(30f, Vector2f(box2D.body.position)), enemy)
            explosiveRocket.velocity(10f)
            val rotation = Vector2f(enemy.position()).sub(box2D.body.position).angle()
            explosiveRocket.setRotation(rotation)
            gameView!!.surfaceView.projectiles.add(explosiveRocket)
            timeLastAction = TimeController.getGameTime()
        }
    }
    
    override fun buildCost(): Int {
        return 1000
    }
    
    override fun upgrade() {
    }
    
    override fun upgradeCost(): Int {
        return 200
    }
    
    override fun upgradeInfo(): String {
        return "Upgrade not available"
    }
    
    override fun clone(): Tower {
        return TRocketLauncher(radius, box2D.clone() as Box2D)
    }
}
