package com.myproject.testingframework.mvvm_Arc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.testingframework.mvvm_Arc.model.DataManager.DataManager
import com.myproject.testingframework.mvvm_Arc.model.myapi.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: MyRepository,
) : ViewModel() {
    val organizationList = repository.organizationList

    init {
        viewModelScope.launch {
            repository.fetchOrganizationData()
        }
    }
}