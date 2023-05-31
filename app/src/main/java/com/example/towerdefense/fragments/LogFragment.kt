package com.example.towerdefense.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.towerdefense.databinding.FragmentLogBinding

class LogFragment : Fragment() {
    
    
    var binding: FragmentLogBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        
        binding = FragmentLogBinding.inflate(layoutInflater)
        return binding!!.root
    }
    
    fun addLog(log: String) {
        if (binding?.logLinearLayout == null) return
        try {
            activity?.runOnUiThread {
                binding?.logLinearLayout?.addView(TextView(requireContext()).apply {
                    layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    text = log
                }.also { binding!!.logScrollView.post { binding!!.logScrollView.fullScroll(View.FOCUS_DOWN) } })
                // Scroll to bottom
            }
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        
    }
    
    
}