package com.example.towerdefense.utility

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test

class RoadTest {

    @Test
    fun initiateRoad_Success() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        for (i in 1 until positions.size) {
            multiVector.add(positions[i])
        }

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        val expectedDirections = hashMapOf(
            Vector2Di(0, 0) to Direction2D.RIGHT,
            Vector2Di(1, 0) to Direction2D.RIGHT,
            Vector2Di(2, 0) to Direction2D.RIGHT,
            Vector2Di(3, 0) to Direction2D.UNDEFINED
        )
        assertEquals(expectedDirections, road.roadDirections)
    }

    @Test(expected = IncorrectFormatException::class)
    fun initiateRoad_IncorrectFormat1() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(2, 1),
            Vector2Di(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        for (i in 1 until positions.size) {
            multiVector.add(positions[i])
        }

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)
    }

    @Test
    fun getRoadDirection_Success1() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        assertEquals(Direction2D.RIGHT, road.getRoadDirection(Vector2Di(0, 0)))
        assertEquals(Direction2D.RIGHT, road.getRoadDirection(Vector2Di(1, 0)))
        assertEquals(Direction2D.RIGHT, road.getRoadDirection(Vector2Di(2, 0)))
        assertEquals(Direction2D.UNDEFINED, road.getRoadDirection(Vector2Di(3, 0)))
    }

    @Test
    fun getRoadDirection_Success2() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        assertEquals(Direction2D.UNDEFINED, road.getRoadDirection(Vector2Di(0, 1)))
    }

    @Test(expected = IncorrectFormatException::class)
    fun initiateRoad_IncorrectFormat() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(2, 1),
            Vector2Di(3, 0)
        )

        val multiVector = MultiVector(positions[0])
        for (i in 1 until positions.size) {
            multiVector.add(positions[i])
        }

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)
    }

    @Test(expected = IncorrectPositionException::class)
    fun initiateRoad_IncorrectPosition() {
        val startVector = Vector2Di(0, 0)
        val endVector = Vector2Di(3, 0)
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(2, 0),
            Vector2Di(3, 0),
            Vector2Di(4, 0)
        )

        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)
    }
}
