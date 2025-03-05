package com.myproject.composeflow.Components.Layouts.Vertical

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun DynamicColumn(modifier: Modifier = Modifier) {
    LazyColumn {
        items(50) {
            TextBlock("Centered Element", Modifier)
        }
    }
}