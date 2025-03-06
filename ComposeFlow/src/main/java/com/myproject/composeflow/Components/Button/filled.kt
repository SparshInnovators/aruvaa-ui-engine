package com.myproject.composeflow.Components.Button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun ButtonFilled(
    modifier: Modifier = Modifier,
    text: String,
    bgColor: String? = null,
    textColor: String? = null,
    fontSize: Int = 20,
    fontWeight: FontWeight,
    width: Dp? = null,
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (bgColor != null) Color(bgColor.toColorInt()) else ButtonDefaults.buttonColors().containerColor,
            contentColor = if (textColor != null) Color(textColor.toColorInt()) else ButtonDefaults.buttonColors().contentColor
        ),
        modifier = modifier.then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
    ) {
        Text(
            text,
            style = TextStyle(fontSize = fontSize.sp, fontWeight = fontWeight),
        )
    }
}