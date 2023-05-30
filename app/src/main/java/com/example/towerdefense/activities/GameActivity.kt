package com.example.towerdefense.activities

import android.os.Bundle
import android.view.Surface
import android.view.Window
import android.view.WindowManager
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.views.GameView
import org.joml.Vector2i

class GameActivity : AppCompatActivity() {
    
    private lateinit var gameView: GameView
    
    private lateinit var screenSize: Vector2i
    
    private var screenRotation: Float = 0f
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        // Hide action bar
        supportActionBar?.hide()
        
        // New fullscreen method only for Android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        val difficulty = intent.getIntExtra("difficulty", 1)
        val enemiesSpeed = intent.getFloatExtra("enemiesSpeed", 0f)
        val maxTime = intent.getIntExtra("maxTime", -100)
        val gameName = intent.getStringExtra("gameName") ?: "DefaultGame"
        
        gameView = GameView(this, gameName)//, difficulty, enemiesSpeed, maxTime)
        setContentView(gameView)
    }
    
    override fun onResume() {
        super.onResume()
    }

    
    private fun resetWindowSizes() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenSize = Vector2i(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
    
    fun getScreenSize(): Vector2i {
        resetWindowSizes()
        return screenSize
    }
}
