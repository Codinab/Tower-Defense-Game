package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.towerdefense.Utility.MultiVector
import com.example.towerdefense.Utility.Vector2Di
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
        } else {
            game = Game(this)
        }

        var multiVector = MultiVector(Vector2Di())
        multiVector.add(Vector2Di(1, 0))
        multiVector.add(Vector2Di(0, 1))
        println(multiVector.toString())

        setContentView(GameView(this, game))
    }
}