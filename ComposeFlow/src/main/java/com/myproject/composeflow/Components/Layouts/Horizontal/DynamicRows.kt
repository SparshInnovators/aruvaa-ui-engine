package com.myproject.composeflow.Components.Layouts.Horizontal

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun DynamicRows(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    LazyRow {
        items(50) {
            content()
        }
    }
}