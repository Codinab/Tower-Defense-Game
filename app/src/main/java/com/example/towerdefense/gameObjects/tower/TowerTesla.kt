package com.example.towerdefense.gameObjects.tower

import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.utility.TimeController
import kotlin.math.max

class TowerTesla(radius: Float, collider2D: Collider2D) : Tower(radius, collider2D) {


    private var dphLog = dph.toFloat()

    override fun applyDamageInArea() {
        if (readyToDamage()) {
            towerArea.toDamage()?.let {
                println("Damage: $dphLog")
                if(enemyHit == null || enemyHit != it) {
                    dphLog = dph.toFloat()
                    enemyHit = it
                }
                it.damage(dphLog.toInt())
                dphLog *= 1.1f
                if (it.getHealth() <= 0) lastHit = true
            }
            timeLastDamage = TimeController.getGameTime()
        }
    }
}