package com.example.towerdefense.bbdd

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    val dataBase by lazy { MyDataBase.getDatabase(this)  }
    val repository by lazy { MyRepository(dataBase.myDaoIdentity()) }
}