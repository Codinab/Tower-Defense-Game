package com.example.towerdefense.utility

import com.example.towerdefense.GameView
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.atan2

var fps = false

var screenSize = Vector2i(0, 0)
var cameraPosition = Vector2f(0f, 0f)
var gameView : GameView? = null
var towerClicked : Tower? = null

var money = AtomicInteger(100000)
var gameHealth = AtomicInteger(1000)

