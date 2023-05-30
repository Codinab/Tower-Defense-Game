package com.example.towerdefense.bbdd

import androidx.annotation.WorkerThread
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import kotlinx.coroutines.flow.Flow

class MyRepository(private val myDaoIdentity: MyDaoIdentity) {
    val allGameInfo: Flow<List<GameInfo>> = myDaoIdentity.getAlphabetizedWords()
    
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(gameInfo: GameInfo) {
        myDaoIdentity.insert(gameInfo)
    }
    
    @WorkerThread
    suspend fun deleteAll() {
        myDaoIdentity.deleteAll()
    }
}