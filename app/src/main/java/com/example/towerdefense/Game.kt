package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.PriorityQueue

/**
 * Game manages the game objects and updates and renders them
 * */
class Game(context: Context) : SurfaceView(context), Serializable, SurfaceHolder.Callback {
    private var gameLoop: GameLoop
    private var gameObjectList = PriorityQueue<GameObject>(compareByDescending { it.z })
    var health = 3
    var money = 100
    var level = 1
    var name : String = "Default Game"
    var fileName : String = "example.txt"


    init {
        holder.addCallback(this)
        gameLoop = GameLoop(this, holder)




        /*var textPaint = Paint()
        var healthPaint = Paint()
        var TEXT_SIZE = 120
        textPaint.setColor(Color.rgb(255, 165, 0))
        textPaint.setTextSize(TEXT_SIZE.toFloat())
        textPaint.setTextAlign(Paint.Align.LEFT)
        //textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.test))
        healthPaint.setColor(Color.rgb(20, 255, 20))*/
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        drawUPS(canvas)
        drawFPS(canvas)

        //addButtonObjectCreator(canvas)


        for (gameObject in gameObjectList) {
            gameObject.draw(canvas)
        }
    }


    //Button to add a new object
    fun addButtonObjectCreator(canvas: Canvas?) {
        val button = Button(context, null)
        button.text = "Generate GameObject"
        button.setOnClickListener {
            val gameObject = GameObject(context, 100.0, 100.0)
            gameObjectList.add(gameObject)
            // Add gameObject to your game scene
        }
        button.draw(canvas)
    }
    fun drawUPS(canvas: Canvas?) {
        val averageUPS = gameLoop.averageUPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.setColor(color)
        paint.setTextSize(50f)
        canvas?.drawText("UPS: $averageUPS", 100f, 100f, paint)
    }

    fun drawFPS(canvas: Canvas?) {
        val averageFPS = gameLoop.averageFPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.setTextSize(50f)
        paint.setColor(color)
        canvas?.drawText("FPS: $averageFPS", 100f, 200f, paint)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        gameLoop.startLoop()

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        return
    }

    var objectMovedThread : Thread? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return true
        for (gameObject in gameObjectList) {
            if(gameObject.onTouchEvent(event)) return true
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            val gameObject = GameObject(context, event.x.toDouble(), event.y.toDouble())
            gameObjectList.add(gameObject)
            return true
        }

        return super.onTouchEvent(event)
    }

    fun saveToBinaryFile(context: Context) {

        var fos: ObjectOutputStream? = null

        try {
            fos = ObjectOutputStream(context.openFileOutput(fileName, Context.MODE_PRIVATE))
            fos.writeObject(this)

            Toast.makeText(context, "Saved game $name", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()

        } finally {
            fos?.close()
        }
    }

    fun update() {

        for (gameObject in gameObjectList) {
            gameObject.update()
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
