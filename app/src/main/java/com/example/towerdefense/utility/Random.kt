package com.example.towerdefense.utility

class Random {
    companion object {

        fun randomInt(min: Int, max: Int, seed: Int): Int {
            return (randomFloat(min.toFloat(), max.toFloat(), seed) + 0.5).toInt()
        }
        fun randomInt(min: Int, max: Int): Int {
            return (randomFloat(min.toFloat(), max.toFloat()) + 0.5).toInt()
        }

        fun randomFloat(min: Float, max: Float, seed: Int): Float {
            val random = java.util.Random(seed.toLong())
            return min + (random.nextFloat() * (max - min))
        }
        fun randomFloat(min: Float, max: Float): Float {
            val random = java.util.Random()
            return min + (random.nextFloat() * (max - min))
        }

    }
}