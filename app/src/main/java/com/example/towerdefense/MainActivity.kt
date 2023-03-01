package com.example.towerdefense

import GameObjectView
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.databinding.ActivityMainBinding
import org.joml.Vector2f

class MainActivity : AppCompatActivity() {

    var screenHeight : Int? = null
    var screenWidth : Int? = null
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        saveWindowSizes()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        initializeButtons(binding)

        setContentView(view)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun saveWindowSizes() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
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



    @RequiresApi(Build.VERSION_CODES.R)
    fun createGame() {
        val game = GameView(this, SurfaceView(this).holder)
        game.addGameObjectView(GameObjectView(this, game, Circle(100f, Rigidbody2D(Vector2f(100f, 100f)))))
        setContentView(game)
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