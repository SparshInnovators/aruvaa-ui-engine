package com.myproject.composeflow.Components.Container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun HorizontalContainer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBlock("wrap_content", Modifier.padding(end = 2.dp))

        TextBlock(
            "Weight = 1", Modifier
                .weight(1f)
                .padding(horizontal = 2.dp)
        )

        TextBlock(
            "Weight = 2", Modifier
                .weight(2f)
                .padding(horizontal = 2.dp)
        )

        TextBlock("wrap_content", Modifier.padding(start = 2.dp))
    }
}
