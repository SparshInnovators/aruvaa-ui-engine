package com.myproject.testingframework.model.DataManager

import com.myproject.testingframework.model.myapi.MyRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

object DataManager {
    var organizationDataList: List<Map<String, String>> = emptyList()
}