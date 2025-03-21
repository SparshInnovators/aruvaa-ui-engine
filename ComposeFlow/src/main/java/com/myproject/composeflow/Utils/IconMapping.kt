package com.myproject.composeflow.Utils

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

fun mapIcon(icon: String?): ImageVector {
    return when (icon) {
        "person" -> Icons.Default.Person
        "lock" -> Icons.Default.Lock
        "arrow_back" -> Icons.Default.ArrowBackIosNew
        "more_vert" -> Icons.Default.MoreVert
        "menu" -> Icons.Default.Menu
        "home" -> Icons.Default.Home
        "settings" -> Icons.Default.Settings
        "logout" -> Icons.Default.Logout
        else -> Icons.Default.HelpOutline
    }
}
