package com.example.towerdefense.fragments

import android.R
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.towerdefense.activities.GameActivity
import com.example.towerdefense.databinding.FragmentOptionBinding
import com.example.towerdefense.utility.screenSize
import com.example.towerdefense.utility.selectedMap
import org.joml.Vector2i

class OptionFragment : Fragment() {
    
    private lateinit var binding: FragmentOptionBinding
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOptionBinding.inflate(inflater, container, false)
        
        val mapArray: Array<String> =  Roads.values().map { it.name }.toTypedArray()
        setupSpinner(mapArray)
        
        initializeButtons()
        
        return binding.root
    }
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons() {
        binding.easyButton.setOnClickListener {
            difficulty = 1
        }
        binding.mediumButton.setOnClickListener {
            difficulty = 2
        }
        binding.hardButton.setOnClickListener {
            difficulty = 4
        }
        
        binding.x1.setOnClickListener {
            enemiesSpeed = 0f
            println(enemiesSpeed)
        }
        
        binding.x2.setOnClickListener {
            enemiesSpeed = 4f
            println(enemiesSpeed)
        }
        
        binding.x3.setOnClickListener {
            enemiesSpeed = 8f
            println(enemiesSpeed)
        }
        
        binding.nombreEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }
            
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                gameName = s.toString()
            }
            
            override fun afterTextChanged(s: android.text.Editable) {
                // Do nothing
            }
        })
        
        binding.noTime.setOnClickListener {
            maxTime = -100
        }
        binding.yesTime.setOnClickListener {
            maxTime = 240
        }
        
        binding.newGame.setOnClickListener {
            if (difficulty == -1 || enemiesSpeed == -1f || maxTime == -1 || gameName == "") {
                return@setOnClickListener
            }
            val intent = Intent(requireContext(), GameActivity::class.java)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("enemiesSpeed", enemiesSpeed)
            intent.putExtra("maxTime", maxTime)
            intent.putExtra("gameName", gameName)
            startActivity(intent)
            
            
        }
        
        binding.exitOptions.setOnClickListener {
            requireActivity().finish()
        }
    }
    
    private var difficulty: Int = -1
    private var enemiesSpeed: Float = -1f
    private var maxTime: Int = -1
    private var gameName: String = ""
    
    private fun setupSpinner(mapArray: Array<String>) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, mapArray)
        
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        // Apply the adapter to the spinner
        binding.spinnerMap.adapter = adapter
        
        // Set an onItemSelectedListener for the spinner
        binding.spinnerMap.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedMap = Roads.fromString(mapArray[position])
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedMap = Roads.fromString(mapArray[0])
            }
        }
        
        binding.spinnerMap.invalidate()
        binding.spinnerMap.requestLayout()
    }
}
