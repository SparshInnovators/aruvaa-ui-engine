package com.myproject.composeflow.ComponentRender

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.composeflow.Components.Image.Image
import com.myproject.composeflow.Utils.Padding.paddingValues
import kotlin.collections.get

@Composable
fun Render_Image(modifier: Modifier = Modifier, dataMap: Map<*, *>) {
    val imagePadding = paddingValues(path = dataMap["padding"])
    val imageUrl = dataMap["image_url"] as String
    val imageHeight = (dataMap["height"] as? Number ?: 100).toInt()


    Image(
        image_url = imageUrl,
        modifier = modifier
            .size(imageHeight.dp)
            .then(imagePadding)
    )
}
