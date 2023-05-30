package com.example.towerdefense.bbdd

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val dataBase by lazy { MyDataBase.getDatabase(this, applicationScope)  }
    val repository by lazy { MyRepository(dataBase.myDaoIdentity()) }
}