package com.example.towerdefense.gameObjects.enemies

import com.example.towerdefense.utility.Interfaces.Removable
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView

class EnemyWave(private val enemyGenerators: ArrayList<EnemyGenerator>, private val timeBetweenEnemies: Long) : Removable {
    
    private var waveStartTime: Long = Long.MAX_VALUE
    private var lastEnemySpawned = 0L
    
    fun update() {
        if (TimeController.isPaused()) return
        if (TimeController.getGameTime() < waveStartTime) return
        if (enemyGenerators.isNotEmpty() && TimeController.getGameTime() - lastEnemySpawned > timeBetweenEnemies) {
            gameView!!.surfaceView.enemies
                .add(enemyGenerators.removeFirst().getEnemy(gameView!!.surfaceView.road))
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
    fun clone(): EnemyWave {
        return EnemyWave(ArrayList(enemyGenerators), timeBetweenEnemies)
    }
}