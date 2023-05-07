package com.example.towerdefense.utility

import Roads
import android.os.Bundle
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.Surface
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.GameView
import com.example.towerdefense.databinding.ActivityOptionBinding
import org.joml.Vector2i


class OptionActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    
        val binding = ActivityOptionBinding.inflate(layoutInflater)
        
        val mapArray: Array<String> =  Roads.values().map { it.name }.toTypedArray()
        setupSpinner(binding, mapArray)
        
        initializeButtons(binding)
        
        setContentView(binding.root)
    }
    
    
    private fun initializeButtons(binding: ActivityOptionBinding) {
        /*binding.difficultyGroup.setOnClickListener {
            when (binding.difficultyGroup.checkedRadioButtonId) {
                R.id.easy_button -> difficulty = 1
                R.id.medium_button -> difficulty = 2
                R.id.hard_button -> difficulty = 4
            }
        }*/
        
        binding.easyButton.setOnClickListener {
            difficulty = 1
        }
        binding.mediumButton.setOnClickListener {
            difficulty = 2
        }
        binding.hardButton.setOnClickListener {
            difficulty = 4
        }
        
        /*binding.velocityGroup.setOnClickListener {
            when (binding.velocityGroup.checkedRadioButtonId) {
                R.id.x1 -> enemiesSpeed = 0f
                R.id.x2 -> enemiesSpeed = 1f
                R.id.x3 -> enemiesSpeed = 2f
            }
            println(enemiesSpeed)
        }*/
        
        binding.x1.setOnClickListener {
            enemiesSpeed = 0f
            println(enemiesSpeed)
        }
        
        binding.x2.setOnClickListener {
            enemiesSpeed = 4f
            println(enemiesSpeed)
    
        }
        
        binding.x3.setOnClickListener {
            enemiesSpeed = 8f
            println(enemiesSpeed)
    
        }
        
        /*binding.timeGroup.setOnClickListener {
            when (binding.timeGroup.checkedRadioButtonId) {
                R.id.no_time -> maxTime = -100
                R.id.yes_time -> maxTime = 240
            }
        }*/
    
        binding.nombreEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing or add code if needed
            }
    
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                gameName = s.toString()
            }
    
            override fun afterTextChanged(s: android.text.Editable) {
                // Do nothing or add code if needed
            }
        })
        
        
        
        binding.noTime.setOnClickListener {
            maxTime = -100
        }
        binding.yesTime.setOnClickListener {
            maxTime = 240
        }
        
        
        binding.newGame.setOnClickListener {
            val view = GameView(this, gameName)
            setContentView(view)
            gameView = view
        }
        
        binding.exitOptions.setOnClickListener {
            finish()
        }
        
    }
    
    fun rotation() {
        // Get the window manager
        val windowManager = windowManager
        
        
        // Get the rotation of the window
        val rotation = windowManager.defaultDisplay.rotation
        
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
    
    private var gameName: String = "DefaultGame"
    
    fun saveWindowSizes() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        
        screenSize = Vector2i(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
    
    private fun setupSpinner(binding: ActivityOptionBinding, mapArray: Array<String>) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mapArray)
        
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        // Apply the adapter to the spinner
        binding.spinnerMap?.adapter = adapter
        
        // Set an onItemSelectedListener for the spinner
        binding.spinnerMap?.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMap = Roads.fromString(mapArray[position])
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedMap = Roads.fromString(mapArray[0])
            }
        })
        
        binding.spinnerMap?.invalidate()
        binding.spinnerMap?.requestLayout()
    }
    
}
    