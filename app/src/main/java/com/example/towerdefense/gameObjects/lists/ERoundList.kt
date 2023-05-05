package com.example.towerdefense.gameObjects.lists

import android.os.Looper
import android.widget.Toast
import com.example.towerdefense.gameObjects.enemies.ERound
import com.example.towerdefense.gameObjects.enemies.ERounds
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.money
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class ERoundList(private val rounds: Vector<ERound> = Vector()) :
    MutableList<ERound> by rounds {
    
    private var round = 1
    private var roundStart = true
    
    init {
        ERounds.values().forEach {
            add(it.eRound)
        }
    }
    
    fun update() {
        if (TimeController.isPaused()) return
        if(roundStart) {
            gameView!!.surfaceView.roundStart()
            roundStart = false
        }
    
        if (rounds.isEmpty() && gameView!!.surfaceView.enemies.isEmpty()) {
            gameView!!.end()
        }
        if (rounds.isEmpty()) return
        
        rounds.first().update()
        
        if (rounds.first().toDelete() && gameView!!.surfaceView.enemies.isEmpty()) {
            rounds.removeAt(0)
            round++
            money.addAndGet(round * 100)
            println("Round $round")
            gameView!!.roundEnd()
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