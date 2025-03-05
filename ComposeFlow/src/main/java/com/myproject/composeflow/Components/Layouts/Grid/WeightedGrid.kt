package com.myproject.composeflow.Components.Layouts.Grid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightedGrid() {
    Column(
        modifier = Modifier
            .height(260.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GridItem(
                "1", Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
            GridItem(
                "2", Modifier
                    .weight(2f)
                    .fillMaxHeight()
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            GridItem(
                "3", Modifier
                    .weight(2f)
                    .fillMaxHeight()
            )
            GridItem(
                "4", Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }

    }
}

@Composable
fun GridItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(Color(0x0E000000))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 12.sp, color = Color.Black)
    }
}
