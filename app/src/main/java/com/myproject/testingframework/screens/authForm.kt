package com.myproject.testingframework.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.myproject.composeflow.Actions.Button_Actions.actionToast
import com.myproject.composeflow.Components.Button.TextButton
import com.myproject.composeflow.Components.Container.BoxContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Text.MultiLineInputText
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.Navigation
import com.myproject.testingframework.formdata
import com.myproject.testingframework.parseJsonToKotlin

@Composable
fun AuthenticationScreen(modifier: Modifier = Modifier, NavController: NavController) {

    val context = LocalContext.current

    val template = parseJsonToKotlin(data = formdata)[0]
    val content = parseJsonToKotlin(data = formdata)[1]

    val name = template["name"]
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])

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

                                                                val existingValue =
                                                                    textFieldValues.find { it.first == contentId }?.second
                                                                        ?: ""

                                                                SingleLineInputText(
                                                                    keyboardType = keyboardType
                                                                        ?: KeyboardType.Text,
                                                                    font_size = font_size,
                                                                    fontWeight = font_weight,
                                                                    suffixIcon = suffixIcon.toString(),
                                                                    hintText = text,
                                                                    value = existingValue,
                                                                    onValueChange = { newValue ->
                                                                        val index =
                                                                            textFieldValues.indexOfFirst { it.first == contentId }
                                                                        if (index != -1) {
                                                                            textFieldValues[index] =
                                                                                textFieldValues[index].copy(
                                                                                    second = newValue
                                                                                )
                                                                        } else {
                                                                            textFieldValues.add(
                                                                                contentId to newValue
                                                                            )
                                                                        }
                                                                    },
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
                                                        val action =
                                                            tempItem1["action"] as Map<*, *>
                                                        val onclick: () -> Unit = {
                                                            action.let {
                                                                if (it["type"] == "toast") {
                                                                    val message =
                                                                        it["message"] as String
                                                                    val duration =
                                                                        it["duration"] as String

                                                                    actionToast(
                                                                        context = context,
                                                                        message = message,
                                                                        duration = duration
                                                                    )
                                                                } else if (it["type"] == "navigate") {
                                                                    val screenName =
                                                                        it["destination"] as String

                                                                    if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                        NavController.navigate(
                                                                            screenName
                                                                        )
                                                                    } else {
                                                                        actionToast(
                                                                            modifier = Modifier,
                                                                            context = context,
                                                                            message = "Invalid Credentials",
                                                                            duration = "short"
                                                                        )
                                                                    }

                                                                }
                                                            }
                                                        }
                                                        val contentId = tempItem1["#text"] as String

                                                        val text = contItem1[contentId].toString()

                                                        TextButton(
                                                            text = text,
                                                            bgColor = bgColor,
                                                            textColor = "#fcfdff",
                                                            fontSize = font_size,
                                                            fontWeight = font_weight,
                                                            onclick = onclick,
                                                            modifier = Modifier
                                                                .then(buttonPadding),
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

fun extractType(type: String): Pair<String, String> {
    if (type.startsWith("text-")) {
        val textType = type.split("-")
        return Pair("text", textType.getOrElse(1) { "" })
    }
    return Pair(type, "")
}