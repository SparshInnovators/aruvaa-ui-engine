package com.myproject.composeflow.Components.Container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


//Place element inside the container with a fixed height
@Composable
fun BoxContainer(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    alignment: Alignment?,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .let { if (height != null) it.height(height) else it.fillMaxHeight() }
//            .background(
//                Color.Gray.copy(alpha = 0.1f),
//                shape = RoundedCornerShape(16.dp)
//            ),
        , contentAlignment = alignment ?: Alignment.TopStart
    ) {
        content()
    }
}