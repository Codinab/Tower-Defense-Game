package com.example.towerdefense

import android.graphics.Canvas
import android.view.SurfaceView

/**
 *
 * */
class GameLoop(private val game: GameView) : Thread() , java.io.Serializable {

    private val view: GameSurfaceView = game.surfaceView
    internal var isRunning = false
    private var averageUPS: Double = 0.0
    private var averageFPS: Double = 0.0

    fun averageUPS(): Double {
        return averageUPS
    }

    fun averageFPS(): Double {
        return averageFPS
    }

    fun startLoop() {
        isRunning = true
        view.isRunning = true
        start()
    }

    override fun run() {
        super.run()
        var updateCount: Int = 0
        var frameCount: Int = 0

        var startTime: Long
        var elapsedTime: Long
        var sleepTime: Long

        startTime = System.currentTimeMillis()
        //Game loop
        while (isRunning) {

            try { //Update and render
                synchronized(view.holder) {
                    game.update()
                    updateCount++
                    view.postInvalidate()
                    frameCount++
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

            //Pause to maintain target UPS
            elapsedTime = System.currentTimeMillis() - startTime
            sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

            //Skip frames to maintain target FPS
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                game.update()
                updateCount++
                elapsedTime = System.currentTimeMillis() - startTime
                sleepTime = (updateCount * UPS_PERIOD - elapsedTime).toLong()
            }

            //Calculate UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (1E-3 * elapsedTime)
                averageFPS = frameCount / (1E-3 * elapsedTime)
                updateCount = 0
                frameCount = 0
                startTime = System.currentTimeMillis()
            }
        }
    }

    fun stopLoop() {
        isRunning = false
        view.isRunning = false
    }

    companion object {
        private const val MAX_UPS = 60
        private const val UPS_PERIOD: Double = 1E+3 / MAX_UPS
    }

}
