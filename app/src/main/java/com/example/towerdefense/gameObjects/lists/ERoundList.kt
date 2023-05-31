package com.example.towerdefense.gameObjects.lists

import android.os.Build
import android.os.Looper
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.gameObjects.enemies.ERound
import com.example.towerdefense.gameObjects.enemies.ERoundBuilder
import com.example.towerdefense.gameObjects.enemies.ERounds
import com.example.towerdefense.utility.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

class ERoundList(private val rounds: Vector<ERound> = Vector(), private val context: GameActivity) :
    MutableList<ERound> by rounds {
    
    
    private var roundStart = true
    
    init {
        ERoundBuilder(context).buildRoundList().forEach { add(it) }
        gameLog?.addRoundLog(1)
    }
    
    @RequiresApi(Build.VERSION_CODES.N) fun update() {
        if (TimeController.isPaused()) return
        if(roundStart) {
            context.gameView()!!.surfaceView.roundStart()
            roundStart = false
        }
        
        if (rounds.isEmpty() && context.gameView()!!.surfaceView.enemies.isEmpty()) {
            context.gameView()!!.end()
        }
        if (rounds.isEmpty()) return
        
        rounds.first().update()
        
        if (rounds.first().toDelete() && context.gameView()!!.surfaceView.enemies.isEmpty()) {
            rounds.removeAt(0)
            context.gameView()!!.round++
            context.gameView()!!.money.addAndGet(context.gameView()!!.round * 100)
            gameLog?.addRoundLog(context.gameView()!!.round)
            context.gameView()!!.roundEnd()
            if (rounds.isNotEmpty()) first().roundStartTime(TimeController.getGameTime() + 1000)
        }
        
    }
    
    override fun add(element: ERound): Boolean {
        if(isEmpty()) element.roundStartTime(TimeController.getGameTime())
        return rounds.add(element)
    }
    
    fun getRound(): Int {
        return context.gameView()!!.round
    }
    
    fun getRoundStart(): Boolean {
        return roundStart
    }
}