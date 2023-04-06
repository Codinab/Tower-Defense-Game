package com.example.towerdefense.utility

import com.example.towerdefense.GameView
import com.example.towerdefense.gameObjects.tower.Tower
import org.joml.Vector2f
import org.joml.Vector2i
import java.util.concurrent.atomic.AtomicInteger

var fps = false

var screenSize = Vector2i(0, 0)
var cameraPosition = Vector2f(0f, 0f)
var gameView : GameView? = null
var towerClicked : Tower? = null

var money = AtomicInteger(100)
var gameHealth = AtomicInteger(10)
