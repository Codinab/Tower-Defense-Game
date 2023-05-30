package com.example.towerdefense.bbdd

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.towerdefense.R

class NewGameInfAct: AppCompatActivity() {
    private lateinit var editGameInfoView: EditText
    private lateinit var editGameInfoScoreView: EditText
    
    
    
    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newgameinfo)
        editGameInfoView = findViewById(R.id.edit_game_info)
        editGameInfoScoreView = findViewById(R.id.edit_game_info_score)
        
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGameInfoView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val gameInf = editGameInfoView.text.toString()
                val gameScore = editGameInfoScoreView.text.toString() // Add this line to get the game score
                replyIntent.putExtra(EXTRA_REPLY, gameInf)
                replyIntent.putExtra(EXTRA_REPLY, gameScore) // Add this line to include the game score in the result
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}