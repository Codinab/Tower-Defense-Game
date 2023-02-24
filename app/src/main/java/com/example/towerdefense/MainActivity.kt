package com.example.towerdefense

import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.databinding.ActivityMainBinding

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
            createGame(it)
        }
        val createButton = binding.continueGame
        createButton.setOnClickListener {
            continueGame(it)
        }
    }

    private var lastClickTime: Long = 0
    override fun onBackPressed() {
        //Do double back press to exit
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 400) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Press 2 times to exit", Toast.LENGTH_SHORT).show()
            lastClickTime = currentTime
        }
    }



    @RequiresApi(Build.VERSION_CODES.R)
    fun createGame(view: View) {
        val game = Game(this)
        setContentView(game)
    }

    fun continueGame(view: View) {
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