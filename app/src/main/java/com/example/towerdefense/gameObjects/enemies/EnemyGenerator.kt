package com.example.towerdefense.gameObjects.enemies

import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.utility.Road

class EnemyGenerator(var health: Int, var velocity: Float) {
    
    private var context: GameActivity? = null
    fun setContext(context: GameActivity) {
        this.context = context
    }
    fun getEnemy(road: Road): Enemy {
        if (this.context == null) {
            throw Exception("Context is null")
        }
        val enemy = Enemy(road, context!!)
        enemy.health(health)
        enemy.velocity(velocity)
        return enemy
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        
        other as EnemyGenerator
        
        if (health != other.health) return false
        if (velocity != other.velocity) return false
        
        return true
    }
    
    override fun hashCode(): Int {
        var result = health
        result = 31 * result + velocity.hashCode()
        return result
    }
    
}