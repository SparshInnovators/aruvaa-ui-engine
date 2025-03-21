package com.myproject.composeflow.Components.Layouts

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemLayout(
    modifier: Modifier = Modifier,
    headerContent: (@Composable () -> Unit)? = null,
    content: @Composable (index: Int) -> Unit,
    count: Int,
    footerContent: (@Composable () -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        headerContent?.let {
            item {
                headerContent()
            }
        }

        items(count) { index ->
            content(index)
            if (index < count - 1) {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        footerContent?.let {
            item {
                footerContent()
            }
        }
    }
}