package com.myproject.testingframework.divkitSample

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Text.CustomAnnotatedText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.testingframework.parseJsonToKotlin

@Composable
fun UiTemplate(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val template = parseJsonToKotlin(data = data)[0]
    val content = parseJsonToKotlin(data = data)[1]

    val name = template["name"] as String

    val paddingModifier = when (val paddingValue = template["paddings"]) {
        is Number -> Modifier.padding(paddingValue.toFloat().dp)
        is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
            start = (paddingValue["left"] as? Number ?: 0.0).toFloat().dp,
            top = (paddingValue["top"] as? Number ?: 0.0).toFloat().dp,
            end = (paddingValue["right"] as? Number ?: 0.0).toFloat().dp,
            bottom = (paddingValue["bottom"] as? Number ?: 0.0).toFloat().dp
        )

        else -> Modifier.padding(3.dp)
    }

    val itemsList = template["items"] as List<Map<*, *>>
    val contentList = content["items"] as List<Map<*, *>>


    BoxContainer(
        alignment = null,
        height = null,
        content = {
            Column {
                contentList.forEach { item1 ->
                    when (item1["type"]) {
                        name -> {
                            when (template["orientation"] ?: "vertical") {
                                "vertical" -> {
                                    Column {
                                        itemsList.forEach { item ->
                                            when (item["type"]) {
                                                "text" -> {

                                                    val font_size =
                                                        item["font_size"].toString().toFloat()
                                                            .toInt()
                                                    val font_weight =
                                                        fontWeightMap[item["font_weight"]]

                                                    val contentId = item["#text"] as String

                                                    val text = item1[contentId].toString()

                                                    if (item["#text"] == "title") {
                                                        TitleText(
                                                            text,
                                                            fontSize = font_size,
                                                            fontWeight = font_weight
                                                                ?: FontWeight.Bold,
                                                            Modifier.then(
                                                                when (val paddingValue =
                                                                    item["margins"]) {
                                                                    is Number -> Modifier.padding(
                                                                        paddingValue.toFloat().dp
                                                                    )

                                                                    is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
                                                                        start = (paddingValue["left"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        top = (paddingValue["top"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        end = (paddingValue["right"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        bottom = (paddingValue["bottom"] as? Number
                                                                            ?: 0.0).toFloat().dp
                                                                    )

                                                                    else -> Modifier.padding(3.dp)
                                                                }
                                                            )
                                                        )
                                                    } else if (item["#text"] == "body" || item["#text"] == "Subtitle") {
                                                        SubtitleText(
                                                            text = text,
                                                            fontSize = font_size,
                                                            modifier = Modifier.then(
                                                                when (val paddingValue =
                                                                    item["margins"]) {
                                                                    is Number -> Modifier.padding(
                                                                        paddingValue.toFloat().dp
                                                                    )

                                                                    is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
                                                                        start = (paddingValue["left"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        top = (paddingValue["top"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        end = (paddingValue["right"] as? Number
                                                                            ?: 0.0).toFloat().dp,
                                                                        bottom = (paddingValue["bottom"] as? Number
                                                                            ?: 0.0).toFloat().dp
                                                                    )

                                                                    else -> Modifier.padding(3.dp)
                                                                }
                                                            )
                                                        )
                                                    } else {
                                                        TextBlock(text = text)
                                                    }
                                                }

                                                "container" -> {
                                                    val itemType = item["#items"] as String
                                                    when (itemType) {
                                                        "links" -> {
                                                            val list =
                                                                item1[itemType] as List<Map<*, *>>
                                                            list.forEach { link ->
                                                                val link_text =
                                                                    link["link_text"].toString()
                                                                val link_url =
                                                                    link["link"].toString()

                                                                CustomAnnotatedText(
                                                                    link_text, ranges = listOf(
                                                                        mapOf(
                                                                            "start" to 0,
                                                                            "end" to link_text.length,
                                                                            "underline" to "single",
                                                                            "text_color" to "#0000ff"
                                                                        ),
                                                                    ),
                                                                    modifier = Modifier
                                                                        .clickable {
                                                                            val intent = Intent(
                                                                                Intent.ACTION_VIEW,
                                                                                Uri.parse(link_url)
                                                                            )
                                                                            context.startActivity(
                                                                                intent
                                                                            )
                                                                        }
                                                                        .padding(bottom = 5.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                "horizontal" -> {
                                    Row(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        itemsList.forEach { item ->
                                            when (item["type"]) {
                                                "text" -> {

                                                    val font_size =
                                                        item["font_size"].toString().toFloat()
                                                            .toInt()
                                                    val font_weight =
                                                        fontWeightMap[item["font_weight"]]

                                                    val contentId = item["#text"] as String

                                                    val text = item1[contentId].toString()

                                                    if (item["#text"] == "title") {
                                                        TitleText(
                                                            text,
                                                            fontSize = font_size,
                                                            fontWeight = font_weight
                                                                ?: FontWeight.Bold,
                                                            Modifier
                                                                .weight(1f)
                                                                .then(
                                                                    when (val paddingValue =
                                                                        item["margins"]) {
                                                                        is Number -> Modifier.padding(
                                                                            paddingValue.toFloat().dp
                                                                        )

                                                                        is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
                                                                            start = (paddingValue["left"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            top = (paddingValue["top"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            end = (paddingValue["right"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            bottom = (paddingValue["bottom"] as? Number
                                                                                ?: 0.0).toFloat().dp
                                                                        )

                                                                        else -> Modifier.padding(3.dp)
                                                                    }
                                                                )
                                                        )
                                                    } else if (item["#text"] == "body" || item["#text"] == "Subtitle") {
                                                        SubtitleText(
                                                            text = text,
                                                            fontSize = font_size,
                                                            modifier = Modifier
                                                                .weight(1f)
                                                                .then(
                                                                    when (val paddingValue =
                                                                        item["margins"]) {
                                                                        is Number -> Modifier.padding(
                                                                            paddingValue.toFloat().dp
                                                                        )

                                                                        is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
                                                                            start = (paddingValue["left"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            top = (paddingValue["top"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            end = (paddingValue["right"] as? Number
                                                                                ?: 0.0).toFloat().dp,
                                                                            bottom = (paddingValue["bottom"] as? Number
                                                                                ?: 0.0).toFloat().dp
                                                                        )

                                                                        else -> Modifier.padding(3.dp)
                                                                    }
                                                                )
                                                        )
                                                    } else {
                                                        TextBlock(text=text)
                                                    }
                                                }

                                                "container" -> {
                                                    val itemType = item["#items"] as String
                                                    when (itemType) {
                                                        "links" -> {
                                                            val list =
                                                                item1[itemType] as List<Map<*, *>>
                                                            list.forEach { link ->
                                                                val link_text =
                                                                    link["link_text"].toString()
                                                                val link_url =
                                                                    link["link"].toString()

                                                                CustomAnnotatedText(
                                                                    link_text, ranges = listOf(
                                                                        mapOf(
                                                                            "start" to 0,
                                                                            "end" to link_text.length,
                                                                            "underline" to "single",
                                                                            "text_color" to "#0000ff"
                                                                        ),
                                                                    ),
                                                                    modifier = Modifier
                                                                        .weight(1f)
                                                                        .clickable {
                                                                            val intent = Intent(
                                                                                Intent.ACTION_VIEW,
                                                                                Uri.parse(link_url)
                                                                            )
                                                                            context.startActivity(
                                                                                intent
                                                                            )
                                                                        }
                                                                        .padding(bottom = 5.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else -> {
                            when (item1["type"]) {
                                "image" -> {
                                    val image_url = item1["image_url"] as String
                                    AsyncImage(
                                        model = image_url,
                                        contentDescription = null,
                                        Modifier
                                            .fillMaxWidth()
                                            .then(
                                                when (val paddingValue = item1["margins"]) {
                                                    is Number -> Modifier.padding(paddingValue.toFloat().dp)
                                                    is Map<*, *> -> Modifier.padding( // Case 2: Map of paddings
                                                        start = (paddingValue["left"] as? Number
                                                            ?: 0.0).toFloat().dp,
                                                        top = (paddingValue["top"] as? Number
                                                            ?: 0.0).toFloat().dp,
                                                        end = (paddingValue["right"] as? Number
                                                            ?: 0.0).toFloat().dp,
                                                        bottom = (paddingValue["bottom"] as? Number
                                                            ?: 0.0).toFloat().dp
                                                    )

                                                    else -> Modifier.padding(3.dp)
                                                }
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier.then(paddingModifier)
    )
}



