package com.example.myfinances2020.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfinances2020.repository.database.daos.TransactionDao
import com.example.myfinances2020.repository.database.entities.Transaction

@Database(entities = [Transaction::class], version = 1)
abstract class MyFinancesDatabase : RoomDatabase(){
    abstract val transactionDao: TransactionDao
}

private lateinit var INSTANCE: MyFinancesDatabase

fun getDatabase(context: Context): MyFinancesDatabase{
    synchronized(MyFinancesDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext, MyFinancesDatabase::class.java, "MyFinances2020").build()
        }
    }
    return INSTANCE
}