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
        
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editGameInfoView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val gameInf = editGameInfoView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, gameInf)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
            
        }
    }
    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }
}