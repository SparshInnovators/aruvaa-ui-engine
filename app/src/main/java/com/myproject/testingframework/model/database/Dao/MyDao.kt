package com.myproject.testingframework.model.database.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myproject.testingframework.model.database.DataEntity

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertJsonData(data:DataEntity)

    @Query("SELECT ScreenId FROM data_table")
    suspend fun getAllStoredScreenIds(): List<String>

    @Query("SELECT * FROM data_table WHERE ScreenId = :screenId")
    suspend fun fetchJsonDataFromScreenId(screenId:String):DataEntity?
}