package com.example.towerdefense.utility.Interfaces

import com.example.towerdefense.utility.gameLog

interface Removable {
    
    var toDelete: Boolean
    fun destroy() {
        toDelete = true
        gameLog?.addRemoveLog(this)
    }
    fun toDelete(): Boolean = toDelete
}