package com.myproject.testingframework.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myproject.testingframework.CallLogsService
import com.myproject.testingframework.model.database.DataEntity
import com.myproject.testingframework.model.database.Repository.DbRepository
import com.myproject.testingframework.model.myapi.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repo: DbRepository,
    private val repo2: MyRepository,
    private val context: Context
) : BaseViewModel() {

    //example
    var storedIndex :Int = 0

    //data from external api source
    val organizationList = repo2.organizationList

    init {
        viewModelScope.launch {
            repo2.fetchOrganizationData()
        }
    }

    //Form Field Values
    private val _ScreenData = MutableStateFlow(mutableMapOf<String, MutableMap<String, String>>())
    val ScreenData: StateFlow<Map<String, Map<String, String>>> = _ScreenData
    val formData = MutableStateFlow(mutableMapOf<String, MutableMap<String, String>>())

    fun updateField(screenId: String, fieldId: String, value: String) {
        viewModelScope.launch {
            val tempData = formData.value.toMutableMap()
            val screenTempData = tempData[screenId]?.toMutableMap() ?: mutableMapOf()
            screenTempData[fieldId] = value
            tempData[screenId] = screenTempData
            formData.value = tempData
        }
    }

    fun saveFormData(screenId: String) {
        viewModelScope.launch {
            val formScreenData = formData.value[screenId] ?: return@launch

            // Save form data permanently into ScreenData
            val permanentData = _ScreenData.value.toMutableMap()
            val updatedScreenData = permanentData[screenId]?.toMutableMap() ?: mutableMapOf()
            updatedScreenData.putAll(formScreenData)
            permanentData[screenId] = updatedScreenData
            _ScreenData.value = permanentData

            // Clear formData for that screen (reset fields when navigating back)
            val tempData = formData.value.toMutableMap()
            tempData.remove(screenId)
            formData.value = tempData
        }
    }

    fun getFieldValue(screenId: String, fieldId: String): String {
        return _ScreenData.value[screenId]?.get(fieldId) ?: "empty"
    }


    //Room DB
    private val _jsonData = MutableLiveData<DataEntity>()
    val fetchedJsonData: LiveData<DataEntity> get() = _jsonData

    fun fetchJsonData(screenId: String) {
        viewModelScope.launch {
//            showLoading()
//            delay(3000)
            val data = repo.fetchJsonDataFromScreenId(screenId)
            data?.let { _jsonData.postValue(it) }
//            hideLoading()
        }
    }

    private val _secondaryJsonData = MutableLiveData<DataEntity?>()
    val secondaryJsonData: LiveData<DataEntity?> get() = _secondaryJsonData

    fun fetchSecondaryJsonData(screenId: String) {
        _secondaryJsonData.postValue(null)
        viewModelScope.launch {
//            showLoading()
//            delay(3000)
            val data = repo.fetchJsonDataFromScreenId(screenId)
            data?.let { _secondaryJsonData.postValue(it) }
//            hideLoading()
        }
    }

//    init {
//        viewModelScope.launch {
//            repo.insertJsonData(
//                data = DataEntity(
//                    id = ,
//                    ScreenId = "",
//                    jsonData = ""
//                )
//            )
//        }
//    }

    //Service - notification
    @RequiresApi(Build.VERSION_CODES.O)
    fun startForegroundService() {
        val serviceIntent = Intent(context, CallLogsService::class.java)
        context.startForegroundService(serviceIntent)
    }

    fun stopForegroundService() {
        val serviceIntent = Intent(context, CallLogsService::class.java)
        context.stopService(serviceIntent)
    }


    //extract jsondata


}