package com.example.towerdefense.utility

import org.joml.Vector2f
import org.joml.Vector2i
import kotlin.math.atan2

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
    
        fun Vector2i.toVector2f(): Vector2f {
            return Vector2f(this.x.toFloat(), this.y.toFloat())
        }
        fun Vector2f.toVector2i(): Vector2i {
            return Vector2i(this.x.toInt(), this.y.toInt())
        }
    
        fun Vector2f.angle(): Float {
            return atan2(this.y.toDouble(), this.x.toDouble()).toFloat().radToDegrees()
        }
    
        fun Float.radToDegrees(): Float {
            return this * 180 / Math.PI.toFloat()
        }
    
        fun anglePositionToTarget(position: Vector2f, target: Vector2f): Float {
            return Vector2f(position).sub(target).normalize().angle()
        }
    
    }
}