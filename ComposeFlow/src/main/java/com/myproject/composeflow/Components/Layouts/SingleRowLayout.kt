package com.myproject.composeflow.Components.Layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleRowLayout(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
    spacedBy: Int? = 0,
    isCentered: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isCentered) Arrangement.spacedBy(
            spacedBy?.dp ?: 0.dp,
            Alignment.CenterHorizontally
        )
        else Arrangement.spacedBy(spacedBy?.dp ?: 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

