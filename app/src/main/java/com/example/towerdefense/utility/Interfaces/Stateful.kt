package com.example.towerdefense.utility.Interfaces

import java.util.concurrent.atomic.AtomicBoolean

interface Stateful {
    var movable: AtomicBoolean
    var fixable: AtomicBoolean
    companion object val layerLevel: Int
}
