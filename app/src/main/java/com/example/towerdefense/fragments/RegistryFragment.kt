package com.example.towerdefense.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.towerdefense.R
import com.example.towerdefense.bbdd.MyApplication
import com.example.towerdefense.bbdd.MyListAdapter
import com.example.towerdefense.bbdd.MyViewModel
import com.example.towerdefense.bbdd.MyViewModelFactory
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import com.example.towerdefense.databinding.FragmentMainBinding
import com.example.towerdefense.databinding.FragmentOptionBinding
import com.example.towerdefense.databinding.FragmentRegistryBinding

class RegistryFragment : Fragment() {
    
    private val binding by lazy { FragmentRegistryBinding.inflate(layoutInflater) }
    private val myViewModel: MyViewModel by viewModels() {
        MyViewModelFactory((requireActivity().application as MyApplication).repository)
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        
    ): View {
        initializeButtons(binding)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerview
        val adapter = MyListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        myViewModel.allGameInfo.observe(viewLifecycleOwner) { gameInfo ->
            gameInfo.let { adapter.submitList(it) }
        }
        
    }
    
    @RequiresApi(Build.VERSION_CODES.R)
    private fun initializeButtons(binding: FragmentRegistryBinding) {
        
        val exitButton = binding.exitButton
        exitButton.setOnClickListener {
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