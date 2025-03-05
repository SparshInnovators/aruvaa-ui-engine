package com.myproject.composeflow.Components.Button

import android.icu.text.ListFormatter.Width
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text: String,
    bgColor: String,
    textColor: String,
    fontSize: Int = 20,
    fontWeight: FontWeight,
    width: Dp? = null
) {

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(bgColor.toColorInt()),
            contentColor = Color(textColor.toColorInt())
        ),
        modifier = modifier.then(if (width != null) Modifier.width(width) else Modifier.fillMaxWidth())
    ) {
        Text(
            text,
            style = TextStyle(fontSize = fontSize.sp, fontWeight = fontWeight),
        )
    }
}