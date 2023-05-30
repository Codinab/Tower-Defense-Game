package com.example.towerdefense

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.towerdefense.bbdd.MyApplication
import com.example.towerdefense.bbdd.MyListAdapter
import com.example.towerdefense.bbdd.MyViewModel
import com.example.towerdefense.bbdd.MyViewModelFactory
import com.example.towerdefense.bbdd.NewGameInfAct
import com.example.towerdefense.bbdd.TablesClasses.GameInfo
import com.example.towerdefense.databinding.ActivityMainBinding
import com.example.towerdefense.utility.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.joml.Vector2i


class MainActivity : AppCompatActivity() {
    
    lateinit var startForResult: ActivityResultLauncher<Intent>
    private val myViewModel: MyViewModel by viewModels() {
        MyViewModelFactory((application as MyApplication).repository)
    }
    private val newGameInfActivityRequestCode = 1
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Set orientation to landscape
        /*requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;*/
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        getRotation()
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        
        
        initializeButtons(binding)
        setContentView(view)
        
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show()
                }
            }
        
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mainActivity = this
    
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = MyListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        myViewModel.allGameInfo.observe(this) { gameInfo ->
            // Update the cached copy of the words in the adapter.
            gameInfo?.let { adapter.submitList(it) }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewGameInfAct::class.java)
            startForResult.launch(intent)
        }
        
    }
    
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newGameInfActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val gameInfoString = it.getStringExtra(NewGameInfAct.EXTRA_REPLY) ?: ""
                val gameInfoScore = it.getIntExtra(NewGameInfAct.EXTRA_REPLY, -1)
                
                val gameInfo = GameInfo(gameInfoString, gameInfoScore)
                myViewModel.insert(gameInfo)
                
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    
    fun getRotation() {
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
    
    fun saveWindowSizes() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        
        screenSize = Vector2i(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons(binding: ActivityMainBinding) {
        val continueButton = binding.createButton
        continueButton.setOnClickListener {
            val intent = Intent(this, OptionActivity::class.java)
            startForResult.launch(intent)
        }

        val createButton = binding.exitGame
        createButton.setOnClickListener {
            finish()
        }
    
        val helpButton = binding.help
        helpButton.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startForResult.launch(intent)
        }
        
    }
    
    private var lastClickTime: Long = 0
    
    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBackPressed() {
        //Do double back press to exit
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 400) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Press 2 times to exit", Toast.LENGTH_SHORT).show()
            lastClickTime = currentTime
        }
    }
}