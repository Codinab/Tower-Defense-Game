package com.example.towerdefense.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R
import com.example.towerdefense.bbdd.MyApplication
import com.example.towerdefense.bbdd.MyViewModel
import com.example.towerdefense.bbdd.MyViewModelFactory
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import com.example.towerdefense.databinding.ActivityLogBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class LogActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    lateinit var email: String
    lateinit var dayAndHour: String
    
    private val myViewModel: MyViewModel by viewModels {
        MyViewModelFactory((application as MyApplication).repository)
    }
    
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
        
        mailSend(savedInstanceState, binding)
    }
    
    var logMss = ""
    private fun mailSend(savedInstanceState: Bundle?, binding: ActivityLogBinding) {
        emailEditText = findViewById(R.id.emailLog)
        email = "marioferro2002@gmail.com"
        
        // Get the log message from the intent extras or savedInstanceState
        val name = intent.getStringExtra("log_name")
        val money = intent.getStringExtra("log_money")
        val round = intent.getStringExtra("log_round")
        val result = intent.getStringExtra("log_result")
        
        logMss = "Game: {$name} Money: {$money} Round: {$round} Result: {$result}"
        
        if (name == null || money == null || round == null || result == null) {
            logMss = savedInstanceState?.getString(LOG_MESSAGE_KEY) ?: intent.getStringExtra("log")
                    ?: "Log message"
        }
        
        sendToDatabase(binding, logMss, name, money, round, result)
    }
    
    private fun sendToDatabase(binding: ActivityLogBinding, logMss: String, name: String?, money: String?, round: String?, result: String?) {
        binding.logMessage.setText(logMss)
        dayAndHour = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
        
        val editText = findViewById<EditText>(R.id.time)
        editText.setText(dayAndHour)
        
        
        val gameInfo =
            GameInfo(name!!, money!!.toInt(), dayAndHour, money!!.toInt(), round!!.toInt(), result!!)
        myViewModel.insert(gameInfo)
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
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email)) // recipients
            putExtra(Intent.EXTRA_SUBJECT, "Tower Defense Log")
            putExtra(Intent.EXTRA_TEXT, logMss)
        }
        
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            // No app found to handle this intent, inform user or take alternative action
            Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
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