package com.myproject.composeflow.ComponentRender

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.searchData
import kotlin.collections.get

@Composable
fun Render_Text(modifier: Modifier = Modifier, dataMap: Map<*, *>, searchData: String) {
    val textPadding = paddingValues(path = dataMap["padding"])
    val fontSize =
        (dataMap["font_size"] ?: 16).toString().toFloat().toInt()
    val fontWeight =
        mapFontWeight(fontWeight = dataMap["font_weight"] as? String)
    val textcolor = if (dataMap["color"] != null) Color(
        dataMap["color"].toString()
            .toColorInt()
    ) else Color.Unspecified
    val text = if (dataMap["text"] != null) {
        dataMap["text"] as String
    } else {
        searchData
    }
    SubtitleText(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = textcolor,
        modifier = modifier.then(textPadding)
    )
}