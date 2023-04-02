package com.example.towerdefense.utility.Interfaces

import com.example.towerdefense.utility.Direction2D

interface Movable {
    fun addVelocity(velocity: Float)
    fun getVelocity(): Float
    fun setVelocity(velocity: Float)
    fun setAngularVelocity(angularVelocity: Float)
    fun getAngularVelocity(): Float
    fun addAngularVelocity(angularVelocity: Float)
    fun setRotation(rotation: Float)
    fun getRotation(): Float
    fun addRotation(rotation: Float)
    fun update()
}