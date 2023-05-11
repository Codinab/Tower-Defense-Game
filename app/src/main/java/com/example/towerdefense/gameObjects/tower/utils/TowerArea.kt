package com.example.towerdefense.gameObjects.tower.utils

import android.graphics.*
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.enemies.Enemy
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.utility.KMath.Companion.anglePositionToTarget
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
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
    /*fun updateArea(enemies: EnemyList): Boolean {
        val tmpArea = ArrayDeque<Enemy>()
        for (enemy in enemies) {
            if (IntersectionDetector2D.intersection(this, enemy.collider())) tmpArea.add(enemy)
        }
        if (!tmpArea.containsAll(inArea) || !inArea.containsAll(tmpArea)) inArea = tmpArea
        return inArea.isNotEmpty()
    }*/
    
    fun updateArea(enemies: EnemyList): Boolean {
        val tmpArea = ArrayDeque<Enemy>()
        synchronized(enemies) {
            for (enemy in enemies) {
                if (IntersectionDetector2D.intersection(this, enemy.collider())) tmpArea.add(enemy)
            }
        }
        synchronized(inArea) {
            if (!tmpArea.containsAll(inArea) || !inArea.containsAll(tmpArea)) {
                inArea.clear()
                inArea.addAll(tmpArea)
            }
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
    
    fun toDamageList(): ArrayList<Enemy>? {
        val toDamageList = ArrayList<Enemy>()
        while(inArea.isNotEmpty()) {
            toDamageList.add(toDamage() ?: return null)
            inArea.remove(toDamageList.last())
        }
        return if (toDamageList.isEmpty()) null else toDamageList
    }
    
    
    fun setToDamageType(damageType: DamageType) {
        this.damageType = damageType
    }
    
    fun nextDamageType() {
        if (damageType == DamageType.SLOWEST) setToDamageType(DamageType.FIRST)
        else setToDamageType(DamageType.values()[damageType.ordinal + 1])
    }
    
    
    fun getToDamageType(): DamageType = damageType
    
    
    fun getFirst(): Enemy? {
        val nonNullInArea = inArea.filterNotNull()
        if (nonNullInArea.isEmpty()) return null
        
        return try {
            val corner = nonNullInArea.maxByOrNull { it.corner() }?.corner() ?: return null
            val filteredList = nonNullInArea.filter { it.corner() == corner }
            if (filteredList.isNotEmpty()) {
                filteredList.minByOrNull { it.distanceToNextCornerSquared() }
            } else {
                null
            }
        } catch (e: Exception) {
            // Log the exception or handle it as required
            null
        }
    }
    
    
    
    private fun getLast(): Enemy? {
        val nonNullInArea = inArea.filterNotNull()
        if (nonNullInArea.isEmpty()) return null
        
        return try {
            val corner = nonNullInArea.minByOrNull { it.corner() }?.corner() ?: return null
            val filteredList = nonNullInArea.filter { it.corner() == corner }
            if (filteredList.isNotEmpty()) {
                filteredList.maxByOrNull { it.distanceToNextCornerSquared() }
            } else {
                null
            }
        } catch (e: Exception) {
            // Log the exception or handle it as required
            null
        }
    }
    
    
    
    
    
    private fun getLeastHealthy(): Enemy? = inArea.minByOrNull { it.getMaxHealth() }
    
    
    private fun getMostHealthy(): Enemy? = inArea.maxByOrNull { it.getMaxHealth() }
    
    
    private fun getFastest(): Enemy? = inArea.maxByOrNull { it.velocity() }
    
    
    private fun getSlowest(): Enemy? = inArea.minByOrNull { it.velocity() }
    
    
    private fun getRandom(): Enemy? = inArea.randomOrNull()
    
    private var savedAngle = 0f
    fun angle(): Float {
        val enemy = toDamage()
        if (enemy != null) {
            savedAngle = anglePositionToTarget(center, enemy.position())
        }
        return savedAngle
    }
    
    
    override fun clone(): Collider2D = TowerArea(radius, Vector2f(center))
    
    
    override fun toString(): String = "TowerArea(inArea=$inArea, damageType=$damageType)"
    
    
    enum class DamageType {
        FIRST, LAST, RANDOM, MOST_HEALTH, LEAST_HEALTH, FASTEST, SLOWEST;
        
        companion object {
            private fun firstBitmap(): Bitmap {
                if (firstBitmap == null) firstBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.first_damage)
                return firstBitmap!!
            }
            
            private fun lastBitmap(): Bitmap {
                if (lastBitmap == null) lastBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.last_damage)
                return lastBitmap!!
            }
            
            private fun randomBitmap(): Bitmap {
                if (randomBitmap == null) randomBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.random_damage)
                return randomBitmap!!
            }
            
            private fun mostHealthBitmap(): Bitmap {
                if (mostHealthBitmap == null) mostHealthBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.most_health_damage)
                return mostHealthBitmap!!
            }
            
            private fun leastHealthBitmap(): Bitmap {
                if (leastHealthBitmap == null) leastHealthBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.least_health_damage)
                return leastHealthBitmap!!
            }
            
            private fun fastestBitmap(): Bitmap {
                if (fastestBitmap == null) fastestBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.fastest_damage)
                return fastestBitmap!!
            }
            
            private fun slowestBitmap(): Bitmap {
                if (slowestBitmap == null) slowestBitmap =
                    BitmapFactory.decodeResource(gameView!!.context.resources, R.drawable.slowest_damage)
                return slowestBitmap!!
            }
            
            private var firstBitmap: Bitmap? = null
            private var lastBitmap: Bitmap? = null
            private var randomBitmap: Bitmap? = null
            private var mostHealthBitmap: Bitmap? = null
            private var leastHealthBitmap: Bitmap? = null
            private var fastestBitmap: Bitmap? = null
            private var slowestBitmap: Bitmap? = null
        }
        
        fun getBitmap(): Bitmap {
            return when (this) {
                FIRST -> firstBitmap()
                LAST -> lastBitmap()
                RANDOM -> randomBitmap()
                MOST_HEALTH -> mostHealthBitmap()
                LEAST_HEALTH -> leastHealthBitmap()
                FASTEST -> fastestBitmap()
                SLOWEST -> slowestBitmap()
            }
        }
    }
    
}