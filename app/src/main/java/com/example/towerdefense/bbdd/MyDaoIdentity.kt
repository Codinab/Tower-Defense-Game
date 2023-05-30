package com.example.towerdefense.bbdd
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.towerdefense.bbdd.MyDataBase.*
import com.example.towerdefense.bbdd.tablesClasses.GameInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDaoIdentity {
    
    @Query ("SELECT * FROM game_table ORDER BY gameName ASC")
    fun getAlphabetizedWords(): Flow<List<GameInfo>>
    
    
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gameInfo: GameInfo)
    
    @Query ("DELETE FROM game_table")
    suspend fun deleteAll(): Int
    
    
}