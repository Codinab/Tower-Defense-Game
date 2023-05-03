package com.example.towerdefense.gameObjects.enemies

enum class ERounds(val eRound: ERound) {
    ROUND_1(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 0)
    }),
    ROUND_2(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 1)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 2)
    }),
    ROUND_3(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 1)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 3)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 4)
    }),
    ROUND_4(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 1)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 3)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 3)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 4)
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 5)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 6)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 7)
    }),
    ROUND_5(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 1)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 3)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 3)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 4)
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 5)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 5)
        it.addWave(EnemyWaves.Tier1Wave5.getValue(), 6)

    }),
    ROUND_6(ERound().also {
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 1)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier1Wave4.getValue(), 3)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 4)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 5)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 6)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 7)
        it.addWave(EnemyWaves.Tier1Wave4.getValue(), 8)
        it.addWave(EnemyWaves.Tier1Wave4.getValue(), 9)
        it.addWave(EnemyWaves.Tier1Wave3.getValue(), 10)
        it.addWave(EnemyWaves.Tier1Wave2.getValue(), 11)
        it.addWave(EnemyWaves.Tier1Wave1.getValue(), 12)
        it.addWave(EnemyWaves.Tier1Wave4.getValue(), 13)
    }),
}