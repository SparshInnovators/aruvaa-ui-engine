package com.myproject.composeflow.Components.Button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun ButtonElevated(
    modifier: Modifier = Modifier, text: String,
    bgColor: String? = null,
    textColor: String? = null,
    fontSize: Int = 20,
    fontWeight: FontWeight,
    width: Dp? = null,
    wrapContent: Boolean = false,
    onclick: () -> Unit
) {
    ElevatedButton(
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (bgColor != null) Color(bgColor.toColorInt()) else ButtonDefaults.buttonColors().containerColor,
            contentColor = if (textColor != null) Color(textColor.toColorInt()) else ButtonDefaults.buttonColors().contentColor
        ),
        modifier = modifier.then(
            when {
                wrapContent -> Modifier.wrapContentWidth()
                width != null -> Modifier.width(width)
                else -> Modifier.fillMaxWidth()
            }
        )
    ) {
        Text(
            text,
            style = TextStyle(fontSize = fontSize.sp, fontWeight = fontWeight),
        )
    }
}