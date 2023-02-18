package com.example.towerdefense

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.towerdefense.Physics2d.JMath
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.utility.Direction2D
import com.example.towerdefense.utility.lastTouchPosition
import org.joml.Vector2f
import java.io.Serializable

/**
 * Game manages the game objects and updates and renders them
 * */
class Game(context: Context) : SurfaceView(context), Serializable, SurfaceHolder.Callback {
    private var gameLoop: GameLoop
    var gameObjectList = GameObjectList()
    var gameObjectListToRemove = GameObjectList()
    var health = 3
    var money = 1000
    var level = 1
    var name: String = "Default Game"
    var fileName: String = "example.txt"
    var gameObjectCreator: GameObject
    var cameraPosition = Vector2f(0f, 0f)

    init {
        holder.addCallback(this)


        context as MainActivity

        val rigidbody =
            Rigidbody2D(0f, Vector2f(context.screenWidth!! / 2f, context.screenHeight!! / 2f))

        @Temporary
        gameObjectCreator = Box2DGameObject(
            Vector2f(100f, 100f),
            rigidbody,
            this
        )
        (gameObjectCreator as Box2DGameObject).creator = true
        //gameObjectCreator.setRotation(Direction2D.DOWN.toAngle())
        //gameObjectCreator.setVelocity(1f)
        gameObjectCreator.setAngularVelocity(2f)


        gameLoop = GameLoop(this, holder)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return

        canvas.translate(-cameraPosition.x, -cameraPosition.y)

        @Temporary
        gameObjectCreator.draw(canvas)

        gameObjectList.draw(canvas)

        drawUPS(canvas)
        drawFPS(canvas)
        drawMoney(canvas)
        drawBuildingsNumber(canvas)
    }

    fun drawMoney(canvas: Canvas?) {
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText("Money: $money", 100f, 300f, paint)
    }

    fun drawBuildingsNumber(canvas: Canvas?) {
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText("Buildings: ${gameObjectList.size}", 100f, 400f, paint)
    }

    fun drawUPS(canvas: Canvas?) {
        val averageUPS = gameLoop.averageUPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText("UPS: $averageUPS", 100f, 100f, paint)
    }

    fun drawFPS(canvas: Canvas?) {
        val averageFPS = gameLoop.averageFPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
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

    override fun performClick(): Boolean {
        return super.performClick()
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        println(event)

        //lastTouchPosition.set(event.x, event.y)

        //if (gameObjectList.onTouchEvent(event)) return true


        //if (event.action == MotionEvent.ACTION_DOWN && onTouchEvent_ACTION_DOWN(event)) return true

        //onTouchEvent_ACTION_MOVE(event)
        println(Vector2f(event.x, event.y))

        println("Game: onTouchEvent2")

        return super.onTouchEvent(event)
    }

    fun onTouchEvent_ACTION_MOVE(event: MotionEvent): Boolean {
        println(cameraPosition)
        cameraPosition.x = -event.x + (context as MainActivity).screenWidth!! / 2f
        cameraPosition.y = -event.y + (context as MainActivity).screenHeight!! / 2f
        return true
    }

    private fun onTouchEvent_ACTION_DOWN(event: MotionEvent): Boolean {
        if (IntersectionDetector2D.intersection(gameObjectCreator, Vector2f(event.x, event.y))) {
            //Generate random radius between 10 and 100
            val circle = CircleGameObject(
                (10..200).random().toFloat(),
                Rigidbody2D(Vector2f(event.x, event.y)),
                this
            )
            val box = Box2DGameObject(
                Vector2f((10..200).random().toFloat()),
                Rigidbody2D(Vector2f(event.x, event.y)),
                this
            )
            gameObjectList.add(listOf(circle, box).random())
            money -= 10
            return true
        }
        return false
    }

    /*fun saveToBinaryFile(context: Context) {

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
    }*/

    fun update() {

        @Temporary
        gameObjectCreator.update()

        for (gameObject in gameObjectList) {
            if (gameObject.movable.get()) {
                var colliding = false
                for (other in gameObjectList) {
                    if (gameObject != other &&
                            IntersectionDetector2D.intersection(gameObject, other)) {
                        gameObject.fixable.set(false)
                        colliding = true
                        break
                    }
                }
                if (!colliding) {
                    gameObject.fixable.set(true)
                }
            }
        }

        for (gameObject in gameObjectListToRemove) {
            gameObjectList.remove(gameObject)
        }
        gameObjectListToRemove.clear()
    }


    companion object {
        /*fun fromBinaryFile(filePath: String, context: Context): Game? {
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
        }*/
    }
}
