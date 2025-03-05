package com.myproject.testingframework.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.formdata
import com.myproject.testingframework.parseJsonToKotlin
import com.myproject.testingframework.secondpage

@Composable
fun DynamicScreen(modifier: Modifier = Modifier, screenName: String) {

    val template = parseJsonToKotlin(data = secondpage)[0]
    val content = parseJsonToKotlin(data = secondpage)[1]

    val name = template["name"] as String
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])

    if (screenName == name) {
        val templateItems = template["items"] as List<Map<*, *>>
        val contentItems = content["items"] as List<Map<*, *>>

        BoxContainer(
            alignment = null,
            height = null,
            modifier = modifier.then(templatePadding),
            content = {
                Column {
                    contentItems.forEach { contItem1 ->
                        when (contItem1["type"]) {
                            name -> {
                                when (orientation ?: "vertical") {
                                    "vertical" -> {
                                        VerticalContainer(
                                            modifier = Modifier,
                                            contents = {
                                                templateItems.forEach { tempItem1 ->
                                                    val type =
                                                        when (val type =
                                                            extractType(tempItem1["type"] as String)) {
                                                            Pair("text", "title") -> "TitleText"
                                                            Pair("text", "body") -> "SubtitleText"
                                                            Pair(
                                                                "text",
                                                                "Subtitle"
                                                            ) -> "SubtitleText"

                                                            else -> type.first
                                                        }
                                                    when (type) {
                                                        "container" -> {
                                                            BoxContainer(
                                                                alignment = Alignment.Center,
                                                                height = null,
                                                                content = {
                                                                    val alignment =
                                                                        tempItem1["align"]
                                                                    val items =
                                                                        tempItem1["items"] as List<Map<*, *>>
                                                                    items.forEach { conItem ->
                                                                        val type =
                                                                            when (val type =
                                                                                extractType(conItem["type"] as String)) {
                                                                                Pair(
                                                                                    "text",
                                                                                    "title"
                                                                                ) -> "TitleText"

                                                                                Pair(
                                                                                    "text",
                                                                                    "body"
                                                                                ) -> "SubtitleText"

                                                                                Pair(
                                                                                    "text",
                                                                                    "Subtitle"
                                                                                ) -> "SubtitleText"

                                                                                else -> type.first
                                                                            }
                                                                        when (type) {
                                                                            "TitleText" -> {
                                                                                val font_size =
                                                                                    conItem["font_size"].toString()
                                                                                        .toFloat()
                                                                                        .toInt()
                                                                                val font_weight =
                                                                                    fontWeightMap[conItem["font_weight"]]
                                                                                        ?: FontWeight.Black
                                                                                val paddings =
                                                                                    paddingValues(
                                                                                        path = conItem["margins"]
                                                                                    )
                                                                                val contentId =
                                                                                    conItem["#text"] as String

                                                                                val text =
                                                                                    replacePlaceholders(
                                                                                        jsonString = contItem1[contentId] as String,
                                                                                        replacementString = (textFieldValues.find { it.first == "usernameText" }?.second)
                                                                                            ?: "null",
                                                                                    )

                                                                                TitleText(
                                                                                    text = text,
                                                                                    fontWeight = font_weight,
                                                                                    fontSize = font_size,
                                                                                    modifier = Modifier.then(
                                                                                        paddings
                                                                                    )
                                                                                )
                                                                            }
                                                                        }
                                                                    }
                                                                },
                                                                modifier = Modifier,
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        )
                                    }

                                    "horizontal" -> {}
                                }
                            }

                            else -> {
                                when (contItem1["type"]) {
                                    "image" -> {
                                        val image_url = contItem1["image_url"] as String
                                        AsyncImage(
                                            model = image_url,
                                            contentDescription = null,
                                            Modifier
                                                .padding(top = 20.dp)
                                                .fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}


fun replacePlaceholders(jsonString: String, replacementString: String): String {
    return jsonString.replace(Regex("\\{\\{(.*?)\\}\\}"), replacementString)
}
