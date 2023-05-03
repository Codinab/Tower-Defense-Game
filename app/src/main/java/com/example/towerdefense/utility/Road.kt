package com.example.towerdefense.utility

import android.graphics.Canvas
import android.graphics.Paint
import com.example.towerdefense.Physics2d.primitives.Box2D
import com.example.towerdefense.Physics2d.rigidbody.Rigidbody2D
import com.example.towerdefense.utility.KMath.Companion.toVector2f
import com.example.towerdefense.utility.textures.Drawing
import org.joml.Vector2f
import org.joml.Vector2i
import java.io.Serializable
import kotlin.math.pow

class Road(private val startVector: Vector2i) : Serializable {
    
    private val roadHeight: Int = 100
    private val roadWidth: Int = 100
    private val roadPaint: Paint = Paint()
    private var roadDirections: ArrayList<Pair<Vector2i, Direction2D>> = ArrayList()
    private var roadCorners: ArrayList<Pair<Vector2i, Direction2D>> = ArrayList()
    
    
    init {
    
        roadPaint.strokeWidth = 100f
        roadPaint.color = android.graphics.Color.YELLOW
        roadPaint.alpha = 100
        
        roadDirections.add(Pair(startVector, Direction2D.RIGHT))
        roadCorners.add(roadDirections.first())
        
    }
    
    fun isLastCorner(position: Vector2f): Boolean {
        val position2f = Vector2f(position)
        position2f.div(roadWidth.toFloat(), roadHeight.toFloat())
        return roadCorners.last().first.toVector2f() == position2f
    }
    
    private fun updateCorners() {
        
        roadCorners.clear()
        
        roadCorners.add(roadDirections.first())
        
        for (i in 0 until roadDirections.size - 1) {
            if (roadDirections[i].second != roadDirections[i + 1].second) {
                roadCorners.add(Pair(roadDirections[i].first, roadDirections[i + 1].second))
            }
        }
        roadCorners.add(Pair(roadDirections.last().first, Direction2D.UNDEFINED))
        
        println(roadCorners)
    }
    
    fun addDirection(direction: Direction2D) {
        val lastPosition = roadDirections.last().first
        val newPosition = lastPosition.add(direction.vector, Vector2i())
        roadDirections.add(Pair(newPosition, direction))
        updateCorners()
    }
    
    fun addLine(direction: Direction2D, length: Int) {
        for (i in 0 until length) {
            addDirection(direction)
        }
    }
    
    fun getFirstDirection(): Direction2D {
        return roadDirections.first().second
    }
    
    fun getStart(): Vector2f = roadToCanvasPosition(roadDirections.first().first)
    
    
    fun draw(canvas: Canvas) {
        debugDraw(canvas)
    }
    
    fun distanceToRoad(vector2f: Vector2f): Float {
        var minDistance = Float.MAX_VALUE
        for ((start, _) in roadDirections) {
            val canvasStart = roadToCanvasPosition(start)
            val distance = vector2f.distanceSquared(canvasStart)
            if (distance < minDistance) {
                minDistance = distance
            }
        }
        return minDistance
    }
    
    
    private fun debugDraw(canvas: Canvas) {
        for ((start, direction) in roadDirections) {
            
            
            Drawing.drawBox2D(canvas, Box2D(Vector2f(roadWidth.toFloat(), roadHeight.toFloat()),
                Rigidbody2D(roadToCanvasPosition(start))),roadPaint)
        }
    }
    
    private fun roadToCanvasPosition(position: Vector2i): Vector2f {
        return Vector2f(position.x.toFloat() * roadWidth, position.y.toFloat() * roadHeight)
    }
    
    
    private fun inCorner(position: Vector2f, cornerN: Int, velocity: Float): Boolean {
        val corner = roadToCanvasPosition(roadCorners[cornerN].first)
        return corner.distanceSquared(position) < (velocity).pow(2)
    }
    
    fun getDirection(position: Vector2f, corner: Int, velocity: Float): Direction2D {
        //if(corner == roadCorners.size - 1) return Direction2D.UNDEFINED
        return if(corner != roadCorners.size - 1) {
            val inNextCorner = inCorner(position, corner + 1, velocity)
            if (inNextCorner) roadCorners[corner + 1].second else roadCorners[corner].second
        } else {
            val inNextCorner = inCorner(position, roadCorners.size - 1, velocity)
            if (inNextCorner) Direction2D.UNDEFINED else roadCorners[corner].second
        }
    }
    
    fun distanceToNextCornerSquared(position: Vector2f, corner: Int): Float {
        val cornerPosition = roadToCanvasPosition(roadCorners[corner + 1].first)
        return cornerPosition.distanceSquared(position)
    }
}


object IncorrectFormatException : Throwable() {

}