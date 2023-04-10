package com.example.towerdefense.utility

import org.joml.Vector2f

class KMath {
    companion object {
    
        fun aim(shooterPos: Vector2f, objectPos: Vector2f, objectVel: Vector2f, bulletSpeed: Float): Float {
            val displacement = objectPos.sub(shooterPos, Vector2f())
            val distance = displacement.length()
            val timeToHit = distance / bulletSpeed
            val predictedPos = objectPos.add(objectVel.mul(timeToHit, Vector2f()), Vector2f())
            val aimDirection = predictedPos.sub(shooterPos, Vector2f()).normalize()
            return aimDirection.angle()
        }
    
    
    }
}