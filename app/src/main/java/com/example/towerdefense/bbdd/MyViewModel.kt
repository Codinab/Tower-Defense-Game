package com.example.towerdefense.bbdd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.towerdefense.bbdd.TablesClasses.GameInfo
import kotlinx.coroutines.launch




class MyViewModel(private val repository: MyRepository) : ViewModel(){
    val allGameInfo: LiveData<List<GameInfo>> = repository.allGameInfo.asLiveData()
    
    fun insert(gameInfo: GameInfo) = viewModelScope.launch{
        repository.insert(gameInfo)
    }
}

class MyViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
