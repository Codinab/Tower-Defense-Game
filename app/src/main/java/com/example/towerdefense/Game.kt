package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.towerdefense.Utility.MultiVector
import com.example.towerdefense.Utility.Vector2Di
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import kotlin.system.exitProcess

class Game(mainActivity: MainActivity) : Serializable{
    var health = 3
    var money = 100
    var level = 1
    var name : String = "Default Game"

    var fileName : String = "example.txt"

    var Towers : HashMap<Vector2Di, Int> = HashMap()

    init {
        var textPaint = Paint()
        var healthPaint = Paint()

        var TEXT_SIZE = 120
        textPaint.setColor(Color.rgb(255, 165, 0))
        textPaint.setTextSize(TEXT_SIZE.toFloat())
        textPaint.setTextAlign(Paint.Align.LEFT)
        //textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.test))
        healthPaint.setColor(Color.rgb(20, 255, 20))

    }

    fun saveToBinaryFile(context: Context) {

        var fos: ObjectOutputStream? = null

        health = 10
        //TODO: Remove this ^

        try {
            fos = ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            fos.writeObject(this)

            Toast.makeText(context, "Saved game $name", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            println("Error saving file")
            e.printStackTrace()
        } finally {
            fos?.close()
        }
    }
    companion object {
        fun fromBinaryFile(filePath: String, context: Context): Game? {
            var fis: ObjectInputStream? = null
            var game: Game? = null
            try {
                fis = ObjectInputStream(context.openFileInput(filePath))
                game = fis.readObject() as Game
                Toast.makeText(context, "Loaded game ${game.name}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fis?.close()
            }
            return game
        }
    }
}