package com.example.towerdefense.utility

import org.junit.Assert.assertEquals
import org.junit.Test

class RoadTest {

    @Test
    fun initiateRoad_Success() {
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        for (i in 1 until positions.size) {
            multiVector.add(positions[i])
        }

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        val expectedDirections = hashMapOf(
            Vector2i(0, 0) to Direction2D.RIGHT,
            Vector2i(1, 0) to Direction2D.RIGHT,
            Vector2i(2, 0) to Direction2D.RIGHT,
            Vector2i(3, 0) to Direction2D.UNDEFINED
        )
        assertEquals(expectedDirections, road.roadDirections)
    }

    @Test(expected = IncorrectFormatException::class)
    fun initiateRoad_IncorrectFormat1() {
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(2, 1),
            Vector2i(3, 0)
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
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        assertEquals(Direction2D.RIGHT, road.getRoadDirection(
            Vector2i(
                0,
                0
            )
        ))
        assertEquals(Direction2D.RIGHT, road.getRoadDirection(
            Vector2i(
                1,
                0
            )
        ))
        assertEquals(Direction2D.RIGHT, road.getRoadDirection(
            Vector2i(
                2,
                0
            )
        ))
        assertEquals(Direction2D.UNDEFINED, road.getRoadDirection(
            Vector2i(
                3,
                0
            )
        ))
    }

    @Test
    fun getRoadDirection_Success2() {
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(3, 0)
        )
        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)

        assertEquals(Direction2D.UNDEFINED, road.getRoadDirection(
            Vector2i(
                0,
                1
            )
        ))
    }

    @Test(expected = IncorrectFormatException::class)
    fun initiateRoad_IncorrectFormat() {
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(2, 1),
            Vector2i(3, 0)
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
        val startVector = Vector2i(0, 0)
        val endVector = Vector2i(3, 0)
        val positions = arrayOf(
            Vector2i(0, 0),
            Vector2i(1, 0),
            Vector2i(2, 0),
            Vector2i(3, 0),
            Vector2i(4, 0)
        )

        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        val road = Road(startVector, endVector)
        road.initiateRoad(multiVector)
    }
}
