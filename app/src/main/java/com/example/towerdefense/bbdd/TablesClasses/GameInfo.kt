package com.example.towerdefense.bbdd.TablesClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
class GameInfo(
    @PrimaryKey
    @ColumnInfo(name = "gameName")
    val name: String,
    
    @ColumnInfo(name = "gameScore")
    val score: Int
)