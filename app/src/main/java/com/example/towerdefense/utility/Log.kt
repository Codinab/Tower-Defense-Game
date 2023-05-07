package com.example.towerdefense.utility

import com.example.towerdefense.GameSurfaceView
import com.example.towerdefense.GameView
import com.example.towerdefense.utility.Interfaces.Movable
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.Interfaces.Removable
import org.joml.Vector2i
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Log {
    private val log: MutableList<String> = mutableListOf()
    
    fun addLog(message: String) {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedTime = currentTime.format(formatter)
        val logMessage = "[$formattedTime] $message"
        log.add(logMessage)
        println(logMessage)
    }
    
    fun addGameViewLog(gameView: GameView) {
        // Add gameView information
        val message = "GameView created '${gameView.name}' (${gameView.width}x${gameView.height})"
        addLog(message)
    }
    
    fun addSurfaceViewLog(surfaceView: GameSurfaceView) {
        // Add surfaceView information
        val message = "SurfaceView created (${surfaceView.width}x${surfaceView.height})"
        addLog(message)
    }
    
    fun addScreenRotatedLog(angle: Float) {
        // Add screen rotated information
        val message = "Screen rotated ($angle)"
        addLog(message)
    }
    
    fun addPositionableLog(positionable: Positionable) {
        // Add type of object and position added to the canvas
        val message = "$positionable added"
        addLog(message)
    }
    
    fun addRoundLog(round: Int) {
        // Add round information
        val message = "Round $round started"
        addLog(message)
    }
    
    fun addRemoveLog(removable: Removable) {
        // Add type of object and position removed from the canvas
        val message = "$removable destroyed"
        addLog(message)
    }
}
