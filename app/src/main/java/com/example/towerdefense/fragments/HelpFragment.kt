package com.example.towerdefense.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.towerdefense.R
import com.example.towerdefense.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        
        val binding = FragmentHelpBinding.inflate(layoutInflater)
        initializeButtons(binding)
        
        return binding.root
    }
    
    @SuppressLint("QueryPermissionsNeeded")
    private fun initializeButtons(binding: FragmentHelpBinding) {
        
        binding.exitHelp.setOnClickListener {
            val fragment = MainFragment()
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
