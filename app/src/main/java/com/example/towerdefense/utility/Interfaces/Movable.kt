package com.example.towerdefense.utility.Interfaces

interface Movable {
    fun addVelocity(velocity: Float)
    fun velocity(): Float
    fun velocity(velocity: Float)
    fun setAngularVelocity(angularVelocity: Float)
    fun getAngularVelocity(): Float
    fun addAngularVelocity(angularVelocity: Float)
    fun setRotation(rotation: Float)
    fun getRotation(): Float
    fun addRotation(rotation: Float)
    fun update()
}