package com.example.myfinances2020

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myfinances2020.data.database.MyFinancesDatabase
import com.example.myfinances2020.data.database.daos.TransactionDao
import com.example.myfinances2020.data.database.entities.Transaction
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest{

    private lateinit var transactionDao: TransactionDao
    private lateinit var database: MyFinancesDatabase

    @Before
    fun createDb(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        database = Room.inMemoryDatabaseBuilder(context, MyFinancesDatabase::class.java).allowMainThreadQueries().build()
        transactionDao = database.transactionDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetTransaction() {
        Log.d("TEST", "init test")
        val transaction = Transaction()
        transactionDao.insert(transaction)

        val t = transactionDao.getAllTransactions()

        t.value?.forEach { Log.d("TEST", it.toString()) }
    }
}