package com.example.towerdefense.utility

import android.graphics.Canvas
import android.graphics.Paint
import android.util.ArrayMap
import org.joml.Vector2f
import org.joml.Vector2i
import java.io.Serializable

class Road(private var startVector: Vector2i, multiVector: MultiVector) : Serializable {

    private val roadHeight: Int = 10
    private val roadWidth: Int = 10
    private val roadPaint: Paint = Paint()
    private var roadDirections: ArrayList<Pair<Vector2i, Direction2D>> = ArrayList()
    private var roadCorners : ArrayList<Pair<Vector2i, Direction2D>> = ArrayList()


    init {
        if (!roadFormat(multiVector)) throw IncorrectFormatException

        multiVector.positions.remove(startVector)
        val positions = multiVector.positions

        val positionArray: Array<Vector2i?> = Array(positions.size + 1) { null }
        positionArray[0] = startVector


        for (i in 1..positions.size) {
            for (position in positions) {
                if (position.distanceSquared(positionArray[i - 1]!!) <= 1.01) {
                    positionArray[i] = position
                    positions.remove(position)
                    break
                }
            }
        }

        for (i in 0 until positionArray.size - 1) {
            val direction =
                Direction2D.fromVector(positionArray[i + 1]!!.sub(positionArray[i]!!, Vector2i()))
            roadDirections.add(Pair(positionArray[i]!!, direction))
        }
        roadDirections.add(Pair(positionArray[positionArray.size - 1]!!, Direction2D.UNDEFINED))
        roadPaint.strokeWidth = 10f
        roadPaint.color = android.graphics.Color.RED

        for (i in 0 until roadDirections.size - 1) {
            if (roadDirections[i].second != roadDirections[i + 1].second) {
                roadCorners.add(roadDirections[i + 1])
            }
        }

        roadCorners.forEach { println(it.first) }

    }

    fun getRoadDirection(position: Vector2i): Direction2D {
        return roadDirections.first { it.first == position }.second
    }


    fun getAllDirections(): List<Direction2D> {
        return roadDirections.map { it.second }
    }

    //Returns the next position in the road that changes direction
    fun getNextCorner(position: Vector2f?): Vector2f {
        if (position == null) return roadCorners.first().first.toVector2f()
        for (i in 0 until roadCorners.size - 1) {
            if (roadCorners[i].first.toVector2f() == position) {
                return roadCorners[i + 1].first.toVector2f()
            }
        }
        return roadCorners.last().first.toVector2f()
    }

    fun getStart(): Vector2i = roadDirections.first().first

    fun draw(canvas: Canvas) {
        for ((start, direction) in roadDirections) {
            val startX = start.x * roadWidth
            val startY = start.y * roadHeight
            val endX = (start.x + direction.vector.x) * roadWidth
            val endY = (start.y + direction.vector.y) * roadHeight

            canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(),
                endY.toFloat(), roadPaint)
        }
    }


    companion object {

        fun Vector2i.toVector2f(): Vector2f {
            return Vector2f(this.x.toFloat(), this.y.toFloat())
        }
        fun Vector2f.toVector2i(): Vector2i {
            return Vector2i(this.x.toInt(), this.y.toInt())
        }
        fun roadFormat(multiVector: MultiVector): Boolean {
            var oneDirection = 2
            for (directions in multiVector.allDirections) {
                if (directions.directionCount > 2) return false
                if (directions.directionCount == 1) oneDirection--
                if (directions.directionCount < 1) return false
            }
            return oneDirection == 0
        }
    }
}

object IncorrectFormatException : Throwable() {

}