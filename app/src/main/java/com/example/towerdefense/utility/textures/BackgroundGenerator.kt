package com.example.towerdefense.utility.textures

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.towerdefense.R
import com.example.towerdefense.utility.gameView
import java.util.*
import kotlin.collections.ArrayList

class BackgroundGenerator(val context: Context) {
    
    private val grass = mutableListOf<Bitmap>()
    private val grassSize = 128
    
    init {
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_1)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_2)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_3)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_4)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_5)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_6)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_7)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass1_8)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_1)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_2)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_3)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_4)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_5)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_6)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_7)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass2_8)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_1)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_2)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_3)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_4)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_5)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_6)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_7)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass3_8)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_1)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_2)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_3)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_4)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_5)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_6)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_7)!!.toBitmap())
        grass.add(ContextCompat.getDrawable(context, R.drawable.grass4_8)!!.toBitmap())
    }
    
    fun generateBackground(bitmapsPerRow: Int, bitmapsPerColumn: Int): Bitmap {
        val resultWidth = bitmapsPerRow * grassSize
        val resultHeight = bitmapsPerColumn * grassSize
        val resultBitmap = Bitmap.createBitmap(resultWidth, resultHeight, Bitmap.Config.ARGB_8888)
        
        val canvas = Canvas(resultBitmap)
        val random = Random()
        
        for (row in 0 until bitmapsPerRow) {
            for (column in 0 until bitmapsPerColumn) {
                val randomIndex = random.nextInt(grass.size)
                val sourceBitmap = grass[randomIndex]
                val sourceRect = Rect(0, 0, sourceBitmap.width, sourceBitmap.height)
                val destinationRect = Rect(row * grassSize, column * grassSize, (row + 1) * grassSize, (column + 1) * grassSize)
                canvas.drawBitmap(sourceBitmap, sourceRect, destinationRect, null)
            }
        }
        
        return resultBitmap
    }
    
    
}