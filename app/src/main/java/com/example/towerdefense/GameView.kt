package com.example.towerdefense

import GameObjectView
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.Box2DGameObject
import com.example.towerdefense.gameObjects.CircleGameObject
import com.example.towerdefense.gameObjects.GameObjectList
import com.example.towerdefense.gameObjects.Temporary
import org.joml.Vector2f

@Temporary
class GameView(context: Context, surfaceHolder : SurfaceHolder) : ViewGroup(context) , SurfaceHolder.Callback {
    init {
        // Create the SurfaceHolder and add the callback
        surfaceHolder.addCallback(this)
    }
    fun addGameObjectView(gameObjectView: GameObjectView) {
        addView(gameObjectView)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParams
            child.layout(left, top, left + lp.width, top + lp.height)
        }
    }

    var cameraPosition = Vector2f(0f, 0f)
    var previousTouchX = 0f
    var previousTouchY = 0f



    override fun onTouchEvent(event: MotionEvent): Boolean {
        val adjustedPosition = Vector2f(event.x + cameraPosition.x, event.y + cameraPosition.y)
        val movable = gameObjectList.getMovable()
        val clicked = gameObjectList.getClicked(adjustedPosition)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleDownEvent(movable, event, adjustedPosition, clicked)
            }
            MotionEvent.ACTION_MOVE -> {
                handleMoveEvent(movable, event, adjustedPosition, clicked)
            }

            MotionEvent.ACTION_UP -> {
                handleUpEvent(movable, event, adjustedPosition)
            }
        }
        return true
    }

    private fun handleUpEvent(
        movable: GameObjectList,
        event: MotionEvent,
        adjustedPosition: Vector2f
    ) {
        if (movable.isNotEmpty()) movable.onTouchEvent(event, adjustedPosition)
    }

    private fun handleDownEvent(
        movable: GameObjectList,
        event: MotionEvent,
        adjustedPosition: Vector2f,
        clicked: GameObjectList
    ) {
        if (movable.isNotEmpty()) movable.onTouchEvent(event, adjustedPosition)
        else if (clicked.isNotEmpty()) clicked.onTouchEvent(event, adjustedPosition)
        else if (gameObjectCreator.isClicked(adjustedPosition)) {
            val circle = CircleGameObject(
                (10..200).random().toFloat(),
                Rigidbody2D(Vector2f(event.x, event.y)),
                this
            )
            val box = Box2DGameObject(
                Vector2f((10..200).random().toFloat()),
                Rigidbody2D(adjustedPosition),
                this
            )
            gameObjectList.add(listOf(circle, box).random())
            money -= 10
        } else {
            previousTouchX = event.x
            previousTouchY = event.y
        }
    }

    private fun handleMoveEvent(
        movable: GameObjectList,
        event: MotionEvent,
        adjustedPosition: Vector2f,
        clicked: GameObjectList
    ) {
        if (movable.isNotEmpty()) movable.onTouchEvent(event, adjustedPosition)
        else if (clicked.isNotEmpty()) return
        else {
            val deltaX = event.x - previousTouchX
            val deltaY = event.y - previousTouchY
            cameraPosition.x -= deltaX
            cameraPosition.y -= deltaY
            previousTouchX = event.x
            previousTouchY = event.y
        }
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        //TODO("Not yet implemented")
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
     //   TODO("Not yet implemented")
    }


}




/*    private var buttonSize: Int = 0
    private var buttonX: Int = 0
    private var buttonY: Int = 0
    var UPDATE_MILLIS = 30
    var textPaint = Paint()
    var healthPaint = Paint()
    var TEXT_SIZE = 120
    var game = game


    companion object {
        var SCREEN_WIDTH = 0
        var SCREEN_HEIGHT = 0
    }

    var windowManager = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager)

    val SCREEN_WIDTH = windowManager.defaultDisplay.width
    val SCREEN_HEIGHT = windowManager.defaultDisplay.height
    var rectBackGround = Rect(0, 0, SCREEN_HEIGHT, SCREEN_WIDTH)
    //var tower = BitmapFactory.decodeResource(resources, R.drawable.tower) as Bitmap

    var runnable = Runnable {
        run { invalidate() }
    }
    init {
        textPaint.setColor(Color.rgb(255, 165, 0))
        textPaint.setTextSize(TEXT_SIZE.toFloat())
        textPaint.setTextAlign(Paint.Align.LEFT)
        //textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.test))
        healthPaint.setColor(Color.rgb(20, 255, 20))

    }

    var random = Random()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //var health = game.health
        var money = game.money

        //canvas?.drawBitmap(tower, null, rectBackGround, null)
        canvas?.drawText("Money: $money", 0f, 100f, textPaint)
        //canvas?.drawText("Health: $health", 0f, 200f, textPaint)


        canvas?.drawRect(0f, 300f, 100f, 400f, healthPaint)

        // Create a bitmap from the R.drawable.config image
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.config)

        // Set the size of the button to be smaller than the original image
        buttonSize = 100
        val buttonBitmap = Bitmap.createScaledBitmap(bitmap, buttonSize, buttonSize, false)

        // Get the location where the button will be drawn
        buttonX = width - buttonSize - 20
        buttonY = 20

        // Draw the button on the canvas
        canvas?.drawBitmap(buttonBitmap, buttonX.toFloat(), buttonY.toFloat(), null)

        postDelayed(runnable, UPDATE_MILLIS.toLong())
    }

    /*fun handleConfigButtonPress() {
        game.saveToBinaryFile(context)
        //Change view to activity_main
        (context as MainActivity).setContentView(R.layout.activity_main)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Update canva

        if (event?.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            if (x > buttonX && x < buttonX + buttonSize && y > buttonY && y < buttonY + buttonSize) {
                handleConfigButtonPress()
            } else {
                game.health--
                if (game.health <= 0) {
                    game.money *= 2
                    game.saveToBinaryFile(context)
                    var newGame = Game.fromBinaryFile(game.fileName, context)!!
                    var gameView = GameView(context as MainActivity, newGame)
                    (context as MainActivity).setContentView(gameView)
                }
            }
        }
        return true
    }*/*/
