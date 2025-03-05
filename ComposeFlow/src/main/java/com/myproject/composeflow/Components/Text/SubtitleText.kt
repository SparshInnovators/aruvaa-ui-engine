package com.myproject.composeflow.Components.Text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SubtitleText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 15,
    fontWeight: FontWeight? = FontWeight.Light
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        style = TextStyle(fontWeight = fontWeight),
        modifier = modifier
    )
}