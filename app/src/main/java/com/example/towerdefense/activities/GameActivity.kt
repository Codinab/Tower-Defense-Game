package com.example.towerdefense.activities

import android.os.Build
import android.os.Bundle
import android.view.Surface
import android.view.Window
import android.view.WindowManager
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.databinding.ActivityGameBinding
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
        
        difficulty = intent.getIntExtra("difficulty", 1)
        enemiesSpeed = intent.getFloatExtra("enemiesSpeed", 0f)
        maxTime = intent.getIntExtra("maxTime", -100)
        gameName = intent.getStringExtra("gameName") ?: "DefaultGame"
        
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
    
    override fun onResume() {
        super.onResume()
    }

    
    private fun resetWindowSizes() {
        screenSize = Vector2i(binding.fragmentContainerViewGame.width, binding.fragmentContainerViewGame.height)
    }
    
    fun getScreenSize(): Vector2i {
        resetWindowSizes()
        return screenSize
    }
    
    private var lastClickTime: Long = 0
    override fun onBackPressed() {
        //Do double back press to exit
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 400) {
            finish()
        } else {
            Toast.makeText(this, "Press 2 times to exit", Toast.LENGTH_SHORT).show()
            lastClickTime = currentTime
        }
    }
}
