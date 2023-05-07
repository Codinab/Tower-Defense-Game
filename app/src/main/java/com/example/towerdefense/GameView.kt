package com.example.towerdefense

import Roads
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.*
import android.widget.RelativeLayout
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.utility.*
import org.joml.Vector2f
import org.joml.Vector2i

@SuppressLint("ClickableViewAccessibility")
class GameView(private val context: Context) : RelativeLayout(context), SurfaceHolder.Callback, java.io.Serializable {
    
    private lateinit var pauseStartButton: GameObjectView
    lateinit var surfaceView: GameSurfaceView
    private lateinit var gameLoop: GameLoop
    
    private lateinit var towerMenuView: TowerMenuView
    
    
    init {
        gameView = this
        
        //change to horizontal view
        setBackgroundColor(Color.TRANSPARENT)
        initSurfaceView(context)
        
        
        initTowerMenu(context)
        initStartPauseButton()
        
        hideTowerButtons()
        
        
    }
    
    private val continueTexture =
        BitmapFactory.decodeResource(resources, R.drawable.continue_button)
    private val pauseTexture = BitmapFactory.decodeResource(resources, R.drawable.pause_button)
    private fun initStartPauseButton() {
        
        
        pauseStartButton = GameObjectView(
            context, Box2D(
                Vector2f(250f, 100f), Rigidbody2D(
                    Vector2f(
                        screenSize.x - 250f, 50f
                    )
                )
            )
        )
        
        pauseStartButton.setImageBitmap(continueTexture)
        
        pauseStartButton.setOnClickListener {
            if (TimeController.isPaused()) {
                gameResume()
            } else {
                gamePause()
            }
        }
        addView(pauseStartButton)
    }
    
    private fun updatePosStartPauseButton() {
        pauseStartButton.position(Vector2f(screenSize.x - 250f, 50f))
    }
    
    private fun initTowerMenu(context: Context) {
        towerMenuView = TowerMenuView(context)
        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            screenSize.y / 4
        )
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        towerMenuView.layoutParams = layoutParams
        
        addView(towerMenuView)
    }
    
    private fun initSurfaceView(context: Context) {
        surfaceView = GameSurfaceView(context, Roads.ROAD_5.road)
        surfaceView.holder.addCallback(this)
        surfaceView.visibility = VISIBLE
        surfaceView.isFocusableInTouchMode = true
        surfaceView.setBackgroundColor(Color.WHITE)
        this.addView(surfaceView)
        
        surfaceView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        
        surfaceView.holder.setKeepScreenOn(true)
    }
    
    
    fun showTowerButtons() {
        towerMenuView.showTowerButtons()
        towerMenuView.visibility = VISIBLE
    }
    
    fun hideTowerButtons() {
        towerMenuView.hideTowerButtons()
        towerMenuView.visibility = INVISIBLE
    }
    
    fun update() {
        surfaceView.update()
    }
    
    fun isRunning(): Boolean {
        return ::gameLoop.isInitialized && gameLoop.isRunning
    }
    
    fun stop() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        gameLoop.stopLoop()
        gameLoop.join()
        surfaceView.gameLoop = gameLoop
    }
    
    fun end() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        gameLoop.stopLoop()
        gameLoop.join()
        surfaceView.gameLoop = gameLoop
        /*if (gameHealth.get() <= 0) Toast.makeText(context, "You lost!", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "You won!", Toast.LENGTH_SHORT).show()*/
    }
    
    fun start() {
        if (::gameLoop.isInitialized && gameLoop.isRunning) return
        gameLoop = GameLoop(this)
        gameLoop.startLoop()
        surfaceView.gameLoop = gameLoop
    }
    
    fun gamePause() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        
        pauseStartButton.setImageBitmap(continueTexture)
        pauseStartButton.invalidate()
        
        TimeController.pause()
    }
    
    fun gameResume() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        if (gameHealth.get() <= 0) return
        
        pauseStartButton.setImageBitmap(pauseTexture)
        pauseStartButton.invalidate()
        TimeController.resume()
    }
    
    fun roundEnd() {
        gamePause()
        surfaceView.roundEnd()
    }
    
    fun roundStart() {
        gameResume()
        surfaceView.roundStart()
    }
    
    override fun surfaceCreated(p0: SurfaceHolder) {
        if (restoreGame != null) {
            onRestoreInstanceState(restoreGame)
            restoreGame = null
        }
    }
    
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        //onSaveInstanceState(this)
        if (!isRunning()) {
            start()
            gamePause()
        }
        (context as MainActivity).getRotation()
        updatePosStartPauseButton()
        restoreGame = onSaveInstanceState()
    }
    
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        restoreGame = onSaveInstanceState()
    }
}