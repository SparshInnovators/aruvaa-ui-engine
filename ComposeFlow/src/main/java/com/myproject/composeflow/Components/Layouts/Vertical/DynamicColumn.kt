package com.myproject.composeflow.Components.Layouts.Vertical

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun DynamicColumn(
    modifier: Modifier = Modifier, itemCount: Int,
    content: @Composable (index: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
    ) {

        items(itemCount) { index ->
            content(index)
            if (index < itemCount - 1) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}