package com.example.towerdefense.utility

import android.os.Parcelable
import com.example.towerdefense.views.GameView
import com.example.towerdefense.activities.MainActivity
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger

var fps = false

var towerClicked : Tower? = null

var gameLog: Log? = null

var restoreGame: Parcelable? = null
var restoreSurfaceView: Parcelable? = null

var mainActivity: MainActivity? = null
var selectedMap: Road? = null

