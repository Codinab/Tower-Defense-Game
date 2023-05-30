package com.example.towerdefense.activities

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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towerdefense.R
import com.example.towerdefense.bbdd.*
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import com.example.towerdefense.databinding.ActivityMainBinding
import com.example.towerdefense.utility.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.joml.Vector2i

class MainActivity : AppCompatActivity() {
    
    private var startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.let {
                val gameInfoString = it.data?.getStringExtra(NewGameInfAct.EXTRA_REPLY) ?: ""
                val gameInfo = GameInfo(gameInfoString, 20)
                println("InfoString: $gameInfoString")
                Toast.makeText(applicationContext, gameInfoString, Toast.LENGTH_LONG).show()
                myViewModel.insert(gameInfo)
            
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
            
            println(result)
        }
    }
    private val myViewModel: MyViewModel by viewModels() {
        MyViewModelFactory((application as MyApplication).repository)
    }
    
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
        
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mainActivity = this
        
        val recyclerView = binding.recyclerview
        val adapter = MyListAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(this)
        
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
    
    private var lastClickTime: Long = 0
    
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
