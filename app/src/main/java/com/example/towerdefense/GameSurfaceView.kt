package com.example.towerdefense

import com.example.towerdefense.gameObjects.lists.TowerList
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Looper
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.gameObjects.*
import com.example.towerdefense.gameObjects.enemies.ERound
import com.example.towerdefense.gameObjects.enemies.EnemyWaves
import com.example.towerdefense.gameObjects.lists.ERoundList
import com.example.towerdefense.gameObjects.lists.EnemyList
import com.example.towerdefense.gameObjects.lists.ProjectileList
import com.example.towerdefense.gameObjects.tower.*
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Random.Companion.randomInt
import com.example.towerdefense.utility.textures.BackgroundGenerator
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.*

open class GameSurfaceView(context: Context, private val gameView: GameView) : SurfaceView(context),
    SurfaceHolder.Callback, java.io.Serializable {
    
    var enemies = EnemyList()
    var movableTower: Tower? = null
    var towers = TowerList()
    var towerSpawners = Vector<TowerSpawner>()
    var projectiles = ProjectileList()
    var rounds = ERoundList()
    
    var gameLoop: GameLoop? = null
    var isRunning = false
    internal var road: Road = Road(Vector2i(0, 0))
    private val backgroundGenerator : BackgroundGenerator
    private val background : Bitmap

    init {
        
        road.addLine(Direction2D.RIGHT, 10)
        road.addLine(Direction2D.DOWN, 10)
        road.addLine(Direction2D.RIGHT, 10)
        
        
        backgroundGenerator = BackgroundGenerator(context)
        background = backgroundGenerator.generateBackground(30, 10)
        
        val round = ERound()
        round.addWave(EnemyWaves.Tier1Wave8.getValue(), 0)
        round.addWave(EnemyWaves.Tier1Wave1.getValue(), 1)
        round.addWave(EnemyWaves.Tier1Wave1.getValue(), 2)
        round.addWave(EnemyWaves.Tier1Wave1.getValue(), 4)
        round.addWave(EnemyWaves.Tier1Wave1.getValue(), 6)
    
        round.addWave(EnemyWaves.Tier1Wave1.getValue(), 10)
        
        rounds.add(round)
        
    }
    
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas == null) return
        
        //towerSpawners.forEach { it.draw(canvas) }
        
        camera.updateCanvas(canvas)
        
        //For every R.draw.grass draw it 1_1 to 4_8
        
        drawBackGround(canvas)
        
        //Draw the road
        road.draw(canvas)
        
        enemies.draw(canvas)
        towers.draw(canvas)
        projectiles.draw(canvas)
        
        if (fps) drawUPS(canvas)
        if (fps) drawFPS(canvas)
        
        drawMoney(canvas)
        drawGameHealth(canvas)
        drawGameTime(canvas)
    }
    
    private fun drawBackGround(canvas: Canvas) {
        Drawing.drawBitmap(canvas, background, Vector2f((screenSize.x.toFloat() / 2), (screenSize.y.toFloat() / 2)))
    }
    
    val camera = Camera()
    
    
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val adjustedPosition = camera.adjustedPosition(event)
        
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
                context,
                Vector2f(TowerSpawner.SpawnerPosition.TOP_LEFT.vector2f),
                TCanon(Vector2f(TowerSpawner.SpawnerPosition.TOP_LEFT.vector2f))
            )
        addTowerSpawner(towerSpawner2)
        
        val towerSpawner3 =
            TowerSpawner(
                context,
                Vector2f(TowerSpawner.SpawnerPosition.TOP_RIGHT.vector2f),
                TLaser(Vector2f(TowerSpawner.SpawnerPosition.TOP_RIGHT.vector2f))
            )
        addTowerSpawner(towerSpawner3)
        
        val towerSpawner4 =
            TowerSpawner(
                context,
                Vector2f(TowerSpawner.SpawnerPosition.MIDDLE_LEFT.vector2f),
                TRocketLauncher(Vector2f(TowerSpawner.SpawnerPosition.MIDDLE_LEFT.vector2f))
            )
        addTowerSpawner(towerSpawner4)
    }
    
    @SuppressLint("ClickableViewAccessibility")
    private fun addTowerSpawner(towerSpawner: TowerSpawner) {
        towerSpawners.add(towerSpawner)
        gameView.addView(towerSpawner)
        towerSpawner.requestLayout()
        gameView.invalidate()
        towerSpawner.setOnTouchListener() { _, it ->
            towerSpawner.onTouchEvent(it, camera.adjustedPosition(it))
            performClick()
        }
    }
    
    private var timeLastSpawn = 0L
    fun update() {
        if (checkGameEnd()) gameEnd()
        
        rounds.update()
        
        updateGameObjects()
        
        deleteObjects()
        
        spawnEnemies()
    }
    
    fun roundStart() {
/*
        Toast.makeText(context, "Round ${rounds.getRound()}", Toast.LENGTH_SHORT).show()
*/
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
    
    private fun spawnEnemies() {
        if (TimeController.getGameTime() - timeLastSpawn > randomInt(
                100,
                2000,
                TimeController.getGameTime().toInt()
            )
        ) {
            /*enemies.add(
                Enemy(Box2D(Vector2f(0f, 0f), Vector2f(100f, 100f)), road).apply {
                    this.setHealth(randomInt(1, 5, TimeController.getGameTime().toInt()))
                    velocity(randomFloat(8f, 15f, TimeController.getGameTime().toInt()))
                }
            )*/
            timeLastSpawn = TimeController.getGameTime()
            //health = (health * 1.1).toInt()
        }
    }
    
    private fun deleteProjectiles() {
        projectiles.filter { it.toDelete() }.forEach { projectiles.remove(it) }
    }
    
    private fun gameEnd() {
        gameView.gamePause()
    }
    
    
    private fun updateGameObjects() {
        
        
        updateMovableTower()
        
        updateEnemies()
        updateTowerSpawners() //Does nothing
        updateTowers()
        updateProjectiles()

/*
        movableTowers.removeIf { !it.movable.get() }
*/
    }
    
    private fun updateProjectiles() {
        projectiles.update()
    }
    
    private fun updateMovableTower() {
        if (!movableTowerUpdateConditions()) return
        towers.forEach {
            if (movableTower != it) {
                if(IntersectionDetector2D.intersection(movableTower!!.collider2D(), it.collider2D())) {
                    movableTower!!.fixable.set(false)
                    return@forEach
                }
            }
        }
        movableTower!!.fixable.set(true)
        return
    }
    
    private fun movableTowerUpdateConditions(): Boolean {
        if (movableTower == null) return false
        if (!movableTower!!.movable.get()) {
            movableTower = null
            return false
        }
        if (towers.size == 1) return false
        return true
    }
    
    
    private fun updateEnemies() {
        enemies.update()
    }
    
    private fun updateTowers() {
        towers.updateAreas(enemies)
        towers.update()
    }
    
    private fun updateTowerSpawners() { towerSpawners.forEach() { it.update() } }
    
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
    
    fun drawMoney(canvas: Canvas?) {
        val color = Color.CYAN
        val paint = Paint()
        paint.color = color
        paint.textSize = 50f
        canvas?.drawText(
            "Money: $money", 100f + camera.x(), 100f + camera.y(), paint
        )
    }
    
    private fun drawGameTime(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 50f
        canvas.drawText(
            "Time: ${TimeController.getGameTime() / 1000} ${TimeController.getSinceAppStart() / 1000}",
            camera.x() + 100,
            camera.y() + 200f,
            paint
        )
    }
    
    private fun drawGameHealth(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 50f
        canvas.drawText("Health: $gameHealth", camera.x() + 100f, camera.y() + 300f, paint)
    }
    
    override fun surfaceCreated(p0: SurfaceHolder) {
    
    }
    
    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }
    
    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }
}