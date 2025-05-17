package com.myproject.testingframework.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myproject.testingframework.model.database.Dao.MyDao

@Database(entities = [DataEntity::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): MyDao
}