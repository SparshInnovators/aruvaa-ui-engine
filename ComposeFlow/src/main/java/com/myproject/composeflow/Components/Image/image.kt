package com.myproject.composeflow.Components.Image

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun Image(modifier: Modifier = Modifier, image_url: String,imgDesc:String?=null) {
    AsyncImage(
        model = image_url,
        contentDescription = imgDesc,
        modifier = modifier
    )
}