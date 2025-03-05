package com.myproject.composeflow.Components.Text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//simple text with option of alignment
@Composable
fun TextBlock(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.Black)
    }
}