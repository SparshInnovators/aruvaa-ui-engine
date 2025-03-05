package com.myproject.testingframework.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Button.TextButton
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Text.MultiLineInputText
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.globalMap.GlobalMap
import com.myproject.testingframework.parseJsonToKotlin

@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier) {
    val template = parseJsonToKotlin()[0]
    val content = parseJsonToKotlin()[1]

    val name = template["name"]
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])

    val templateItems = template["items"] as List<Map<*, *>>
    val contentItems = content["items"] as List<Map<*, *>>


    BoxContainer(
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
                                                        Pair("text", "Subtitle") -> "SubtitleText"
                                                        else -> type.first
                                                    }
                                                when (type) {
                                                    "TitleText" -> {
                                                        val font_size =
                                                            tempItem1["font_size"].toString()
                                                                .toFloat().toInt()
                                                        val font_weight =
                                                            fontWeightMap[tempItem1["font_weight"]]
                                                                ?: FontWeight.Black
                                                        val paddings =
                                                            paddingValues(path = tempItem1["margins"])
                                                        val contentId = tempItem1["#text"] as String

                                                        val text = contItem1[contentId].toString()

                                                        TitleText(
                                                            text = text,
                                                            fontWeight = font_weight,
                                                            fontSize = font_size,
                                                            modifier = Modifier.then(paddings)
                                                        )
                                                    }

                                                    "SubtitleText" -> {
                                                        val font_size =
                                                            tempItem1["font_size"].toString()
                                                                .toFloat().toInt()
                                                        val font_weight =
                                                            fontWeightMap[tempItem1["font_weight"]]
                                                                ?: FontWeight.Black
                                                        val paddings =
                                                            paddingValues(path = tempItem1["margins"])
                                                        val contentId = tempItem1["#text"] as String

                                                        val text = contItem1[contentId].toString()

                                                        SubtitleText(
                                                            text = text,
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            modifier = Modifier.then(paddings)
                                                        )
                                                    }

                                                    "inputText" -> {
                                                        when ((tempItem1["maxLines"] as? Number)?.toInt()
                                                            ?: 1) {
                                                            1 -> {
                                                                val keyboardType =
                                                                    mapToKeyboardType[tempItem1["keyboardType"]]
                                                                val font_size =
                                                                    (tempItem1["font_size"] as Number).toInt()
                                                                val font_weight =
                                                                    fontWeightMap[tempItem1["font_weight"]]
                                                                val paddings =
                                                                    paddingValues(path = tempItem1["margins"])
                                                                val suffixIcon =
                                                                    tempItem1["suffixIcon"]
                                                                val contentId =
                                                                    tempItem1["#text"] as String

                                                                val text =
                                                                    contItem1[contentId].toString()

                                                                SingleLineInputText(
                                                                    id = contentId,
                                                                    keyboardType = keyboardType
                                                                        ?: KeyboardType.Text,
                                                                    font_size = font_size,
                                                                    fontWeight = font_weight,
                                                                    suffixIcon = suffixIcon.toString(),
                                                                    hintText = text,
                                                                    modifier = Modifier.then(
                                                                        paddings
                                                                    )
                                                                )
                                                            }

                                                            else -> {
                                                                MultiLineInputText()
                                                            }
                                                        }
                                                    }

                                                    "button" -> {
                                                        val font_size =
                                                            (tempItem1["font_size"] as Number).toInt()
                                                        val font_weight =
                                                            fontWeightMap[tempItem1["font_weight"]]
                                                                ?: FontWeight.Normal
                                                        val buttonPadding =
                                                            paddingValues(path = tempItem1["margins"])
                                                        val bgColor =
                                                            (tempItem1["backgroundColor"] as String)
                                                        val contentId = tempItem1["#text"] as String

                                                        val text = contItem1[contentId].toString()

                                                        TextButton(
                                                            text = text,
                                                            bgColor = bgColor,
                                                            textColor = "#fcfdff",
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            modifier = Modifier
                                                                .then(buttonPadding),
                                                        )

                                                    }
                                                }
                                            }
                                        }
                                    )
                                }

                                "horizontal" -> {

                                }
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

fun extractType(type: String): Pair<String, String> {
    if (type.startsWith("text-")) {
        val textType = type.split("-")
        return Pair("text", textType.getOrElse(1) { "" })
    }
    return Pair(type, "")
}