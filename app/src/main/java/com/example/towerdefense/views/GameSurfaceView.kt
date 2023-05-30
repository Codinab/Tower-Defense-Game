package com.example.towerdefense.views

import com.example.towerdefense.gameObjects.lists.TowerList
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.towerdefense.GameLoop
import com.example.towerdefense.R
import com.example.towerdefense.gameObjects.*
import com.example.towerdefense.gameObjects.Camera
import com.example.towerdefense.gameObjects.lists.ERoundList
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.gameObjects.lists.ProjectileList
import com.example.towerdefense.gameObjects.tower.*
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.TimeController.*
import com.example.towerdefense.utility.TimeController.Companion.timeLeft
import com.example.towerdefense.utility.textures.BackgroundGenerator
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

@SuppressLint("ViewConstructor")
class GameSurfaceView(private val context: Context, road: Road) : SurfaceView(context), java.io.Serializable {
    
    var enemies = EnemyList()
    var movableTower: Tower? = null
    var towers = TowerList()
    var towerSpawners = Vector<TowerSpawner>()
    var projectiles = ProjectileList()
    var rounds = ERoundList()
    
    var gameLoop: GameLoop? = null
    var isRunning = false
    internal var road: Road
    private val backgroundGenerator: BackgroundGenerator
    private val backgroundBitmaps: HashMap<Vector2f, Bitmap> = HashMap()
    
    init {
        
        this.road = road
        gameLog?.addSurfaceViewLog(this)
        
        backgroundGenerator = BackgroundGenerator(context)
        road.getPositions().forEach {
            backgroundBitmaps[it] = backgroundGenerator.generateBackground(it, 7)
        }
        setZOrderOnTop(false)
        setZOrderMediaOverlay(false)
        
        TimeController.initCount()
    }
    
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        
        camera.updateCanvas(canvas)
        
        
        drawBackGround(canvas)
        
        
        //Draw the road
        road.draw(canvas)
        
        enemies.draw(canvas)
        towers.draw(canvas)
        projectiles.draw(canvas)
        
        towerSpawners.forEach { it.draw(canvas) }
        
        if (rounds.getRoundStart()) Drawing.drawBitmap(canvas, newRound, Vector2f(cameraPosition).add(screenSize.x.toFloat() / 2f, newRound.height.toFloat() / 2f))
        
        if (movableTower != null) movableTower!!.draw(canvas)
        
        if (gameView!!.end) printEnd(canvas)
        
        if (fps) drawUPS(canvas)
        if (fps) drawFPS(canvas)
        
        drawMoney(canvas)
        drawGameHealth(canvas)
        drawGameTime(canvas)
        
    }
    
    private fun printEnd(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        paint.textSize = 100f
        if (gameHealth.get() > 0) Drawing.drawText(canvas, "You Won!", Vector2f(cameraPosition).add(screenSize.x.toFloat() / 2f, screenSize.y.toFloat() / 2f), paint)
        else Drawing.drawText(canvas, "You Lost!", Vector2f(cameraPosition).add(screenSize.x.toFloat() / 2f, screenSize.y.toFloat() / 2f), paint)
    }
    
    
    private fun drawBackGround(canvas: Canvas) {
        backgroundBitmaps.forEach {
            Drawing.drawBitmap(canvas, it.value, it.key)
        }
    }
    
    private val camera = Camera()
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val adjustedPosition = camera.adjustedPosition(event)
        val pos = Vector2f(event.x, event.y).add(cameraPosition)
        
        
        if (!isRunning) return false
        
        if (movableTower != null) {
            if (movableTower!!.onTouchEvent(event, adjustedPosition)) return true
        }
        
        
        towers.forEach() {
            if (it.isClicked(adjustedPosition) || it.movable.get()) if (it.onTouchEvent(
                    event, adjustedPosition
                )
            ) return true
        }
        
        towerSpawners.forEach {if (it.isClicked(pos)) if (it.onTouchEvent(event, pos)) return true}
        
        //Check if the child view is clicked
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                camera.touch(event)
            }
            MotionEvent.ACTION_MOVE -> {
                if (camera.moving) camera.update(event)
            }
            MotionEvent.ACTION_UP -> {
                camera.moving = false
            }
        }
        return true
    }
    
    fun initTowerSpawners() {
        val towerSpawner2 =
            TowerSpawner(
                TowerSpawner.SpawnerPosition.TOP_RIGHT,
                TCannon(Vector2f(TowerSpawner.SpawnerPosition.TOP_RIGHT.vector2f(context))),
                context
            )
        towerSpawner2.setTexture(R.drawable.cannon)
        addTowerSpawner(towerSpawner2)
        
        val towerSpawner3 =
            TowerSpawner(
                TowerSpawner.SpawnerPosition.MIDDLE_RIGHT,
                TLaser(Vector2f(TowerSpawner.SpawnerPosition.MIDDLE_RIGHT.vector2f(context))),
                context
            )
        towerSpawner3.setTexture(R.drawable.tlaser)
        addTowerSpawner(towerSpawner3)
        
        val towerSpawner4 =
            TowerSpawner(
                TowerSpawner.SpawnerPosition.BOTTOM_RIGHT,
                TRocketSilo(Vector2f(TowerSpawner.SpawnerPosition.BOTTOM_RIGHT.vector2f(context))),
                context
            )
        towerSpawner4.setTexture(R.drawable.silo_closed)
        addTowerSpawner(towerSpawner4)
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private fun addTowerSpawner(towerSpawner: TowerSpawner) {
        towerSpawners.add(towerSpawner)
    }
    
    private var updates = 0
    fun update() {
        if (updates == 0) initTowerSpawners()
        
        rounds.update()
        
        updateGameObjects()
        
        deleteObjects()
        updates++
    }
    
    fun roundStart() {
    
    }
    
    fun roundEnd() {
        projectiles.clear()
    }
    
    private fun checkGameEnd() = gameHealth.get() <= 0
    
    private fun deleteObjects() {
        deleteProjectiles()
        deleteTowers()
        deleteEnemies()
    }
    
    private fun deleteEnemies() {
        enemies.filter { it.toDelete() }.forEach { enemies.remove(it) }
    }
    
    private fun deleteTowers() {
        towers.filter { it.toDelete() }.forEach { towers.remove(it) }
    }
    
    private fun deleteProjectiles() {
        projectiles.filter { it.toDelete() }.forEach { projectiles.remove(it) }
    }
    
    
    private fun updateGameObjects() {
        
        updateEnemies()
        updateTowerSpawners() //Does nothing
        updateTowers()
        updateProjectiles()
        
    }
    
    private fun updateProjectiles() {
        projectiles.update()
    }
    
    private fun updateEnemies() {
        enemies.update()
    }
    
    private fun updateTowers() {
        if (movableTower != null && !movableTower!!.movable.get()) movableTower = null
        towers.updateAreas(enemies)
        towers.update()
    }
    
    private fun updateTowerSpawners() {
        towerSpawners.forEach() { it.update() }
    }
    
    fun drawUPS(canvas: Canvas?) {
        gameLoop ?: return
        val averageUPS = gameLoop!!.averageUPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "UPS: $averageUPS", 100f + camera.x(), 300f + camera.y(), paint
        )
    }
    
    fun drawFPS(canvas: Canvas?) {
        gameLoop ?: return
        val averageFPS = gameLoop!!.averageFPS().toString()
        val color = ContextCompat.getColor(context, R.color.purple_500)
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "FPS: $averageFPS", 100f + camera.x(), 200f + camera.y(), paint
        )
    }
    
    fun drawMoney(canvas: Canvas) {
        val color = Color.BLACK
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        Drawing.drawBitmap(canvas, coinTexture, Vector2f(100f + camera.x(), 80f + camera.y()))
        canvas.drawText(
            "$money", 160f + camera.x(), 100f + camera.y(), paint
        )
    }
    
    private fun drawGameTime(canvas: Canvas) {
        if (TimeController.timeLeft() == -100L) return
        val paint = Paint()
        paint.color = Color.BLACK
        paint.textSize = 50f
        Drawing.drawBitmap(canvas, clockTexture, Vector2f(100f + camera.x(), 180f + camera.y()))
        canvas.drawText(
            timeLeft().toString(),
            camera.x() + 160,
            camera.y() + 200f,
            paint
        )
    }
    
    private fun drawGameHealth(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 50f
        Drawing.drawBitmap(canvas, heartTexture, Vector2f(100f + camera.x(), 280f + camera.y()))
        canvas.drawText(
            gameHealth.get().toString(),
            camera.x() + 160,
            camera.y() + 300f,
            paint
        )
    }
    
/*    override fun surfaceCreated(p0: SurfaceHolder) {
        if (restoreSurfaceView != null) {
            restoreSurfaceView = null
            onRestoreInstanceState(restoreSurfaceView)
        }
    }
    
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        restoreSurfaceView = onSaveInstanceState()
    }
    
    override fun surfaceDestroyed(p0: SurfaceHolder) {
        restoreSurfaceView = onSaveInstanceState()
    }*/
    
    fun getGameState(): Serializable {
        return this
    }
    
    companion object {
        fun setGameState(gameState: Serializable): GameSurfaceView {
            return gameState as GameSurfaceView
        }
    }
    
    
    private val coinTexture = BitmapFactory.decodeResource(
        gameView!!.context.resources,
        R.drawable.coin
    )
    private val clockTexture = BitmapFactory.decodeResource(
        gameView!!.context.resources,
        R.drawable.clock
    )
    private val newRound = BitmapFactory.decodeResource(
        gameView!!.context.resources,
        R.drawable.new_round
    )
    private val heartTexture = BitmapFactory.decodeResource(
        gameView!!.context.resources,
        R.drawable.heart
    )
    
    
    
}