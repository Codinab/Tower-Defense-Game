package com.example.towerdefense.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.example.towerdefense.Physics2d.primitives.Collider2D
import com.example.towerdefense.Physics2d.rigidbody.IntersectionDetector2D
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.utility.Interfaces.Positionable
import org.joml.Vector2f

@SuppressLint("ViewConstructor")
class GameObjectView(private val context: GameActivity, private var collider2D: Collider2D) :
    androidx.appcompat.widget.AppCompatImageView(context), Positionable {

    var lastClickTime: Long = 0
    var movable: Boolean = true
    var fixable: Boolean = true
    init {
        val layoutSize = collider2D.layoutSize()
        layoutParams = ViewGroup.LayoutParams(layoutSize.x.toInt(), layoutSize.y.toInt())
        position(collider2D.body.position)
        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val layoutSize = collider2D.layoutSize()
        setMeasuredDimension(layoutSize.x.toInt(), layoutSize.y.toInt())
    }

    override fun position(position: Vector2f) {
        x = position.x
        y = position.y
        collider2D.body.position = position
    }

    override fun position(): Vector2f {
        return Vector2f(collider2D.body.position).add(context.gameView()!!.surfaceView.camera().position())
    }
    
    override fun collider(): Collider2D {
        return collider2D
    }
    
    fun isClicked(vector2f: Vector2f): Boolean {
        return IntersectionDetector2D.intersection(vector2f, collider2D)
    }
}
