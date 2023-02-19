package com.example.towerdefense.utility.Interfaces

import android.view.MotionEvent
import org.joml.Vector2f

interface InputEvent {
    var lastClickTime: Long
    fun onTouchEvent(event: MotionEvent, position: Vector2f): Boolean
    fun isClicked(position: Vector2f?): Boolean
}
