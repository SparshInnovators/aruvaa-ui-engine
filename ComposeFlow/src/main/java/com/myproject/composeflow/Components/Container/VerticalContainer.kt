package com.myproject.composeflow.Components.Container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    height: Dp? = null,
    width: Dp? = null,
    spacedBy: Int? = null,
    wrapContentHeight: Boolean = false,
) {
    Column(
        modifier = modifier
            .let {
                when {
                    height != null -> it.height(height)
                    width != null -> it.width(width)
                    wrapContentHeight -> it.wrapContentHeight()
                    else -> it.fillMaxHeight()
                }
            },
        verticalArrangement = Arrangement.spacedBy((spacedBy ?: 0).dp),
    ) {
        contents()
    }
}
