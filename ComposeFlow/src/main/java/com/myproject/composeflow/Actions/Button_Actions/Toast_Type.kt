package com.myproject.composeflow.Actions.Button_Actions

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.Modifier

fun actionToast(
    modifier: Modifier = Modifier,
    context: Context,
    message: String,
    duration: String
) {
    val dur = when (duration) {
        "short" -> Toast.LENGTH_SHORT
        "long" -> Toast.LENGTH_LONG
        else -> Toast.LENGTH_SHORT
    }
    return Toast.makeText(context, message, dur).show()
}