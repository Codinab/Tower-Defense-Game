package com.example.towerdefense.utility

import android.os.Parcelable
import com.example.towerdefense.GameView
import com.example.towerdefense.MainActivity
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.atan2

var fps = false

var screenSize = Vector2i(0, 0)
var screenRotation = 0f
var cameraPosition = Vector2f(0f, 0f)
var gameView : GameView? = null
var towerClicked : Tower? = null

var gameLog: Log? = null

var money = AtomicInteger(1000)
var gameHealth = AtomicInteger(20)
var maxTime = 300
var round = 20

var difficulty = 1
var enemiesSpeed = 0f

var restoreGame: Parcelable? = null
var restoreSurfaceView: Parcelable? = null

var mainActivity: MainActivity? = null
var selectedMap: Road? = null

