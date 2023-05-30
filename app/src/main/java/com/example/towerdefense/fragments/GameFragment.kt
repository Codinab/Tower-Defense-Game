package com.example.towerdefense.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.views.GameView

class GameFragment(val name: String = "DefaultGame", val difficulty: Int, val enemySpeed: Int, val maxTime: Int) : Fragment() {
    
    private var gameView: GameView? = null
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return GameView(requireContext(), name, difficulty, enemySpeed, maxTime).also { gameView = it }
    }
}
