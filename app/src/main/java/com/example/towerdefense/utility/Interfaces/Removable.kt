package com.example.towerdefense.utility.Interfaces

interface Removable {
    
    var toDelete: Boolean
    fun destroy() {
        toDelete = true
    }
    fun toDelete(): Boolean = toDelete
}