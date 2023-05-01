package com.example.towerdefense.gameObjects.lists

import android.os.Looper
import android.widget.Toast
import com.example.towerdefense.gameObjects.enemies.ERound
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import java.util.concurrent.CopyOnWriteArrayList

class ERoundList(private val rounds: CopyOnWriteArrayList<ERound> = CopyOnWriteArrayList()) :
    MutableList<ERound> by rounds {
    
    private var round = 1
    private var roundStart = true
    
    fun update() {
        if (TimeController.isPaused()) return
        if(roundStart) {
            gameView!!.surfaceView.roundStart()
            roundStart = false
        }
        this.rounds.forEach {
            it.update()
        }
    
        if (rounds.isEmpty() && gameView!!.surfaceView.enemies.isEmpty()) {
            gameView!!.stop()
        }
        if (rounds.isEmpty()) return
        
        
        if (rounds.first().toDelete()) {
            rounds.removeAt(0)
            round++
            if (rounds.isNotEmpty()) first().roundStartTime(TimeController.getGameTime() + 1000)
        }
        
    }
    
    override fun add(element: ERound): Boolean {
        if(isEmpty()) element.roundStartTime(TimeController.getGameTime())
        return rounds.add(element)
    }
    
    fun getRound(): Int {
        return round
    }
}