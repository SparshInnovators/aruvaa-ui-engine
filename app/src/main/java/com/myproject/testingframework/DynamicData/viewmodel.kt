package com.myproject.testingframework.DynamicData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.testingframework.DynamicData.repository.repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class viewmodel @Inject constructor(private val repository: repository) : ViewModel() {
    val quote = repository.quote

    init {
        viewModelScope.launch {
            repository.fetchQuotes()
        }
    }

}