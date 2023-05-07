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
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 1)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 3)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 4)
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 5)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 6)
        it.addWave(EnemyWaves.Tier2Wave3.getValue(), 7)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 8)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 9)
        it.addWave(EnemyWaves.Tier4Wave1.getValue(), 9)
        it.addWave(EnemyWaves.Tier2Wave3.getValue(), 10)
        it.addWave(EnemyWaves.Tier2Wave2.getValue(), 11)
        it.addWave(EnemyWaves.Tier2Wave1.getValue(), 12)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 13)
    }),
    ROUND_7(ERound().also {
        it.addWave(EnemyWaves.Tier3Wave2.getValue(), 0)
        it.addWave(EnemyWaves.Tier2Wave3.getValue(), 1)
        it.addWave(EnemyWaves.Tier3Wave2.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave3.getValue(), 3)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 4)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 5)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 6)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 7)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 8)
        it.addWave(EnemyWaves.Tier2Wave5.getValue(), 9)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 10)
        it.addWave(EnemyWaves.Tier4Wave4.getValue(), 11)
    }),
    
    ROUND_8(ERound().also {
        it.addWave(EnemyWaves.Tier3Wave1.getValue(), 0)
        it.addWave(EnemyWaves.Tier2Wave3.getValue(), 1)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 2)
        it.addWave(EnemyWaves.Tier2Wave4.getValue(), 3)
        it.addWave(EnemyWaves.Tier3Wave1.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 5)
        it.addWave(EnemyWaves.Tier2Wave5.getValue(), 6)
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 7)
        it.addWave(EnemyWaves.Tier3Wave5.getValue(), 8)
        it.addWave(EnemyWaves.Tier2Wave6.getValue(), 9)
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 10)
        it.addWave(EnemyWaves.Tier3Wave5.getValue(), 11)
    }),
    
    ROUND_9(ERound().also {
        it.addWave(EnemyWaves.Tier5Wave1.getValue(), 0)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 1)
        it.addWave(EnemyWaves.Tier4Wave1.getValue(), 2)
        it.addWave(EnemyWaves.Tier3Wave5.getValue(), 3)
        it.addWave(EnemyWaves.Tier5Wave1.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave5.getValue(), 5)
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 6)
        it.addWave(EnemyWaves.Tier3Wave6.getValue(), 7)
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 8)
        it.addWave(EnemyWaves.Tier2Wave6.getValue(), 9)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 10)
        it.addWave(EnemyWaves.Tier5Wave2.getValue(), 11)
    }),
    ROUND_10(ERound().also {
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 2)
        it.addWave(EnemyWaves.Tier5Wave1.getValue(), 3)
        it.addWave(EnemyWaves.Tier4Wave2.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave3.getValue(), 5)
        it.addWave(EnemyWaves.Tier6Wave1.getValue(), 6)
        it.addWave(EnemyWaves.Tier5Wave2.getValue(), 7)
        it.addWave(EnemyWaves.Tier2Wave9.getValue(), 8)
        it.addWave(EnemyWaves.Tier4Wave3.getValue(), 8)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 9)
        it.addWave(EnemyWaves.Tier6Wave2.getValue(), 10)
        it.addWave(EnemyWaves.Tier5Wave3.getValue(), 11)
    }),
    
    ROUND_11(ERound().also {
        it.addWave(EnemyWaves.Tier4Wave3.getValue(), 0)
        it.addWave(EnemyWaves.Tier3Wave4.getValue(), 1)
        it.addWave(EnemyWaves.Tier6Wave2.getValue(), 2)
        it.addWave(EnemyWaves.Tier5Wave3.getValue(), 3)
        it.addWave(EnemyWaves.Tier4Wave4.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave5.getValue(), 5)
        it.addWave(EnemyWaves.Tier6Wave3.getValue(), 6)
        it.addWave(EnemyWaves.Tier5Wave4.getValue(), 7)
        it.addWave(EnemyWaves.Tier4Wave5.getValue(), 8)
        it.addWave(EnemyWaves.Tier3Wave6.getValue(), 9)
        it.addWave(EnemyWaves.Tier6Wave4.getValue(), 10)
        it.addWave(EnemyWaves.Tier3Wave10.getValue(), 11)
        it.addWave(EnemyWaves.Tier5Wave5.getValue(), 11)
    }),
    
    ROUND_12(ERound().also {
        it.addWave(EnemyWaves.Tier4Wave5.getValue(), 0)
        it.addWave(EnemyWaves.Tier3Wave6.getValue(), 1)
        it.addWave(EnemyWaves.Tier6Wave4.getValue(), 2)
        it.addWave(EnemyWaves.Tier5Wave5.getValue(), 3)
        it.addWave(EnemyWaves.Tier3Wave10.getValue(), 3)
        it.addWave(EnemyWaves.Tier4Wave6.getValue(), 4)
        it.addWave(EnemyWaves.Tier3Wave7.getValue(), 5)
        it.addWave(EnemyWaves.Tier6Wave5.getValue(), 6)
        it.addWave(EnemyWaves.Tier5Wave6.getValue(), 7)
        it.addWave(EnemyWaves.Tier4Wave7.getValue(), 8)
        it.addWave(EnemyWaves.Tier3Wave8.getValue(), 9)
        it.addWave(EnemyWaves.Tier6Wave6.getValue(), 10)
        it.addWave(EnemyWaves.Tier5Wave7.getValue(), 11)
    }),
    
    ROUND_13(ERound().also {
        it.addWave(EnemyWaves.Tier4Wave7.getValue(), 0)
        it.addWave(EnemyWaves.Tier3Wave8.getValue(), 1)
        it.addWave(EnemyWaves.Tier6Wave6.getValue(), 2)
        it.addWave(EnemyWaves.Tier5Wave7.getValue(), 3)
        it.addWave(EnemyWaves.Tier5Wave8.getValue(), 4)
        it.addWave(EnemyWaves.Tier6Wave7.getValue(), 5)
        it.addWave(EnemyWaves.Tier6Wave8.getValue(), 6)
        it.addWave(EnemyWaves.Tier1Wave9.getValue(), 7)
        it.addWave(EnemyWaves.Tier6Wave9.getValue(), 7)
    })
    
}