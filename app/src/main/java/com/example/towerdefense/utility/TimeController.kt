package com.example.towerdefense.utility

class TimeController {
    companion object {
        private var startTime: Long = System.currentTimeMillis()
        private var resumeTime: Long = 0L
        private var pauseTime: Long = 0L
        private var paused: Boolean = true
    
        // Returns the current game time in milliseconds
        fun getGameTime(): Long {
            val elapsed =
                if (paused) pauseTime - resumeTime else System.currentTimeMillis() - resumeTime
            return if (elapsed < 0L) 0L else elapsed * gameVelocity
        }

        // Returns the time since the app started in milliseconds
        fun getSinceAppStart(): Long {
            return System.currentTimeMillis() - startTime
        }

        // Resumes the game time
        @Synchronized
        fun resume() {
            if (paused) {
                val resumeTime = System.currentTimeMillis()
                val pauseElapsed = resumeTime - pauseTime
                this.resumeTime += pauseElapsed
                paused = false
            }
        }

        fun isPaused(): Boolean {
            return paused
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

