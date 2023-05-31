package com.example.towerdefense.bbdd


import android.content.Context
import androidx.room.AutoMigration

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.towerdefense.bbdd.tablesClasses.GameInfo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(
    version = 2,
    entities = [GameInfo::class],
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class MyDataBase : RoomDatabase() {
    
    abstract fun myDaoIdentity(): MyDaoIdentity
    
    companion object {
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
