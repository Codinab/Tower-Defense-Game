package com.example.towerdefense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.databinding.ActivityMainBinding
import org.joml.Vector2f

class MainActivity : AppCompatActivity() {

    var screenHeight : Int? = null
    var screenWidth : Int? = null

    lateinit var startForResult : ActivityResultLauncher<Intent>
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set orientation to landscape
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        //Hide action bar
        supportActionBar?.hide()

        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        saveWindowSizes()

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        initializeButtons(binding)

        setContentView(view)

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show()
            }
        }

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



    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.R)
    fun createGame() {


        val gameView = GameView(this)
        setContentView(gameView)

        val gameObjectView = GameObjectView(this, gameView, Circle(1f, Vector2f(screenWidth!!.toFloat() -100f, 100f)))
        var bool = true
        gameObjectView.setOnClickListener {
            if (bool) {
                if (!gameView.isRunning()) gameView.start()
                gameView.gamePause()
            } else {
                gameView.gameResume()
            }
            bool = !bool
        }
        gameView.addView(gameObjectView)

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