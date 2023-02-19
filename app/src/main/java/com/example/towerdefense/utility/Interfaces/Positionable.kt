package com.example.towerdefense.utility.Interfaces

import org.joml.Vector2f

interface Positionable {
    fun setPosition(position: Vector2f)
    fun getPosition(): Vector2f
}