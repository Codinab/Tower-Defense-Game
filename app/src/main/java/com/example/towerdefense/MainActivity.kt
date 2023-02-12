package com.example.towerdefense

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.utility.MultiVector
import com.example.towerdefense.utility.Vector2Di
import java.util.concurrent.atomic.AtomicBoolean

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private var doubleBackToExitPressedOnce = AtomicBoolean(false)
    override fun onBackPressed() {
        //Do double back press to exit
        if (doubleBackToExitPressedOnce.get()) {
            super.onBackPressed()
            return
        } else {
            this.doubleBackToExitPressedOnce.set(true)
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Thread(Runnable {
                Thread.sleep(1000)
                doubleBackToExitPressedOnce.set(false)
            }).start()
        }
    }



    fun startGame(view: View) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(Game(this))
    }

    fun continueGame(view: View) {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        val filePath = "example.txt"

        println(getDir("", MODE_PRIVATE).absolutePath.toString())

        var game : Game? = null
        try {
            game = Game.fromBinaryFile(filePath, this)!!
        } catch (e: Exception) {
            println(e.message)
        }

        setContentView(GameView(this, game!!))
    }


}