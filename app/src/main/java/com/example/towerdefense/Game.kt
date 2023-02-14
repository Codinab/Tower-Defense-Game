package com.example.towerdefense

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import org.joml.Vector2f
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.PriorityQueue

/**
 * Game manages the game objects and updates and renders them
 * */
class Game(context: Context) : SurfaceView(context), Serializable, SurfaceHolder.Callback {
    private var gameLoop: GameLoop
    private var roundGameObjectList = PriorityQueue<RoundGameObject>(compareByDescending { it.layerLevel })
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
        if (canvas == null) return
        drawUPS(canvas)
        drawFPS(canvas)
        drawMoney(canvas)

        //addButtonObjectCreator(canvas)

        for (roundGameObject in roundGameObjectList) {
            roundGameObject.draw(canvas)
        }
    }


    //Button to add a new object
    /*fun addButtonObjectCreator(canvas: Canvas?) {
        val button = Button(context, null)
        button.text = "Generate RoundGameObject2D"
        button.setOnClickListener {
            val RoundGameObject2D = RoundGameObject2D(context, 100.0, 100.0, this)
            RoundGameObject2DList.add(RoundGameObject2D)
            // Add RoundGameObject2D to your game scene
        }
        button.draw(canvas)
    } */

    fun drawMoney(canvas: Canvas?) {
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.setColor(color)
        paint.setTextSize(50f)
        canvas?.drawText("Money: $money", 100f, 300f, paint)
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

        for (roundGameObject in roundGameObjectList) {
            if(roundGameObject.onTouchEvent(event)) return true
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            if (money < 10) return true
            for (roundGameObject in roundGameObjectList) {
                if (roundGameObject.movable) {
                    roundGameObject.setPosition(Vector2f(event.x, event.y))
                    return true
                }
            }
            money -= 10
            var roundGameObject = RoundGameObject(40f, Rigidbody2D(Vector2f(event.x, event.y)), this)
            roundGameObjectList.add(roundGameObject)
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
        for (gameObject in roundGameObjectList) {
            if (gameObject.movable) {
                var colliding = false
                for (other in roundGameObjectList) {
                    if (gameObject != other && IntersectionDetector2D.intersection(gameObject, other)) {
                        gameObject.fixable = false
                        colliding = true
                        println("not fixable")
                        break
                    }
                }
                if (!colliding) {
                    gameObject.fixable = true
                    println("fixable")
                }
            }
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
