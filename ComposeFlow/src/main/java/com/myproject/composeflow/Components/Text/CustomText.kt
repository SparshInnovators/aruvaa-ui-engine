package com.myproject.composeflow.Components.Text

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun CustomAnnotatedText(text: String, ranges: List<Map<String, Any>>,modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        append(text)

        for (range in ranges) {
            val start = (range["start"] as? Number)?.toInt() ?: 0
            val end = (range["end"] as? Number)?.toInt() ?: 0
            if (start < end && start in text.indices && end in 0..text.length) {
                val fontWeight = when (range["annotatedType"] as? String) {
                    "bold" -> FontWeight.Bold
                    "medium" -> FontWeight.Medium
                    else -> null
                }
                val textColor = (range["text_color"] as? String)?.let {
                    Color(
                        android.graphics.Color.parseColor(it)
                    )
                }
                val underline =
                    if ((range["underline"] as? String) == "single") TextDecoration.Underline else TextDecoration.None
                val letterSpacing = (range["letter_spacing"] as? Number)?.toFloat()?.sp

                addStyle(
                    style = SpanStyle(
                        fontWeight = fontWeight,
                        color = textColor ?: Color.Unspecified,
                        textDecoration = underline,
                        letterSpacing = letterSpacing ?: TextUnit.Unspecified
                    ),
                    start = start,
                    end = end
                )
            }
        }

    }

    Text(
        text = annotatedString,
        fontSize = 16.sp,
        modifier = modifier
    )
}