package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    lateinit var game: Game

    fun startGame(view: View) {
        val filePath = "example.txt"
        val file = File(filePath)

        println(getDir("", MODE_PRIVATE).absolutePath.toString())

        if (file.exists()) {
            game = Game.fromBinaryFile(filePath, this)!!
            println("Loaded")
        } else {
            game = Game(this)
            println("Created")
        }

        setContentView(GameView(this, game))
    }
}