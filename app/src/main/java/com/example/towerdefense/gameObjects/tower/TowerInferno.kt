package com.example.towerdefense.gameObjects.tower

import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.TimeController

class TowerInferno(radius: Float, collider2D: Collider2D) : Tower(radius, collider2D) {


    override var dph = 0;
    private var dphLog = this.dph.toFloat()

    override fun applyDamageInArea() {
        if (readyToDamage()) {
            towerArea.toDamage()?.let {
                println("Damage: $dphLog")
                if(enemyHit == null || enemyHit != it) {
                    dphLog = dph.toFloat()
                    enemyHit = it
                }
                it.damage(dphLog.toInt())
                dphLog *= 1.01f
                if (it.getHealth() <= 0) lastHit = true
            }
            timeLastDamage = TimeController.getGameTime()
        }
    }
}