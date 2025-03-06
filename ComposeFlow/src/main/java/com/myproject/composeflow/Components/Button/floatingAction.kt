package com.myproject.composeflow.Components.Button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Text.mapStringToIcon

@Composable
fun ButtonFloatingAction(modifier: Modifier = Modifier, icon: String, onclick: () -> Unit) {
    FloatingActionButton(
        onClick = onclick
    ) {
        Icon(mapStringToIcon[icon] ?: Icons.Default.Add, null)
    }
}