package com.example.towerdefense

import GameObjectView
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.databinding.ActivityMainBinding
import org.joml.Vector2f

class MainActivity : AppCompatActivity() {

    var screenHeight : Int? = null
    var screenWidth : Int? = null

    lateinit var startForResult : ActivityResultLauncher<Intent>
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



    @RequiresApi(Build.VERSION_CODES.R)
    fun createGame() {
        val surfaceView = SurfaceView(this)

        surfaceView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                Toast.makeText(this@MainActivity, "SurfaceView touched", Toast.LENGTH_SHORT).show()
                return true
            }
        })

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val gameView = GameView(this)
        val button = Button(this)
        button.text = "Click me"
        button.layoutParams = layoutParams
        gameView.addView(surfaceView)
        button.setOnClickListener {
            Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
        }
        gameView.addView(button)

        setContentView(gameView)
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