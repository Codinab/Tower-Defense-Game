package com.example.towerdefense.utility

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.databinding.ActivityMainBinding


class OptionActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        setContentView(R.layout.activity_option)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        

        initializeButtons(binding)
    }
    
    
    private fun initializeButtons(binding: ActivityMainBinding) {
        binding.exitGame.setOnClickListener {
            finish()
        }
    }
}
    