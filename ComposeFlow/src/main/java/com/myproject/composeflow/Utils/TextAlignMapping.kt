package com.myproject.composeflow.Utils

import androidx.compose.ui.text.style.TextAlign

fun mapTextAlign(align: String): TextAlign {
    return when (align.lowercase()) {
        "center" -> TextAlign.Center
        "start" -> TextAlign.Start
        "end" -> TextAlign.End
        "justify" -> TextAlign.Justify
        "left" -> TextAlign.Left
        "right" -> TextAlign.Right
        else -> TextAlign.Start
    }
}