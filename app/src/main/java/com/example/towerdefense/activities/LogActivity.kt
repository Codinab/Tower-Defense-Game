package com.example.towerdefense.activities

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
import kotlin.system.exitProcess


class LogActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    lateinit var email: String
    lateinit var dayAndHour: String
    
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
        // Get the log message from the intent extras or savedInstanceState
        val name = intent.getStringExtra("log_name")
        val money = intent.getStringExtra("log_money")
        val round = intent.getStringExtra("log_round")
        val result = intent.getStringExtra("log_result")
        
        
        var logMss = "Game: {$name} Money: {$money} Round: {$round} Result: {$result}"
        
        if (name == null || money == null || round == null || result == null) {
            logMss = savedInstanceState?.getString(LOG_MESSAGE_KEY) ?: intent.getStringExtra("log") ?: "Log message"
        }
        
        binding.logMessage.setText(logMss)
        
        
        dayAndHour = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        val editText = findViewById<EditText>(R.id.time)
        editText.setText(dayAndHour)
    }
    
    @SuppressLint("QueryPermissionsNeeded")
    private fun initializeButtons(binding: ActivityLogBinding) {
        
        binding.emailLog?.setOnClickListener {
            email = emailEditText.text.toString()
        }
        
        binding.newGameLog.setOnClickListener {
            exitProcess(0)
        }
        binding.sendLog.setOnClickListener {
            sendPredefinedMail()
        }
        binding.exitLog.setOnClickListener {
            finishAffinity()
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
        val logMessage = findViewById<EditText>(R.id.logMessage).text.toString()
        outState.putString(LOG_MESSAGE_KEY, logMessage)
    }
    
    // No need to override onRestoreInstanceState, as we already restored the log message in onCreate
}