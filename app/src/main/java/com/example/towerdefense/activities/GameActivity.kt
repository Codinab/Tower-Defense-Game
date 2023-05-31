package com.example.towerdefense.activities

import android.os.Bundle
import android.view.SurfaceHolder
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.databinding.ActivityGameBinding
import com.example.towerdefense.fragments.GameFragment
import com.example.towerdefense.fragments.LogFragment
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.views.GameView
import org.joml.Vector2i

class GameActivity : AppCompatActivity() {
    
    
    private lateinit var screenSize: Vector2i
    
    
    private var difficulty: Int? = null
    private var enemiesSpeed: Float? = null
    private var maxTime: Int? = null
    private var gameName: String? = null
    
    private lateinit var binding: ActivityGameBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        // Hide action bar
        supportActionBar?.hide()
        // New fullscreen method only for Android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    
        optionsConfig()
    
        binding()
    }
    
    private fun optionsConfig() {
        difficulty = intent.getIntExtra("difficulty", 1)
        enemiesSpeed = intent.getFloatExtra("enemiesSpeed", 0f)
        maxTime = intent.getIntExtra("maxTime", -100)
        gameName = intent.getStringExtra("gameName") ?: "DefaultGame"

        
        binding()
    }
    
    fun addLog(log: String) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewLog) as LogFragment?
        if (fragment?.binding != null) {
            fragment.addLog(log)
        }
    }
    
    
    
    internal fun saveGameView(gameView: GameView) {
        this.gameView = gameView
        gameView.gamePause()
        println("GameView saved")
    }
    internal fun restoreGameView(): GameView? {
        if (gameView != null) {
            println("GameView restored")
            return gameView
        }
        return null
    }
    
    private var gameView: GameView? = null
    
    private fun binding() {
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    fun gameView(): GameView? {
        return gameView
    }
    
    fun getGameName(): String {
        return gameName ?: "DefaultGame"
    }
    fun getDifficulty(): Int {
        return difficulty ?: 1
    }
    fun getEnemiesSpeed(): Float {
        return enemiesSpeed ?: 0f
    }
    fun getMaxTime(): Int {
        return maxTime ?: -100
    }

    
    private fun resetWindowSizes() {
        screenSize = Vector2i(binding.fragmentContainerViewGame.width, binding.fragmentContainerViewGame.height)
    }
    
    fun getScreenSize(): Vector2i {
        resetWindowSizes()
        return screenSize
    }
    
    override fun onBackPressed() {
        //Back button creates bugs
    }
}
