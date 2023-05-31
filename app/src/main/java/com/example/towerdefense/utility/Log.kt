package com.example.towerdefense.utility

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.views.GameSurfaceView
import com.example.towerdefense.views.GameView
import com.example.towerdefense.utility.Interfaces.Positionable
import com.example.towerdefense.utility.Interfaces.Removable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Log(private val context: GameActivity) {

    
    @RequiresApi(Build.VERSION_CODES.O) fun addLog(message: String) {
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedTime = currentTime.format(formatter)
        val logMessage = "[$formattedTime] $message"
        context.addLog(logMessage)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addGameViewLog(gameView: GameView) {
        // Add gameView information
        val message = "GameView created '${gameView.name}' (${gameView.width}x${gameView.height})"
        addLog(message)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addSurfaceViewLog(surfaceView: GameSurfaceView) {
        // Add surfaceView information
        val message = "SurfaceView created (${surfaceView.width}x${surfaceView.height})"
        addLog(message)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addScreenRotatedLog(angle: Float) {
        // Add screen rotated information
        val message = "Screen rotated ($angle)"
        addLog(message)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addPositionableLog(positionable: Positionable) {
        // Add type of object and position added to the canvas
        val message = "$positionable added"
        addLog(message)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addRoundLog(round: Int) {
        // Add round information
        val message = "Round $round started"
        addLog(message)
    }
    
    @RequiresApi(Build.VERSION_CODES.O) fun addRemoveLog(removable: Removable) {
        // Add type of object and position removed from the canvas
        val message = "$removable destroyed"
        addLog(message)
    }
}
