package com.myproject.composeflow.Components.Button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: String? = null,
    fontSize: Int = 20,
    fontWeight: FontWeight,
    width: Dp? = null,
    onclick: () -> Unit
) {
    TextButton(
        onClick = onclick,
        modifier = modifier.then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
    ) {
        Text(
            text,
            style = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = fontWeight,
                color = if (textColor != null) Color(textColor.toColorInt()) else Color.White
            )
        )
    }
}
