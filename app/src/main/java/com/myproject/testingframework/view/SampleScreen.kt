package com.myproject.testingframework.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myproject.composeflow.Utils.JsonParsing.ParseJsonString
import com.myproject.testingframework.viewmodel.MyViewModel

@Composable
fun SampleScreen(modifier: Modifier = Modifier) {

    //viewmodel
    val myVm: MyViewModel = hiltViewModel()

    LaunchedEffect("01_login") {
        myVm.fetchJsonData(screenId = "01_login")
    }

    val data = myVm.fetchedJsonData.observeAsState()
    val uiData = data.value?.jsonData?.takeIf { it.isNotEmpty() }?.let { json ->
        ParseJsonString(json)
    } ?: emptyMap<Any, Any>()

    val name = uiData["ScreenName"] as? String ?: "Unknown Screen"
    val screenId = uiData["ScreenId"] as? String ?: "Unknown ID"
    val layoutData = uiData["layout"] as? Map<*, *> ?: emptyMap<Any, Any>()

    val layoutType = layoutData["type"] as? String ?: "Unknown Type"
    val layoutChildren = (layoutData["children"] as? List<*>) ?: emptyList<Any>()


    Column(modifier) {
        Text(layoutData.toString())
    }
}