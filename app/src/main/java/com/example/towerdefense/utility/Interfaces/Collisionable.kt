package com.example.towerdefense.utility.Interfaces

import com.example.towerdefense.Physics2d.primitives.Collider2D

interface Collisionable {
    fun collider(): Collider2D
}
