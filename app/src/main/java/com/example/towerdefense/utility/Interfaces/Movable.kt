package com.example.towerdefense.utility.Interfaces

import java.util.Objects

interface Movable : Collisionable, Removable, Updatable {
    fun addVelocity(velocity: Float) {
        collider.body.velocity += velocity
    }
    
    fun velocity(): Float {
        return collider.body.velocity
    }
    
    fun velocity(velocity: Float) {
        collider.body.velocity = velocity
    }
    
    fun setAngularVelocity(angularVelocity: Float) {
        collider.body.angularVelocity = angularVelocity
    }
    
    fun getAngularVelocity(): Float {
        return collider.body.angularVelocity
    }
    
    fun addAngularVelocity(angularVelocity: Float) {
        collider.body.angularVelocity += angularVelocity
    }
    
    fun setRotation(rotation: Float) {
        collider.body.rotation = rotation
    }
    
    fun getRotation(): Float {
        return collider.body.rotation
    }
    
    fun addRotation(rotation: Float) {
        collider.body.rotation += rotation
    }
    
    override fun update() {
        if (toDelete()) return
        collider.update()
    }
}