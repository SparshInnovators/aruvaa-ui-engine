package com.myproject.testingframework.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.testingframework.model.database.DataEntity
import com.myproject.testingframework.model.database.Repository.DbRepository
import com.myproject.testingframework.model.myapi.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repo: DbRepository) : ViewModel() {

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
            val data = repo.fetchJsonDataFromScreenId(screenId)
            data?.let { _jsonData.postValue(it) }
        }
    }
}