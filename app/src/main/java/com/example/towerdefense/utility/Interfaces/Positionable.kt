package com.example.towerdefense.utility.Interfaces

import org.joml.Vector2f

interface Positionable: Collisionable {
    fun position(position: Vector2f) {
        collider().body.position = position
    }
    fun position(): Vector2f {
        return Vector2f(collider().body.position)
    }
}