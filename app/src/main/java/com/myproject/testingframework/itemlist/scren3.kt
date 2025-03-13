package com.myproject.testingframework.itemlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.testingframework.Authentication.formdata
import com.myproject.testingframework.parseJsonToKotlin

@Composable
fun Screen3(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val template = parseJsonToKotlin(data = formdata)[0]
    val content = parseJsonToKotlin(data = formdata)[1]

    val name = template["name"]
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])

    val templateItems = template["items"] as List<Map<*, *>>
    val contentItems = content["items"] as List<Map<*, *>>


}