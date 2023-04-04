package com.example.towerdefense.utility.Interfaces

import org.joml.Vector2f

interface Positionable {
    fun position(position: Vector2f)
    fun position(): Vector2f
}