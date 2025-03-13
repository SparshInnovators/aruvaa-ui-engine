package com.myproject.testingframework.DynamicData.repository

import android.util.Log
import com.myproject.testingframework.DynamicData.api.quotesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class repository @Inject constructor(private val quotesApi: quotesApi) {

    private val _quote = MutableStateFlow<Map<String, Any>>(emptyMap())
    val quote = _quote

    suspend fun fetchQuotes() {
        val response = quotesApi.getSingleQuote()
        if (response.isSuccessful) {
            val quoteList = response.body()?.first()
            Log.d("Ankit Raj", "fetchQuotes: $quoteList")
            if (quoteList != null) {
                _quote.emit(quoteList)
            }
        }
    }
}