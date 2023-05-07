package com.example.towerdefense.utility

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.databinding.ActivityLogBinding
import java.text.SimpleDateFormat
import java.util.*


class LogActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    lateinit var email: String
    
    // Add a constant for the saved instance state key
    companion object {
        private const val LOG_MESSAGE_KEY = "log_message_key"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Hide title bar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        
        //Hide action bar
        supportActionBar?.hide()
        
        //New fullscreen method only for android 11
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        
        val binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeButtons(binding)
        
        emailEditText = findViewById(R.id.emailLog)
        email = "marioferro2002@gmail.com"
        emailEditText.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()))
        // Get the log message from the intent extras or savedInstanceState
        val logMessage = savedInstanceState?.getString(LOG_MESSAGE_KEY) ?: intent.getStringExtra("logMessage") ?: "Log message"
        binding.editTextText2.setText(logMessage)
    }
    
    @SuppressLint("QueryPermissionsNeeded")
    private fun initializeButtons(binding: ActivityLogBinding) {
        
        binding.emailLog?.setOnClickListener {
            email = emailEditText.text.toString()
        }
        
        binding.newGameLog.setOnClickListener {
            //createNewGame
        }
        binding.sendLog.setOnClickListener {
            sendPredefinedMail()
        }
        binding.exitLog.setOnClickListener {
            finish()
        }
    }
    
    private fun sendPredefinedMail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data =
            Uri.parse("mailto:$email") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Tower Defense Log")
        intent.putExtra(Intent.EXTRA_TEXT, "test")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
    
    // Override onSaveInstanceState to save the log message
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val logMessage = findViewById<EditText>(R.id.editTextText2).text.toString()
        outState.putString(LOG_MESSAGE_KEY, logMessage)
    }
    
    // No need to override onRestoreInstanceState, as we already restored the log message in onCreate
}