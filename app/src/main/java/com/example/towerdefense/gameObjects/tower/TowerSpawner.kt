package com.example.towerdefense.gameObjects.tower

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.gameObjects.tower.utils.TowerArea
import com.example.towerdefense.utility.*
import com.example.towerdefense.utility.Interfaces.Drawable
import com.example.towerdefense.utility.Interfaces.InputEvent
import com.example.towerdefense.utility.Interfaces.Updatable
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import java.util.concurrent.atomic.AtomicBoolean

@SuppressLint("ViewConstructor")
class TowerSpawner(private val box2D: Box2D, var modelTower: Tower, private val context: GameActivity) : InputEvent, Updatable, Drawable, java.io.Serializable {
    
    constructor(position: SpawnerPosition, modelTower: Tower, context: GameActivity) : this(
        Box2D(Vector2f(DEF_WIDTH, DEF_HEIGHT), Rigidbody2D(position.vector2f(context))), modelTower, context
    ) {
        spawnerPosition = position
    }
    
    private var texture: Bitmap? = null
    
    private fun setTexture(texture: Bitmap) {
        this.texture =
            Bitmap.createScaledBitmap(texture, (DEF_WIDTH * 0.8).toInt(), (DEF_HEIGHT * 0.8).toInt(), false)
    }
    
    fun setTexture(resId: Int) {
        setTexture(BitmapFactory.decodeResource(context.gameView()!!.resources, resId))
    }
    
    companion object {
        const val DEF_HEIGHT = 120f
        const val DEF_WIDTH = 120f
        const val PADDING = 20f
        
    }
    
    private val backgroundTextureResized =
        Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.gameView()!!.resources, com.example.towerdefense.R.drawable.universal_background), DEF_WIDTH.toInt(), DEF_HEIGHT.toInt(), false)
    
    init {
    
    }
    
    
    private var lastTower: Tower? = null
    var damageType = TowerArea.DamageType.FIRST
    override fun onTouchEvent(event: MotionEvent, position: Vector2f, context: GameActivity): Boolean {
        
        if (lastTower != null && lastTower!!.movable.get()) {
            return false
        }
        
        
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (context.gameView()!!.money.getAndAdd(-modelTower.buildCost()) < modelTower.buildCost() || context.gameView()!!.surfaceView.movableTower != null) return false.also {
                    context.gameView()!!.money.getAndAdd(
                        modelTower.buildCost()
                    )
                }
                val tower = modelTower.clone()
                
                context.gameView()!!.surfaceView.towers.add(tower)
                context.gameView()!!.surfaceView.movableTower = tower
                context.gameView()!!.hideTowerButtons()
                
                lastTower = tower
                towerClicked = tower
                
                true
            }
            MotionEvent.ACTION_MOVE -> return true
            MotionEvent.ACTION_UP -> {
                if (lastTower != null && IntersectionDetector2D.intersection(collider().clone()
                        .body.position.add(context.gameView()!!.surfaceView.camera().position()),
                            lastTower!!.collider())) {
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
        position(spawnerPosition.vector2f(context).add(context.gameView()!!.
            surfaceView.camera().position()))
        this.spawnerPosition = spawnerPosition
        modelTower.position(spawnerPosition.vector2f(context))
    }
    
    override fun position(): Vector2f {
        if (spawnerPosition != null) position(spawnerPosition!!)
        return super.position()
    }
    
    
    enum class SpawnerPosition() {
        TOP_LEFT, TOP_RIGHT, MIDDLE_LEFT, MIDDLE_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;
        
        fun vector2f(context: Context): Vector2f {
            (context as GameActivity)
            val screenSize = context.getScreenSize()
            return when (this) {
                TOP_LEFT -> Vector2f(screenSize.x - DEF_WIDTH * 2 - PADDING * 2, 300f)
                TOP_RIGHT -> Vector2f(screenSize.x - DEF_WIDTH - PADDING, 300f)
                MIDDLE_LEFT -> Vector2f(
                    screenSize.x - DEF_WIDTH * 2 - PADDING * 2, TOP_LEFT.vector2f(context).y + DEF_HEIGHT + PADDING
                )
                MIDDLE_RIGHT -> Vector2f(
                    screenSize.x - DEF_WIDTH - PADDING, TOP_RIGHT.vector2f(context).y + DEF_HEIGHT + PADDING
                )
                BOTTOM_LEFT -> Vector2f(
                    screenSize.x - DEF_WIDTH * 2 - PADDING * 2, MIDDLE_LEFT.vector2f(context).y + DEF_HEIGHT + PADDING
                )
                BOTTOM_RIGHT -> Vector2f(
                    screenSize.x - DEF_WIDTH - PADDING, MIDDLE_RIGHT.vector2f(context).y + DEF_HEIGHT + PADDING
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
        position(spawnerPosition!!)
    }
}
