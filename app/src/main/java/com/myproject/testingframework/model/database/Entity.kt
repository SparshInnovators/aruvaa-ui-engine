package com.myproject.testingframework.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "data_table")
data class DataEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val ScreenId: String,
    val jsonData: String
)
