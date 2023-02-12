package com.example.towerdefense.utility

import org.junit.Assert.*
import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MultiVectorTest {
    @Test
    fun testConstructors() {
        val multiVector1 = MultiVector()
        assertTrue(multiVector1.vectorConnections.isEmpty())

        val position1 = Vector2Di(0, 0)
        val multiVector2 = MultiVector(position1)
        assertEquals(1, multiVector2.vectorConnections.size)
        assertTrue(multiVector2.vectorConnections.containsKey(position1))
        assertEquals(Directions2D.UNDEFINED, multiVector2.vectorConnections[position1])
    }

    @Test
    fun testAddNotSafe() {
        val multiVector = MultiVector()
        val position = Vector2Di(0, 0)
        val direction = Directions2D.UP
        assertTrue(multiVector.addNotSafe(position, direction))
        assertEquals(1, multiVector.vectorConnections.size)
        assertTrue(multiVector.vectorConnections.containsKey(position))
        assertEquals(direction, multiVector.vectorConnections[position])
    }

    @Test
    fun testAdd1() {
        val position1 = Vector2Di(0, 0)
        val position2 = Vector2Di(1, 0)
        val multiVector = MultiVector(position1)
        assertTrue(multiVector.add(position2))
        assertEquals(2, multiVector.vectorConnections.size)
        assertTrue(multiVector.vectorConnections.containsKey(position1))
        assertNotEquals(Directions2D.UNDEFINED, multiVector.vectorConnections[position1])

        val position3 = Vector2Di(1, 1)
        assertTrue(multiVector.add(position3))
        assertEquals(3, multiVector.vectorConnections.size)
        assertTrue(multiVector.vectorConnections.containsKey(position3))
        assertNotEquals(Directions2D.UNDEFINED, multiVector.vectorConnections[position3])
    }

    @Test
    fun testAdd2() {
        val position1 = Vector2Di(0, 0)
        val multiVector = MultiVector()
        assertFalse(multiVector.add(position1))
        assertEquals(0, multiVector.vectorConnections.size)
    }

    @Test
    fun testDirections1() {
        val position1 = Vector2Di(0, 0)
        val position2 = Vector2Di(1, 0)
        val position3 = Vector2Di(1, 1)
        val multiVector = MultiVector(position1)
        multiVector.add(position2)
        multiVector.add(position3)
        assertEquals(Directions2D.RIGHT, multiVector.getDirections(position1))
        assertEquals(Directions2D.DOWN_LEFT, multiVector.getDirections(position2))
        assertEquals(Directions2D.UP, multiVector.getDirections(position3))
    }

    @Test
    fun testDirections2() {
        val position1 = Vector2Di(0, 0)
        val position2 = Vector2Di(1, 0)
        val position3 = Vector2Di(1, 1)
        val position4 = Vector2Di(0, 1)
        val multiVector = MultiVector(position1)
        multiVector.add(position2)
        multiVector.add(position3)
        multiVector.add(position4)
        assertEquals(Directions2D.DOWN_RIGHT, multiVector.getDirections(position1))
        assertEquals(Directions2D.DOWN_LEFT, multiVector.getDirections(position2))
        assertEquals(Directions2D.UP_LEFT, multiVector.getDirections(position3))
        assertEquals(Directions2D.UP_RIGHT, multiVector.getDirections(position4))
    }

    @Test
    fun testDirections3() {
        val positions = arrayOf(
            Vector2Di(0, 0),
            Vector2Di(1, 0),
            Vector2Di(0, 1),
            Vector2Di(1, 1),
            Vector2Di(-1, 0),
            Vector2Di(0, -1),
            Vector2Di(-1, -1),
            Vector2Di(-1, 1),
            Vector2Di(1, -1)
        )

        val multiVector = MultiVector(positions[0])
        multiVector.add(positions)

        assertEquals(Directions2D.UP_DOWN_LEFT_RIGHT, multiVector.getDirections(positions[0]))
        assertEquals(Directions2D.UP_DOWN_LEFT, multiVector.getDirections(positions[1]))
        assertEquals(Directions2D.UP_LEFT_RIGHT, multiVector.getDirections(positions[2]))
        assertEquals(Directions2D.UP_LEFT, multiVector.getDirections(positions[3]))
        assertEquals(Directions2D.UP_DOWN_RIGHT, multiVector.getDirections(positions[4]))
        assertEquals(Directions2D.DOWN_LEFT_RIGHT, multiVector.getDirections(positions[5]))
        assertEquals(Directions2D.DOWN_RIGHT, multiVector.getDirections(positions[6]))
        assertEquals(Directions2D.UP_RIGHT, multiVector.getDirections(positions[7]))
        assertEquals(Directions2D.DOWN_LEFT, multiVector.getDirections(positions[8]))
    }
}
