package com.example.towerdefense.gameObjects.tower

import android.graphics.Canvas
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.primitives.Circle
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.utility.TimeController
import com.example.towerdefense.utility.angle
import com.example.towerdefense.utility.gameView
import com.example.towerdefense.utility.towerClicked
import org.joml.Vector2f

class Canon(radius: Float, private val box2D: Box2D) : Tower(radius, box2D) {


    var projectileShot: Projectile? = null
    override var hitDelay = 1000f
    override fun applyDamageInArea() {
        if (readyToDamage()) {
            val projectile = Projectile(Circle(10f, Vector2f(box2D.body.position)), 100)
            projectile.setVelocity(10f)
            val enemy = towerArea.getFirst()!!
            val rotation = Vector2f(enemy.position()).sub(box2D.body.position).normalize().angle()
            projectile.setRotation(rotation)
            projectileShot = projectile
            gameView!!.surfaceView.projectiles.add(projectile)
            timeLastDamage = TimeController.getGameTime()
        }
    }

    override fun draw(canvas: Canvas) {
        if (towerClicked == this) towerArea.draw(canvas)
        box2D.draw(canvas)
        projectileShot?.draw(canvas)
    }

    override fun update() {
        println("update")
        super.update()
        projectileShot?.update()
        applyDamageInArea()
    }

    override fun clone(): Tower {
        return Canon(radius, box2D.clone() as Box2D)
    }
}