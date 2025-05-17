package com.myproject.testingframework.model.database.Repository

import com.myproject.testingframework.model.database.Dao.MyDao
import com.myproject.testingframework.model.database.DataEntity
import javax.inject.Inject

class DbRepository @Inject constructor(private val dao: MyDao) {
    suspend fun insertJsonData(data: DataEntity) {
        val existingData = dao.fetchJsonDataFromScreenId(data.ScreenId)
        if (existingData == null) {
            dao.insertJsonData(data)
        }
    }

    suspend fun fetchJsonDataFromScreenId(screenId: String): DataEntity? {
        return dao.fetchJsonDataFromScreenId(screenId)
    }

}