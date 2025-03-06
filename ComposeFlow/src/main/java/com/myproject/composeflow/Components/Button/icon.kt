package com.myproject.composeflow.Components.Button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Text.mapStringToIcon

@Composable
fun ButtonIcon(modifier: Modifier = Modifier, icon: String, onclick: () -> Unit) {
    IconButton(
        onClick = onclick,
        modifier = modifier
    ) {
        Icon(
            imageVector = mapStringToIcon[icon] ?: Icons.Default.Share, contentDescription = "share"
        )
    }
}