package com.example.towerdefense.gameObjects.enemies

import com.example.towerdefense.utility.Interfaces.Removable
import com.example.towerdefense.utility.TimeController

class ERound() : Removable {
    
    private var waves: ArrayList<Pair<EnemyWave, Int>> = ArrayList()
    var roundStartTime: Long = 10000000L
    
    fun update() {
        if (TimeController.isPaused() || toDelete()) return
        if (roundStartTime == Long.MAX_VALUE) throw Exception("Round start time not set")
        for (wave in waves) {
            wave.first.update()
        }
        waves.removeIf { wave -> wave.first.toDelete() }
        if (waves.isEmpty()) destroy()
    }
    
    fun addWave(wave: EnemyWave, wavePos: Int) {
        waves.add(Pair(wave, wavePos))
        setWaveStartTime(wave, wavePos)
    }
    
    private fun setWaveStartTime(wave: EnemyWave, wavePos: Int) {
        wave.setWaveStartTime(roundStartTime + (wavePos * 4000))
    }
    
    fun roundStartTime(roundStartTime: Long) {
        this.roundStartTime = roundStartTime
        
        for (wave in waves) {
            setWaveStartTime(wave.first, wave.second)
        }
    }
    
    override var toDelete: Boolean = false
}