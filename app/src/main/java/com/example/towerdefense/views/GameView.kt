package com.example.towerdefense.views

import Roads
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.towerdefense.GameLoop
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.activities.LogActivity
import com.example.towerdefense.utility.*
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger

@SuppressLint("ClickableViewAccessibility", "ViewConstructor")
class GameView(private val context: GameActivity, val name: String = "DefaultGame", val difficulty: Int, val enemySpeed: Float, val maxTime: Int) : RelativeLayout(context), SurfaceHolder.Callback, java.io.Serializable {
    
    private lateinit var pauseStartButton: GameObjectView
    lateinit var surfaceView: GameSurfaceView
    private lateinit var gameLoop: GameLoop
    private lateinit var towerMenuView: TowerMenuView
    
    init {
        gameView = this
        gameLog = Log()
        gameLog?.addGameViewLog(this)
        
        //change to horizontal view
        setBackgroundColor(Color.TRANSPARENT)
        initSurfaceView(context)
        
        val size = context.getScreenSize()
        initViews(context, size)
        hideTowerButtons()
        
        money = AtomicInteger(1000)
        gameHealth = AtomicInteger(1)
        round = 1
        
        invalidate()
        requestLayout()
        
    }
    
    var roundCounter: TextView? = null
    private fun initRoundCounter() {
        roundCounter = TextView(context).apply {
            text = "$round"
            textSize = 30f
            setTextColor(Color.BLACK)
            x = pauseStartButton.x - 100
            y = pauseStartButton.y
        }
        addView(roundCounter)
    }
    
    private fun updateRoundCounter() {
        post {
            roundCounter?.x = pauseStartButton.x - 100
            roundCounter?.y = pauseStartButton.y
            roundCounter?.text = "$round"
        }
    }
    
    
    val continueTexture =
        BitmapFactory.decodeResource(resources, R.drawable.continue_button)
    private val pauseTexture = BitmapFactory.decodeResource(resources, R.drawable.pause_button)
    private fun initStartPauseButton(size: Vector2i) {
        pauseStartButton = GameObjectView(
            context, Box2D(
                Vector2f(250f, 100f), Rigidbody2D(
                    Vector2f(
                        size.x - 250f,
                        50f
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
    
    private fun initTowerMenu(context: GameActivity, size: Vector2i) {
        towerMenuView = TowerMenuView(context)
        val layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            size.y / 4
        )
        layoutParams.addRule(ALIGN_PARENT_BOTTOM)
        towerMenuView.layoutParams = layoutParams
        
        addView(towerMenuView)
    }
    
    private fun initSurfaceView(context: GameActivity) {
        if (selectedMap == null) selectedMap = Roads.values().random().road
        surfaceView = GameSurfaceView(context, selectedMap!!)
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
        updateRoundCounter()
        towerMenuView.updateCostTexts()
        if ((gameHealth.get() <= 0) && !end) {
            gameHealth.set(0)
            end()
        }
        if (TimeController.timeLeft() <= 0 && !end && TimeController.timeLeft() != -100L) {
            gameHealth.set(0)
            end()
        }
    }
    
    fun isRunning(): Boolean {
        return ::gameLoop.isInitialized && gameLoop.isRunning
    }
    
    var end = false
    fun end() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        
        gamePause()
        end = true
        
        val result = if (gameHealth.get() > 0) "won" else "lost"
        
        
        val intent = Intent(context, LogActivity::class.java)
        val money = money.get().toString()
        val round = round.toString()
        
        
        intent.putExtra("log_name", name)
        intent.putExtra("log_money", money)
        intent.putExtra("log_round", round)
        intent.putExtra("log_result", result)
        context.startActivity(intent)
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
        
        post {
            pauseStartButton.setImageBitmap(continueTexture)
            pauseStartButton.invalidate()
        }
        
        TimeController.pause()
    }
    
    fun gameResume() {
        if (!::gameLoop.isInitialized) return
        if (!gameLoop.isRunning) return
        if (gameHealth.get() <= 0) return
        if (end) return
        
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
    
    /*    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            //onSaveInstanceState(this)
            if (!isRunning()) {
                start()
                gamePause()
            }
        
            //(context as OptionActivity).rotation()
            updatePosStartPauseButton()
            updateRoundCounter()
            restoreGame = onSaveInstanceState()
        }*/
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        if (!isRunning()) {
            start()
            gamePause()
        }
    
        
        val size = Vector2i(p2, p3)
   
        updateViews(size)
        if (towerClicked == null) {
            towerMenuView.visibility = INVISIBLE
        }
        restoreGame = onSaveInstanceState()
    }
    
    private fun initViews(context: GameActivity, size: Vector2i) {
        initSurfaceView(context)
        initTowerMenu(context, size)
        initStartPauseButton(size)
        initRoundCounter()
    }
    private fun updateViews(size: Vector2i) {
        updateTowerMenu(size)
        updatePauseStartButton(size)
        updateRoundCounter()
    }
    
    private fun updateTowerMenu(size: Vector2i) {
        // remove and re-add the tower menu to update its position
        removeView(towerMenuView)
        initTowerMenu(context, size)
    }
    
    private fun updatePauseStartButton(size: Vector2i) {
        // remove and re-add the start/pause button to update its position
        removeView(pauseStartButton)
        initStartPauseButton(size)
    }
    
    
    
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        restoreGame = onSaveInstanceState()
    }
}