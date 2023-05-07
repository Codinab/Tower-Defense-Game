package com.example.towerdefense.gameObjects.tower

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.ViewGroup
import com.example.towerdefense.GameObjectView
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Updatable
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("ViewConstructor")
class TowerSpawner(private val box2D: Box2D, var modelTower: Tower) :
    InputEvent, Updatable, Drawable {
    
    constructor(position: SpawnerPosition, modelTower: Tower) : this(
        Box2D(Vector2f(DEF_WIDTH, DEF_HEIGHT), Rigidbody2D(position.vector2f())),
        modelTower
    ) {
        spawnerPosition = position
    }
    
    private var texture: Bitmap? = null
    
    private fun setTexture(texture: Bitmap) {
        this.texture =
            Bitmap.createScaledBitmap(texture, (DEF_WIDTH * 0.8).toInt(), (DEF_HEIGHT * 0.8).toInt(), false)
    }
    
    fun setTexture(resId: Int) {
        setTexture(BitmapFactory.decodeResource(gameView!!.resources, resId))
    }
    
    companion object {
        const val DEF_HEIGHT = 120f
        const val DEF_WIDTH = 120f
        const val PADDING = 20f
        
    }
    
    private val backgroundTextureResized =
        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(gameView!!.resources, com.example.towerdefense.R.drawable.universal_background), DEF_WIDTH.toInt(), DEF_HEIGHT.toInt(), false)
    
    init {
    
    }
    
    
    private var lastTower: Tower? = null
    var damageType = TowerArea.DamageType.FIRST
    override fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean {
    
        if (lastTower != null && lastTower!!.movable.get()) {
            return false
        }
        
        
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (money.getAndAdd(-modelTower.buildCost()) < modelTower.buildCost() ||
                    gameView!!.surfaceView.movableTower != null
                ) return false.also {
                    money.getAndAdd(
                        modelTower.buildCost()
                    )
                }
                val tower = modelTower.clone()
                
                gameView!!.surfaceView.towers.add(tower)
                gameView!!.surfaceView.movableTower = tower
                gameView!!.hideTowerButtons()
                
                lastTower = tower
                towerClicked = tower
    
                true
            }
            MotionEvent.ACTION_MOVE -> return true
            MotionEvent.ACTION_UP -> {
                if (lastTower != null && IntersectionDetector2D.intersection(collider().clone().body.position.add(cameraPosition), lastTower!!.collider())) {
                    lastTower!!.destroy()
                    return true
                } else lastTower = null
                return false
            }
            else -> false
        }
    }
    
    
    override fun draw(canvas: Canvas) {
        Drawing.drawBitmap(canvas, backgroundTextureResized, position())
        
        if (texture == null) return
        if (lastTower == null || !lastTower!!.movable.get()) {
            Drawing.drawBitmap(canvas, texture!!, position())
        }
        
    }
    
    fun damageType(damageType: TowerArea.DamageType) {
        this.damageType = damageType
    }
    
    private var spawnerPosition: SpawnerPosition? = null
    fun position(spawnerPosition: SpawnerPosition) {
        position(spawnerPosition.vector2f().add(cameraPosition))
        this.spawnerPosition = spawnerPosition
        modelTower.position(spawnerPosition.vector2f())
    }
    
    override fun position(): Vector2f {
        if (spawnerPosition != null) position(spawnerPosition!!)
        return super.position()
    }
    

    
    
    enum class SpawnerPosition() {
        TOP_LEFT,
        TOP_RIGHT,
        MIDDLE_LEFT,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT;
        
        fun vector2f(): Vector2f {
            return when (this) {
                TOP_LEFT -> Vector2f(screenSize.x - DEF_WIDTH * 2 - PADDING * 2, 300f)
                TOP_RIGHT -> Vector2f(screenSize.x - DEF_WIDTH - PADDING, 300f)
                MIDDLE_LEFT -> Vector2f(
                    screenSize.x - DEF_WIDTH * 2 - PADDING * 2,
                    TOP_LEFT.vector2f().y + DEF_HEIGHT + PADDING
                )
                MIDDLE_RIGHT -> Vector2f(
                    screenSize.x - DEF_WIDTH - PADDING,
                    TOP_RIGHT.vector2f().y + DEF_HEIGHT + PADDING
                )
                BOTTOM_LEFT -> Vector2f(
                    screenSize.x - DEF_WIDTH * 2 - PADDING * 2,
                    MIDDLE_LEFT.vector2f().y + DEF_HEIGHT + PADDING
                )
                BOTTOM_RIGHT -> Vector2f(
                    screenSize.x - DEF_WIDTH - PADDING,
                    MIDDLE_RIGHT.vector2f().y + DEF_HEIGHT + PADDING
                )
            }
        }
    }
    
    override var lastClickTime: Long = 0L
    override var toDelete: Boolean = false
    override var movable: AtomicBoolean = AtomicBoolean(false)
    override var fixable: AtomicBoolean = AtomicBoolean(false)
    override val layerLevel: Int = 0
    
    override fun collider(): Collider2D {
        return box2D
    }
    
    override fun update() {
    
    }
}
