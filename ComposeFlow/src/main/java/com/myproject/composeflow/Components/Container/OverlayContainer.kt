package com.myproject.composeflow.Components.Container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Text.TextBlock

@Composable
fun OverlayContainer() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {

        AsyncImage(
            model = ("https://images.ctfassets.net/hrltx12pl8hq/28ECAQiPJZ78hxatLTa7Ts/2f695d869736ae3b0de3e56ceaca3958/free-nature-images.jpg?fit=fill&w=1200&h=630"),
            contentDescription = "Background Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )

        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoFRQjM-wM_nXMA03AGDXgJK3VeX7vtD3ctA&s",
            contentDescription = "image 1",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color.Gray)
        )

        TextBlock(
            text = "center",
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Gray)
        )


        TextBlock(
            text = "Upper-right corner",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Gray)
                .padding(8.dp)
        )

        TextBlock(
            text = "Lower-right corner",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color.Gray)
                .padding(8.dp)
        )
    }
}
