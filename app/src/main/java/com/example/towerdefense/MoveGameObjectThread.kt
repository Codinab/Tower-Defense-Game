package com.example.towerdefense

import android.provider.SyncStateContract.Constants
import com.example.towerdefense.utility.lastTouchPosition

class MoveGameObjectThread(private val gameObject: GameObject) : Thread() {
    override fun run() {
        super.run()
        println("MoveGameObjectThread")
        while (gameObject.movable.get()) {
            gameObject.setPosition(lastTouchPosition)
        }
        if (gameObject.fixable.get()) {
            gameObject.fixable.set(false)
        }
        gameObject.semaphore.release()
    }
}