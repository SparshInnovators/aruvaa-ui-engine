package com.myproject.composeflow.Utils

import androidx.compose.ui.text.input.KeyboardType

fun mapKeyBoardType(keyboardType: String?): KeyboardType {
    return when (keyboardType) {
        "text" -> KeyboardType.Text
        "number" -> KeyboardType.Number
        "phone" -> KeyboardType.Phone
        "email" -> KeyboardType.Email
        "password" -> KeyboardType.Password
        "decimal" -> KeyboardType.Decimal
        "uri" -> KeyboardType.Uri
        else -> KeyboardType.Text
    }
}