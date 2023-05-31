package com.example.towerdefense.gameObjects.enemies

import android.content.Context
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.utility.Interfaces.Removable
import com.example.towerdefense.utility.TimeController

class EnemyWave(private val enemyGenerators: ArrayList<EnemyGenerator>, private val timeBetweenEnemies: Long) : Removable {
    
    private var waveStartTime: Long = Long.MAX_VALUE
    private var lastEnemySpawned = 0L
    
    private var context: GameActivity? = null
    
    fun update() {
        if (context == null) return
        if (TimeController.isPaused()) return
        if (TimeController.getGameTime() < waveStartTime) return
        if (enemyGenerators.isNotEmpty() && TimeController.getGameTime() - lastEnemySpawned > timeBetweenEnemies) {
            context!!.gameView()!!.surfaceView.enemies
                .add(enemyGenerators.removeFirst().getEnemy(context!!.gameView()!!.surfaceView.road))
            lastEnemySpawned = TimeController.getGameTime()
        }
        if (enemyGenerators.isEmpty()) destroy()
    }
    
    fun getWaveStartTime(): Long {
        return waveStartTime
    }
    
    fun setWaveStartTime(waveStartTime: Long) {
        this.waveStartTime = waveStartTime
    }
    
    override var toDelete: Boolean = false
    
    companion object {
        //Returns an array of enemies of size and the EnemyGenerator lambda
        fun enemyWaveGenerator(size: Int, timeBetweenEnemies: Long, generator: () -> EnemyGenerator): EnemyWave {
            return EnemyWave(Array(size) { generator() }.toCollection(ArrayList()), timeBetweenEnemies)
        }
    }
    fun clone(context: GameActivity): EnemyWave {
        enemyGenerators.forEach { it.setContext(context) }
        return EnemyWave(ArrayList(enemyGenerators), timeBetweenEnemies).also { it.context = context }
    }
}