package com.example.towerdefense.utility.Interfaces

import com.example.towerdefense.utility.Direction2D

interface Movable {
    fun move(direction2D: Direction2D, speed: Int)
    fun addVelocity(velocity: Float)
    fun getVelocity(): Float
    fun setVelocity(velocity: Float)
    fun setAngularVelocity(angularVelocity: Float)
    fun getAngularVelocity(): Float
    fun addAngularVelocity(angularVelocity: Float)
    fun setRotation(rotation: Float)
    fun getRotation(): Float
    fun addRotation(rotation: Float)
    fun maxX(): Float
    fun minX(): Float
    fun maxY(): Float
    fun minY(): Float
}