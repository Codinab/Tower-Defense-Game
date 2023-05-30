package com.example.towerdefense.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.databinding.FragmentMainBinding
import com.example.towerdefense.utility.*
import org.joml.Vector2i

class MainFragment : Fragment() {
    
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        
        
        initializeButtons(binding)
        
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(requireContext(), "Result OK", Toast.LENGTH_SHORT).show()
                }
            }
        
        
        
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        return view
    }
    
    private fun getRotation() {
        // Get the window manager
        val windowManager = activity?.windowManager
        
        // Get the rotation of the window
        val rotation = windowManager?.defaultDisplay?.rotation
        
        saveWindowSizes()
        
        // Update screenSize depending on rotation
        when (rotation) {
            Surface.ROTATION_0 -> {
                if (screenRotation != 0f) {
                    screenRotation = 0f
                    gameLog?.addScreenRotatedLog(0f)
                }
            }
            Surface.ROTATION_180 -> {
                if (screenRotation != 180f) {
                    screenRotation = 180f
                    gameLog?.addScreenRotatedLog(180f)
                }
            }
            Surface.ROTATION_90 -> {
                // Landscape
                if (screenRotation != 90f) {
                    screenRotation = 90f
                    gameLog?.addScreenRotatedLog(90f)
                }
            }
            Surface.ROTATION_270 -> {
                // Reversed landscape
                if (screenRotation != 270f) {
                    screenRotation = 270f
                    gameLog?.addScreenRotatedLog(270f)
                }
            }
            else -> {
                screenRotation = 0f
            }
        }
    }
    
    private fun saveWindowSizes() {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.let {
            it.getMetrics(displayMetrics)
            screenSize = Vector2i(displayMetrics.widthPixels, displayMetrics.heightPixels)
        }
        
    }
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons(binding: FragmentMainBinding) {
        val createButton = binding.createButton
        createButton.setOnClickListener {
            val fragment = OptionFragment()
            replaceFragment(fragment)
        }
        
        val exitButton = binding.exitGame
        exitButton.setOnClickListener {
            activity?.finish()
        }
        
        val helpButton = binding.help
        helpButton.setOnClickListener {
            val fragment = HelpFragment()
            replaceFragment(fragment)
        }
    }
    
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            addToBackStack(null)
            commit()
        }
    }
    
}