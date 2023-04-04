package com.example.towerdefense.utility

class TimeController {
    companion object {
        private var startTime: Long = 0L
        private var pauseTime: Long = 0L
        private var paused: Boolean = true

        // Returns the current game time in milliseconds
        fun getGameTime(): Long {
            val elapsed =
                if (paused) pauseTime - startTime else System.currentTimeMillis() - startTime
            return if (elapsed < 0L) 0L else elapsed
        }

        // Resumes the game time
        @Synchronized
        fun resume() {
            if (paused) {
                val resumeTime = System.currentTimeMillis()
                val pauseElapsed = resumeTime - pauseTime
                startTime += pauseElapsed
                paused = false
            }
        }

        // Pauses the game time
        @Synchronized
        fun pause() {
            if (!paused) {
                pauseTime = System.currentTimeMillis()
                paused = true
            }
        }
    }
}

