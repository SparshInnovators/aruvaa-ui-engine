package com.myproject.composeflow.Components.Layouts

import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    itemCount: Int,
    ColumnCount: Int,
    height: Int? = null,
    content: @Composable () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(ColumnCount),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(itemCount) {
            Box(
                modifier = modifier
                    .let {
                        if (height == null) {
                            Modifier.fillMaxHeight()
                        } else {
                            Modifier.height(height.dp)
                        }
                    }
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}