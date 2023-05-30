package com.example.towerdefense.bbdd


import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.towerdefense.bbdd.tablesClasses.GameInfo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    entities = [GameInfo::class],
    version = 1
)
abstract class MyDataBase: RoomDatabase() {
    
    abstract fun myDaoIdentity(): MyDaoIdentity
    
    /*private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch{
                    val myDaoIdentity = database.myDaoIdentity()
                    
                    // Delete all content here.
                    myDaoIdentity.deleteAll()
                    
                    // Add sample gameInfo.
                    var gameInfo = GameInfo("Putin", 200)
                    myDaoIdentity.insert(gameInfo)
                    gameInfo = GameInfo("Putin coding!", 100)
                    myDaoIdentity.insert(gameInfo)
                    
                    //TODO: Add your own words!
                    gameInfo = GameInfo("TETE", 400)
                    myDaoIdentity.insert(gameInfo)
                }
            }
        }
    }
    */
    companion object{
        @Volatile
        private var INSTANCE: MyDataBase? = null
        
        fun getDatabase(context: Context): MyDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDataBase::class.java,
                    "game_database"
                )
                    //.addCallback(WordDatabaseCallback(CoroutineScope(Dispatchers.IO)))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    
        
    }
    
    
   
    
}
