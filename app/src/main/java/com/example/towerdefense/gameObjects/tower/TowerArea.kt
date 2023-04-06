package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.utility.Interfaces.Drawable
import org.joml.Vector2f

class TowerArea(rad: Float, center: Rigidbody2D) : Circle(rad, center) {
    constructor(radius: Float, center: Vector2f) : this(radius, Rigidbody2D(center))

    var damageType = DamageType.FIRST

    override fun draw(p0: Canvas) {
        //Draw circle with radius radius
        val paint = Paint()
        paint.color = Color.RED
        paint.alpha = 100
        p0.drawCircle(center.x, center.y, radius, paint)
    }
    var inArea = ArrayDeque<Enemy>()

    fun isInside(enemy: Enemy): Boolean {
        val shouldBeInside = IntersectionDetector2D.intersection(this, enemy.collider2D())
        if (shouldBeInside) {
            if (!inArea.contains(enemy)) {
                inArea.add(enemy)
            }
        } else {
            inArea.remove(enemy)
        }
        return shouldBeInside
    }

    fun remove(enemy: Enemy) {
        inArea.remove(enemy)
    }

    private fun getFirst(): Enemy? {
        return inArea.firstOrNull()
    }

    fun toDamage(): Enemy? {
        return when (damageType) {
            DamageType.FIRST -> getFirst()
            DamageType.LAST -> getLast()
            DamageType.RANDOM -> getRandom()
            DamageType.MOST_HEALTH -> getMostHealthy()
            DamageType.LEAST_HEALTH -> getLeastHealthy()
            else -> getFirst()
        }
    }
    fun setToDamageType(damageType: DamageType) {
        this.damageType = damageType
    }
    fun getToDamageType(): DamageType {
        return damageType
    }

    private fun getLeastHealthy(): Enemy? {
        return inArea.minByOrNull { it.getHealth() }
    }

    private fun getMostHealthy(): Enemy? {
        return inArea.maxByOrNull { it.getHealth() }
    }

    private fun getRandom(): Enemy? {
        return inArea.randomOrNull()
    }

    fun getLast(): Enemy? {
        return inArea.lastOrNull()
    }
    override fun clone(): Collider2D {
        return TowerArea(radius, Vector2f(center))
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("TowerArea(inArea=")
        inArea.forEach { stringBuilder.append(" $it") }
        stringBuilder.append(")")
        return stringBuilder.toString()
    }

    enum class DamageType {
        FIRST, LAST, RANDOM, MOST_HEALTH, LEAST_HEALTH
    }
}