package com.myproject.composeflow.Components.Text

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//simple text with option of alignment
@Composable
fun TextBlock(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int? = 16,
    fontWeight: FontWeight? = FontWeight.Normal,
    alignment: Alignment? = null
) {
    Box(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 8.dp).fillMaxWidth(),
        contentAlignment = alignment ?:  Alignment.TopStart
    ) {
        Text(
            text = text,
            fontSize = fontSize!!.sp,
            color = Color.Black,
            fontWeight = fontWeight,
        )
    }
}