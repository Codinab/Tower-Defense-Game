package com.example.towerdefense.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.databinding.ActivityHelpBinding


class HelpActivity : AppCompatActivity() {
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        
        val binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        
        initializeButtons(binding)
        
        
    }
    
    
    @SuppressLint("QueryPermissionsNeeded")
    private fun initializeButtons(binding: ActivityHelpBinding) {
        
        binding.exitHelp.setOnClickListener {
            finish()
        }
        
    }
}