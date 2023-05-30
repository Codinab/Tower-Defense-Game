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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.towerdefense.databinding.ActivityMainBinding
import com.example.towerdefense.utility.*
import org.joml.Vector2i

class MainActivity : AppCompatActivity() {
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        // Hide action bar
        supportActionBar?.hide()
        
        // New fullscreen method only for Android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
    }
    
    private var lastClickTime: Long = 0
    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ShowToast") // Suppressing the toast warning
    override fun onBackPressed() {
        // Do double back press to exit
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime < 400) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "Press 2 times to exit", Toast.LENGTH_SHORT).show()
            lastClickTime = currentTime
        }
    }
}
