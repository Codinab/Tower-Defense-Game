package com.example.towerdefense.utility

import java.io.Serializable

class Road(private var startVector: Vector2Di,private var endVector: Vector2Di) : Serializable {

    internal var roadDirections : HashMap<Vector2Di, Direction2D> = HashMap()


    fun initiateRoad(multiVector: MultiVector) {
        if (!roadFormat(multiVector)) throw IncorrectFormatException

        multiVector.positions.remove(startVector)
        var positions = multiVector.positions

        var positionArray : Array<Vector2Di?> = Array(positions.size + 1) { null }
        positionArray[0] = startVector


        for (i in 1 .. positions.size) {
            for (position in positions) {
                if(position.nextTo(positionArray[i-1])) {
                    positionArray[i] = position
                    positions.remove(position)
                    break
                }
            }
        }

        if (positionArray[positionArray.size-1] != endVector) throw IncorrectPositionException

        for (i in 0 until positionArray.size - 1) {
            var direction = positionArray[i]!!.direction(positionArray[i + 1])
            roadDirections.set(positionArray[i]!!, direction)
        }
        roadDirections.set(positionArray[positionArray.size-1]!!, Direction2D.UNDEFINED)
    }



    fun getRoadDirection(position: Vector2Di): Direction2D {
        return roadDirections[position] ?: Direction2D.UNDEFINED
    }

    fun getAllDirections() : List<Direction2D> {
        return roadDirections.values.toList()
    }

    companion object {
        fun roadFormat(multiVector: MultiVector) : Boolean {
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
object IncorrectPositionException : Throwable() {

}
object IncorrectFormatException : Throwable() {

}