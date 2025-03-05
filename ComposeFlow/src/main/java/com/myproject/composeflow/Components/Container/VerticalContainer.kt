package com.myproject.composeflow.Components.Container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun VerticalContainer(
    modifier: Modifier = Modifier,
    contents: @Composable () -> Unit,
    height: Dp? = null
) {
    Column(
        modifier = modifier
            .let { if (height != null) it.height(height) else it.fillMaxHeight() }
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        contents()
    }
}
