package com.example.towerdefense

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.view.get
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.DrawableObject
import com.example.towerdefense.gameObjects.Temporary
import com.example.towerdefense.utility.Direction2D
import org.joml.Vector2f
import java.lang.Integer.max

@SuppressLint("ClickableViewAccessibility")
class GameView(context: Context) : RelativeLayout(context), SurfaceHolder.Callback {

    lateinit var surfaceView: GameSurfaceView
    private lateinit var gameLoop: GameLoop
    private var movableObjects = ArrayList<Collider2D>()

    init {
        setBackgroundColor(Color.TRANSPARENT)
        initSurfaceView(context)

        val circle = Circle(100f, Rigidbody2D(Vector2f(100f, 100f)))
        circle.body.velocity = 10f
        circle.body.rotation = Direction2D.DOWN.toAngle()
        movableObjects.add(circle)
        surfaceView.toDraw.add(DrawableObject(circle))
    }

    private fun initSurfaceView(context: Context) {
        surfaceView = GameSurfaceView(context)
        surfaceView.holder.addCallback(this)
        surfaceView.visibility = VISIBLE
        surfaceView.isFocusableInTouchMode = true
        surfaceView.setBackgroundColor(Color.GREEN)
        this.addView(surfaceView)

        surfaceView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        surfaceView.holder.setKeepScreenOn(true)
    }

    var cameraPosition = Vector2f(0f, 0f)
    var previousTouchX = 0f
    var previousTouchY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //Check if the child view is clicked
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                previousTouchX = event.x
                previousTouchY = event.y
                println("ACTION_DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                val dx = x - previousTouchX
                val dy = y - previousTouchY
                cameraPosition.x -= dx
                cameraPosition.y -= dy
                previousTouchX = x
                previousTouchY = y
                println("ACTION_MOVE")
            }
            MotionEvent.ACTION_UP -> {
                println("ACTION_UP")
            }
        }
        return true
    }


    fun update() {
        movableObjects.forEach { it.update() }
    }

    fun isRunning(): Boolean {
        return ::gameLoop.isInitialized && gameLoop.isRunning
    }

    fun stop() {
        if (!::gameLoop.isInitialized) return
        gameLoop.setRunning(false)
        gameLoop.join()
    }

    fun start() {
        if (::gameLoop.isInitialized && gameLoop.isRunning) return
        gameLoop = GameLoop(this)
        gameLoop.startLoop()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        stop()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        stop()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        stop()
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
/*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    // Measure all child views, accounting for padding and margin
    measureChildren(widthMeasureSpec, heightMeasureSpec)
    // Compute total width and height of this view group
    var width = 0
    var height = 0
    for (i in 0 until childCount) {
        val child = getChildAt(i)
        if (child is SurfaceView) continue
        val lp = child.layoutParams
        if (lp is MarginLayoutParams) {
            width = max(width, child.measuredWidth + lp.leftMargin + lp.rightMargin)
            height += child.measuredHeight + lp.topMargin + lp.bottomMargin
        } else {
            width = max(width, child.measuredWidth)
            height += child.measuredHeight
        }
    }
    width += paddingLeft + paddingRight
    height += paddingTop + paddingBottom

    // Use the computed width and height to set the measured dimensions of this view group
    setMeasuredDimension(
        resolveSize(width, widthMeasureSpec),
        resolveSize(height, heightMeasureSpec)
    )
}*/
/*
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // Layout all child views within this view group, accounting for padding and margin
        val childLeft = paddingLeft
        var childTop = paddingTop
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is SurfaceView) continue
            val lp = child.layoutParams as LinearLayout.LayoutParams
            child.layout(
                childLeft + lp.leftMargin,
                childTop + lp.topMargin,
                childLeft + lp.leftMargin + child.measuredWidth,
                childTop + lp.topMargin + child.measuredHeight
            )
            childTop += child.measuredHeight + lp.topMargin + lp.bottomMargin
        }
        surfaceView.setBackgroundColor(Color.BLUE)
    }
*/