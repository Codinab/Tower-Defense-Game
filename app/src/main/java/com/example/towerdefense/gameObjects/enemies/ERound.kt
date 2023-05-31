package com.example.towerdefense.gameObjects.enemies

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.towerdefense.utility.Interfaces.Removable
import com.example.towerdefense.utility.TimeController

class ERound() : Removable, java.io.Serializable {
    
    private var waves: ArrayList<Pair<EnemyWave, Int>> = ArrayList()
    var roundStartTime: Long = Long.MAX_VALUE
    private var canStartERounds = false
    
    @RequiresApi(Build.VERSION_CODES.N) fun update() {
        if (TimeController.isPaused() || toDelete() || !canStartERounds) return
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
        
        canStartERounds = true
    }
    
    override var toDelete: Boolean = false
}