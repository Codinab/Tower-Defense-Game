package com.example.towerdefense.gameObjects.enemies

import com.example.towerdefense.gameObjects.enemies.EnemyWave.Companion.enemyWaveGenerator

enum class EnemyWaves(private val wave: EnemyWave, private val difficulty: Int) {
    
    
    
    Tier1Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(1, 9f) }, 1),
    Tier1Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(1, 9f) }, 2),
    Tier1Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(1, 9f) }, 3),
    Tier1Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(1, 9f) }, 4),
    Tier1Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(1, 9f) }, 5),
    Tier1Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(1, 9f) }, 6),
    Tier1Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(1, 9f) }, 7),
    Tier1Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(1, 9f) }, 8),
    Tier1Wave9(enemyWaveGenerator(100, 5L) { EnemyGenerator(1, 9f) }, 9),
    
    Tier2Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(2, 9f) }, 3),
    Tier2Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(2, 9f) }, 4),
    Tier2Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(2, 9f) }, 5),
    Tier2Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(2, 9f) }, 6),
    Tier2Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(2, 9f) }, 7),
    Tier2Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(2, 9f) }, 8),
    Tier2Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(2, 9f) }, 9),
    Tier2Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(2, 9f) }, 10),
    
    Tier3Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(3, 9f) }, 5),
    Tier3Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(3, 9f) }, 6),
    Tier3Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(3, 9f) }, 7),
    Tier3Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(3, 9f) }, 8),
    Tier3Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(3, 9f) }, 9),
    Tier3Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(3, 9f) }, 10),
    Tier3Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(3, 9f) }, 11),
    Tier3Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(3, 9f) }, 12),
    
    Tier4Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(4, 13f) }, 9),
    Tier4Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(4, 13f) }, 10),
    Tier4Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(4, 13f) }, 11),
    Tier4Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(4, 13f) }, 12),
    Tier4Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(4, 13f) }, 13),
    Tier4Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(4, 13f) }, 14),
    Tier4Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(4, 13f) }, 15),
    Tier4Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(4, 13f) }, 16),
    Tier4Wave9(enemyWaveGenerator(100, 5L) { EnemyGenerator(4, 13f) }, 17),
    
    Tier5Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(5, 15f) }, 14),
    Tier5Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(5, 15f) }, 14),
    Tier5Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(5, 15f) }, 15),
    Tier5Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(5, 15f) }, 16),
    Tier5Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(5, 15f) }, 17),
    Tier5Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(5, 15f) }, 18),
    Tier5Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(5, 15f) }, 19),
    Tier5Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(5, 15f) }, 20),
    
    Tier6Wave1(enemyWaveGenerator(10, 500L) { EnemyGenerator(6, 15f) }, 19),
    Tier6Wave2(enemyWaveGenerator(20, 200L) { EnemyGenerator(6, 15f) }, 20),
    Tier6Wave3(enemyWaveGenerator(20, 100L) { EnemyGenerator(6, 15f) }, 21),
    Tier6Wave4(enemyWaveGenerator(20, 25L) { EnemyGenerator(6, 15f) }, 22),
    Tier6Wave5(enemyWaveGenerator(40, 50L) { EnemyGenerator(6, 15f) }, 23),
    Tier6Wave6(enemyWaveGenerator(40, 25L) { EnemyGenerator(6, 15f) }, 24),
    Tier6Wave7(enemyWaveGenerator(80, 50L) { EnemyGenerator(6, 15f) }, 25),
    Tier6Wave8(enemyWaveGenerator(80, 10L) { EnemyGenerator(6, 15f) }, 26),
    Tier6Wave9(enemyWaveGenerator(100, 5L) { EnemyGenerator(6, 15f) }, 27);
    
    
    
    
    
    
    
    
    fun getValue(): EnemyWave {
        return this.wave.clone()
    }
    
}