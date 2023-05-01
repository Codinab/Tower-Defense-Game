package com.example.towerdefense.gameObjects.enemies

import com.example.towerdefense.utility.Road

class EnemyGenerator(var health: Int, var velocity: Float) {
    
    
    fun getEnemy(road: Road): Enemy {
        val enemy = Enemy(road)
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