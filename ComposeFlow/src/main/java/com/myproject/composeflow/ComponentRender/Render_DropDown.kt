package com.myproject.composeflow.ComponentRender

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.searchData
import kotlin.collections.get

@Composable
fun Render_DropDown(modifier: Modifier = Modifier, dataMap: Map<*, *>) {
    val dropdownId =
        dataMap["id"] as String
    val dropdownPadding =
        paddingValues(
            path = dataMap["padding"]
        )
    val fontSize =
        (dataMap["font_size"] as? Number
            ?: 16).toString()
            .toFloat()
            .toInt()
    val fontWeight =
        mapFontWeight(
            fontWeight = dataMap["font_weight"] as? String
                ?: ""
        )
    val options =
        dataMap["options"] as List<String>
    val isRequired =
        dataMap["isRequired"] as? Boolean
            ?: false
    val hintText =
        if (dataMap["text"] != null) {
            dataMap["text"] as String
        } else {
            searchData(
                key = dataMap["\$text"] as String
            )
        }

    var expand by remember(
        dropdownId
    ) {
        mutableStateOf(
            false
        )
    }

    DropDownOption(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                dropdownPadding
            ),
        fontWeight = fontWeight,
        font_size = fontSize,
        hintText = hintText,
        options = options,
        selectedOption = "",
        onOptionSelected = {},
        expanded = expand,
        onExpandedChange = {
            expand =
                !expand
        },
        isRequired = isRequired
    )
}