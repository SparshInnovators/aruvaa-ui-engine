package com.myproject.testingframework.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
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
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Alert
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SnackBar
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Url
import com.myproject.composeflow.Components.Button.ButtonElevated
import com.myproject.composeflow.Components.Button.ButtonFilled
import com.myproject.composeflow.Components.Button.ButtonFloatingAction
import com.myproject.composeflow.Components.Button.ButtonIcon
import com.myproject.composeflow.Components.Button.ButtonText
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

    var showalertdialog by remember { mutableStateOf(false) }
    var showsimpledialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

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
                                                        val buttonType =
                                                            tempItem1["subtype"] ?: "elevated"

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
                                                                when (it["type"]) {
                                                                    "toast" -> {
                                                                        val message =
                                                                            it["message"] as String
                                                                        val duration =
                                                                            it["duration"] as String

                                                                        ActionType_SnackBar(
                                                                            context = context,
                                                                            message = message,
                                                                            duration = duration
                                                                        )
                                                                    }

                                                                    "navigate" -> {
                                                                        val screenName =
                                                                            it["destination"] as String

                                                                        if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                            NavController.navigate(
                                                                                screenName
                                                                            )
                                                                        } else {
                                                                            ActionType_SnackBar(
                                                                                modifier = Modifier,
                                                                                context = context,
                                                                                message = "Invalid Credentials",
                                                                                duration = "short"
                                                                            )
                                                                        }
                                                                    }

                                                                    "dialog" -> {
                                                                        val subtype =
                                                                            it["subtype"] as String
                                                                        when (subtype) {
                                                                            "alert" -> {
                                                                                dialogTitle =
                                                                                    it["title"] as String
                                                                                dialogMessage =
                                                                                    it["message"] as String
                                                                                if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                                    showalertdialog =
                                                                                        true
                                                                                }
                                                                            }

                                                                            "simple" -> {
                                                                                dialogTitle =
                                                                                    it["title"] as String
                                                                                dialogMessage =
                                                                                    it["message"] as String
                                                                                if (textFieldValues.find { it.first == "usernameText" }?.second == "12345" && textFieldValues.find { it.first == "passwordText" }?.second == "12345") {
                                                                                    showsimpledialog =
                                                                                        true
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    "url" -> {
                                                                        val url_address =
                                                                            it["url_address"] as String
                                                                        ActionType_Url(
                                                                            url = url_address,
                                                                            context = context,
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        if (showalertdialog) {
                                                            ActionType_Alert(
                                                                onDismissRequest = {
                                                                    showalertdialog = false
                                                                },
                                                                onConfirmation = {
                                                                    showalertdialog = true
                                                                },
                                                                dialogTitle = dialogTitle,
                                                                dialogText = dialogMessage,
                                                            )
                                                        }

                                                        if (showsimpledialog) {
                                                            ActionType_SimpleDialog(
                                                                onDismissRequest = {
                                                                    showsimpledialog = false
                                                                },
                                                                dialogTitle = dialogTitle,
                                                                dialogText = dialogMessage
                                                            )
                                                        }

                                                        val contentId = tempItem1["#text"] as String
                                                        val text = contItem1[contentId].toString()


                                                        when (buttonType) {
                                                            "elevated" -> {
                                                                ButtonElevated(
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

                                                            "text" -> {
                                                                ButtonText(
                                                                    text = text,
                                                                    textColor = "#0f0f0f",
                                                                    fontSize = font_size,
                                                                    fontWeight = font_weight,
                                                                    onclick = onclick,
                                                                    modifier = Modifier
                                                                        .then(buttonPadding),
                                                                )
                                                            }

                                                            "filled" -> {
                                                                ButtonFilled(
                                                                    text = text,
                                                                    textColor = "#0f0f0f",
                                                                    fontSize = font_size,
                                                                    fontWeight = font_weight,
                                                                    onclick = onclick,
                                                                    modifier = Modifier
                                                                        .then(buttonPadding),
                                                                )
                                                            }

                                                            "icon" -> {
                                                                val icon =
                                                                    tempItem1["icon"] as String
                                                                ButtonIcon(
                                                                    onclick = onclick,
                                                                    icon = icon
                                                                )
                                                            }

                                                            "floatingAction" -> {
                                                                val icon =
                                                                    tempItem1["icon"] as String
                                                                ButtonFloatingAction(
                                                                    onclick = onclick,
                                                                    icon = icon,
                                                                )
                                                            }
                                                        }
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