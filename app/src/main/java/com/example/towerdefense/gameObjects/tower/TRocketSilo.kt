package com.example.towerdefense.gameObjects.tower

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.gameObjects.tower.utils.ExplosiveRocket
import com.example.towerdefense.utility.KMath.Companion.angle
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.textures.Drawing
import com.example.towerdefense.utility.towerClicked
import org.joml.Vector2f

class TRocketSilo(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {
    override var timeActionDelay: Float = 1000f
    
    constructor(position: Vector2f) : this(300f, Box2D(Vector2f(100f, 100f), Rigidbody2D(position)))
    
    private val textureOpenedResized = Bitmap.createScaledBitmap(textureOpened, box2D.size.x.toInt(), box2D.size.y.toInt(), false)
    private val textureClosedResized = Bitmap.createScaledBitmap(textureClosed, box2D.size.x.toInt(), box2D.size.y.toInt(), false)
    
    
    
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            val enemies = towerArea.toDamageList()
            val enemy = enemies?.first() ?: return
            val explosiveRocket = ExplosiveRocket(Circle(30f, Vector2f(box2D.body.position)), enemies)
            explosiveRocket.velocity(10f)
            val rotation = Vector2f(enemy.position()).sub(box2D.body.position).angle()
            explosiveRocket.setRotation(rotation)
            gameView!!.surfaceView.projectiles.add(explosiveRocket)
            timeLastAction = TimeController.getGameTime()
        }
    }
    
    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        drawPositionable(canvas)
        
        if (timeLastAction + timeActionDelay / 2 > TimeController.getGameTime()) {
            Drawing.drawBitmap(canvas, textureOpenedResized, box2D.body.position, 0f)
        } else {
            Drawing.drawBitmap(canvas, textureClosedResized, box2D.body.position, 0f)
        }
    }
    
    override fun buildCost(): Int {
        return 1000
    }
    
    override fun upgrade() {
    }
    
    override fun upgradeCost(): Int {
        return 200
    }
    
    override fun upgradeInfo(): String {
        return "Upgrade not available"
    }
    
    override fun clone(): Tower {
        return TRocketSilo(radius, box2D.clone() as Box2D)
    }
    
    override var layerLevel: Int = 0
    companion object {
        private val textureOpened = BitmapFactory.decodeResource(gameView!!.resources, com.example.towerdefense.R.drawable.silo_opened)
        private val textureClosed = BitmapFactory.decodeResource(gameView!!.resources, com.example.towerdefense.R.drawable.silo_closed)
    }
}
