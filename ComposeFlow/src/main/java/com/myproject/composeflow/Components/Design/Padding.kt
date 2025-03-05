package com.myproject.composeflow.Components.Design

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun paddingValues(path: Any?): Modifier {
    return when (val padding = path) {
        is Number -> Modifier.padding(padding.toFloat().dp)
        is Map<*, *> -> Modifier.padding(
            start = (padding["left"] as? Number
                ?: 0.0).toFloat().dp,
            top = (padding["top"] as? Number
                ?: 0.0).toFloat().dp,
            end = (padding["right"] as? Number
                ?: 0.0).toFloat().dp,
            bottom = (padding["bottom"] as? Number
                ?: 0.0).toFloat().dp
        )

        else -> Modifier.padding(3.dp)
    }
}