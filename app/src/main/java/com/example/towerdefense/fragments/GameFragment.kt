package com.example.towerdefense.fragments

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.views.GameView

class GameFragment : Fragment() {
    
    private var gameView: GameView? = null
    fun getGameView(): GameView? {
        return gameView
    }
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val name = (context as GameActivity).getGameName()
        val difficulty = (context as GameActivity).getDifficulty()
        val enemySpeed = (context as GameActivity).getEnemiesSpeed()
        val maxTime = (context as GameActivity).getMaxTime()
        
        gameView = GameView(requireContext() as GameActivity, name, difficulty, enemySpeed, maxTime)
        (context as GameActivity).saveGameView(gameView!!)
        return gameView!!
    }
    
    override fun onDestroy() {
        super.onDestroy()
        gameView?.stop()
        (context as GameActivity).saveGameView(gameView!!)
    }
    
    
    fun setGameView(gameView: GameView) {
        this.gameView = gameView
    }
}

