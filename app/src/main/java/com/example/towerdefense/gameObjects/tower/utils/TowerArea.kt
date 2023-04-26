package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.Enemy
import com.example.towerdefense.gameObjects.lists.EnemyList
import org.joml.Vector2f

class TowerArea(rad: Float, center: Rigidbody2D) : Circle(rad, center) {
    constructor(radius: Float, center: Vector2f) : this(radius, Rigidbody2D(center))

    override fun draw(p0: Canvas) {
        //Draw circle with radius radius
        val paint = Paint()
        paint.color = Color.RED
        paint.alpha = 50
        p0.drawCircle(center.x, center.y, radius, paint)
        paint.alpha = 100
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f // adjust the stroke width as needed
        p0.drawCircle(center.x, center.y, radius, paint)
    }

    private var inArea = ArrayDeque<Enemy>()
    fun updateArea(enemies: EnemyList): Boolean {
        inArea.clear()
        for (enemy in enemies) {
            if (IntersectionDetector2D.intersection(this, enemy.collider2D())) inArea.add(enemy)
        }
        return inArea.isNotEmpty()
    }

    fun isEmpty(): Boolean = inArea.isEmpty()

    fun isNotEmpty(): Boolean = inArea.isNotEmpty()
    
    private var damageType = DamageType.FIRST
    fun toDamage(): Enemy? {
        return when (damageType) {
            DamageType.FIRST -> getFirst()
            DamageType.LAST -> getLast()
            DamageType.RANDOM -> getRandom()
            DamageType.MOST_HEALTH -> getMostHealthy()
            DamageType.LEAST_HEALTH -> getLeastHealthy()
            DamageType.FASTEST -> getFastest()
            DamageType.SLOWEST -> getSlowest()
        }
    }
    fun setToDamageType(damageType: DamageType) {
        this.damageType = damageType
    }
    
    fun nextDamageType() {
        if (damageType == DamageType.SLOWEST) setToDamageType(DamageType.FIRST)
        else setToDamageType(DamageType.values()[damageType.ordinal + 1])
    }
    
    
    fun getToDamageType(): DamageType = damageType
    
    
    fun getFirst(): Enemy? = inArea.minByOrNull { it.distanceToNextCornerSquared() }
    
    private fun getLast(): Enemy? = inArea.maxByOrNull { it.distanceToNextCornerSquared() }
    
    private fun getLeastHealthy(): Enemy? = inArea.minByOrNull { it.getMaxHealth() }
    
    
    private fun getMostHealthy(): Enemy? = inArea.maxByOrNull { it.getMaxHealth() }
    
    
    private fun getFastest(): Enemy? = inArea.maxByOrNull { it.velocity() }
    
    
    private fun getSlowest(): Enemy? = inArea.minByOrNull { it.velocity() }
    
    
    private fun getRandom(): Enemy? = inArea.randomOrNull()
    

    override fun clone(): Collider2D = TowerArea(radius, Vector2f(center))
    

    override fun toString(): String = "TowerArea(inArea=$inArea, damageType=$damageType)"
    

    enum class DamageType {
        FIRST, LAST, RANDOM, MOST_HEALTH, LEAST_HEALTH, FASTEST, SLOWEST;
    }


}