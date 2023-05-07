package com.example.towerdefense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.databinding.ActivityMainBinding
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.screenRotation
import com.example.towerdefense.utility.screenSize
import org.joml.Vector2i


class MainActivity : AppCompatActivity() {
    
    lateinit var startForResult: ActivityResultLauncher<Intent>
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Set orientation to landscape
        /*requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;*/
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        getRotation()
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        
        
        initializeButtons(binding)
        
        setContentView(view)
        
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show()
                }
            }
        
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
    
    fun getRotation() {
        // Get the window manager
        val windowManager = windowManager
        
        
        // Get the rotation of the window
        val rotation = windowManager.defaultDisplay.rotation
        
        saveWindowSizes()
        
        // Update screenSize depending on rotation
        when (rotation) {
            Surface.ROTATION_0 -> {
                screenRotation = 0f
            }
            Surface.ROTATION_180 -> {
                // Reversed portrait
                screenRotation = 180f
            }
            Surface.ROTATION_90 -> {
                // Landscape
                screenRotation = 90f
            }
            Surface.ROTATION_270 -> {
                // Reversed landscape
                screenRotation = 270f
            }
            else -> {
                screenRotation = 0f
            }
        }
        
        println(screenRotation)
    }
    
    fun saveWindowSizes() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        
        screenSize = Vector2i(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons(binding: ActivityMainBinding) {
        val continueButton = binding.createButton
        continueButton.setOnClickListener {
            createGame()
        }
        val createButton = binding.continueGame
        createButton.setOnClickListener {
            continueGame()
        }
    }
    
    private var lastClickTime: Long = 0
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBackPressed() {
        //Do double back press to exit
        val currentTime = System.currentTimeMillis()
        createGame()
        if (currentTime - lastClickTime < 400) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Press 2 times to exit", Toast.LENGTH_SHORT).show()
            lastClickTime = currentTime
        }
    }
    
    
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    fun createGame() {
        
        val view = GameView(this)
        setContentView(view)
        gameView = view
        
    }
    
    fun continueGame() {
        Toast.makeText(baseContext, getString(R.string.textoToast), Toast.LENGTH_SHORT).show()
        
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        /*
        val filePath = "example.txt"

        try {
            game = Game.fromBinaryFile(filePath, this)
        } catch (e: Exception) {
            println(e.message)
        }

        //If game is null initialize a new game
        if (game == null) game = Game(this)
        setContentView(game) */
    }
    
    
}