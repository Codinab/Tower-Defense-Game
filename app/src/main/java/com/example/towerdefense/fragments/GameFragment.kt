package com.example.towerdefense.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.views.GameView

class GameFragment : Fragment() {
    
    private var gameView: GameView? = null
    fun getGameView(): GameView? {
        return gameView
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val name = (context as GameActivity).getGameName()
        val difficulty = (context as GameActivity).getDifficulty()
        val enemySpeed = (context as GameActivity).getEnemiesSpeed()
        val maxTime = (context as GameActivity).getMaxTime()
        return GameView(requireContext() as GameActivity, name, difficulty, enemySpeed, maxTime).also { gameView = it }
    }
}

