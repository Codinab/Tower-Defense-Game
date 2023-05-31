package com.example.towerdefense.bbdd.tablesClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
class GameInfo(
    @PrimaryKey
    @ColumnInfo(name = "gameName")
    val name: String,
    
    @ColumnInfo(name = "gameScore")
    val score: Int,
    
    @ColumnInfo(name = "gameDate", defaultValue = "your_default_value")
    val date: String,
    
    @ColumnInfo(name = "gameMoney", defaultValue = "your_default_value")
    val money: Int,
    
    @ColumnInfo(name = "gameRound", defaultValue = "your_default_value")
    val round: Int,
    
    @ColumnInfo(name = "gameResult",defaultValue = "your_default_value")
    val result: String
)