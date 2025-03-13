package com.myproject.composeflow.Components.Text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(
    text: String,
    fontSize: Int = 30,
    fontWeight: FontWeight,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        fontWeight = fontWeight,
        modifier = modifier
//        modifier = modifier.padding(horizontal = 24.dp)
    )
}

val fontWeightMap = mapOf(
    "thin" to FontWeight.Thin,
    "extra_light" to FontWeight.ExtraLight,
    "light" to FontWeight.Light,
    "normal" to FontWeight.Normal,
    "medium" to FontWeight.Medium,
    "semi-bold" to FontWeight.SemiBold,
    "bold" to FontWeight.Bold,
    "extra_bold" to FontWeight.ExtraBold,
    "black" to FontWeight.Black
)