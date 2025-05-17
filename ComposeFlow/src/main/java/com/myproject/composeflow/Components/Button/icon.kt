package com.myproject.composeflow.Components.Button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myproject.composeflow.Components.Text.mapStringToIcon

@Composable
fun ButtonIcon(
    modifier: Modifier = Modifier,
    icon: String,
    onclick: () -> Unit,
    label: String? = null
) {
//    IconButton(
//        onClick = onclick,
//        modifier = modifier
//    ) {
//        Column(
//            modifier = Modifier.wrapContentSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Icon(
//                imageVector = mapStringToIcon[icon] ?: Icons.Default.Share,
//                contentDescription = "share",
//                modifier = Modifier.size(25.dp)
//            )
//            Text(label ?: "")
//        }
//    }
    Column(
        modifier = modifier
            .clickable { onclick() }, // Acts as a clickable button
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = mapStringToIcon[icon] ?: Icons.Default.Share,
            contentDescription = label,
            modifier = Modifier.size(25.dp)
        )
        if (label != null) {
            Text(
                text = label,
            )
        }
    }
}