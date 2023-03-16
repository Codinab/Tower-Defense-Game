package com.example.towerdefense

import android.graphics.Canvas
import android.view.SurfaceView

/**
 *
 * */
class GameLoop(game: GameView, view: SurfaceView) : Thread() , java.io.Serializable {

    private val view: SurfaceView
    private val game: GameView
    internal var isRunning = false
    private var averageUPS: Double = 0.0
    private var averageFPS: Double = 0.0

    init {
        this.view = view
        this.game = game
    }

    fun averageUPS(): Double {
        return averageUPS
    }

    fun averageFPS(): Double {
        return averageFPS
    }

    fun startLoop() {
        isRunning = true
        start()
    }

    override fun run() {
        super.run()

        println("Valid?: " + view.holder.surface.isValid)
        println(view.holder.surface)

        var updateCount: Int = 0
        var frameCount: Int = 0

        var startTime: Long
        var elapsedTime: Long
        var sleepTime: Long

        var canvas: Canvas? = null
        startTime = System.currentTimeMillis()
        //Game loop
        while (isRunning) {

            println("GameLoop: run()")
            try { //Update and render
                canvas = view.holder.lockCanvas(null)
                synchronized(view.holder) {
                    game.update()
                    updateCount++
                    game.draw(canvas)
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        view.holder.unlockCanvasAndPost(canvas)
                        frameCount++
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    println("GameLoop: canvas is null")
                }
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

    fun setRunning(b: Boolean) {
        isRunning = b
    }

    companion object {
        private const val MAX_UPS = 60
        private const val UPS_PERIOD: Double = 1E+3 / MAX_UPS
    }

}
