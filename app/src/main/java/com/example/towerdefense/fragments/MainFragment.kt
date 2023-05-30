package com.example.towerdefense.fragments

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.databinding.FragmentMainBinding
import com.example.towerdefense.utility.*

class MainFragment : Fragment() {
    
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        
        
        initializeButtons(binding)
        
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Toast.makeText(requireContext(), "Result OK", Toast.LENGTH_SHORT).show()
                }
            }
        
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        return view
    }
    
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons(binding: FragmentMainBinding) {
        val createButton = binding.createButton
        createButton.setOnClickListener {
            val fragment = OptionFragment()
            replaceFragment(fragment)
        }
        
        val exitButton = binding.exitGame
        exitButton.setOnClickListener {
            activity?.finish()
        }
        
        val helpButton = binding.help
        helpButton.setOnClickListener {
            val fragment = HelpFragment()
            replaceFragment(fragment)
        }
    }
    
    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerViewMain, fragment)
            addToBackStack(null)
            commit()
        }
    }
    
}