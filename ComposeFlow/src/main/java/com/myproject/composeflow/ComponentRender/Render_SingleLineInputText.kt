package com.myproject.composeflow.ComponentRender

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.mapKeyBoardType
import com.myproject.composeflow.Utils.searchData
import kotlin.collections.get

@Composable
fun Render_SingleLineInputText(
    modifier: Modifier = Modifier,
    dataMap: Map<*, *>,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val inputId = dataMap["id"] as String
    val inputPadding = paddingValues(path = dataMap["padding"])
    val keyboardType =
        mapKeyBoardType(keyboardType = dataMap["keyboardType"] as String)
    val fontSize =
        (dataMap["font_size"] ?: 16).toString().toFloat().toInt()
    val fontWeight =
        mapFontWeight(fontWeight = dataMap["font_weight"] as? String)
    val suffixIcon = dataMap["suffixIcon"] as? String
    val text = if (dataMap["text"] != null) {
        dataMap["text"] as String
    } else {
        searchData(key = dataMap["\$text"] as String) as String
    }



    SingleLineInputText(
        keyboardType = keyboardType,
        fontWeight = fontWeight,
        hintText = text,
        value = value,
        onValueChange = onValueChange,
        isRequired = false,
        font_size = fontSize,
        suffixIcon = suffixIcon,
        modifier = Modifier
            .fillMaxWidth()
            .then(inputPadding)
    )
}