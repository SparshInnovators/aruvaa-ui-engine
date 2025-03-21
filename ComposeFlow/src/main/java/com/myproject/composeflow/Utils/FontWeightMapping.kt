package com.myproject.composeflow.Utils

import androidx.compose.ui.text.font.FontWeight

fun mapFontWeight(fontWeight: String?): FontWeight {
    return when (fontWeight) {
        "thin" -> FontWeight.Thin
        "extra_light" -> FontWeight.ExtraLight
        "light" -> FontWeight.Light
        "normal" -> FontWeight.Normal
        "medium" -> FontWeight.Medium
        "semi-bold" -> FontWeight.SemiBold
        "bold" -> FontWeight.Bold
        "extra_bold" -> FontWeight.ExtraBold
        else -> FontWeight.Normal
    }
}