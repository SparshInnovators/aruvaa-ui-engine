package com.myproject.testingframework

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import com.myproject.testingframework.model.DataManager.DataManager
import com.myproject.testingframework.model.myapi.MyRepository
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class myapp() : Application() {

    @Inject
    lateinit var repository: MyRepository

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            repository.fetchOrganizationData()
        }
    }
}