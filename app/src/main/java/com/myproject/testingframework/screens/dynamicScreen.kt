package com.myproject.testingframework.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import com.myproject.composeflow.Components.Container.HorizontalContainer
import com.myproject.composeflow.Components.Container.VerticalContainer
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.Components.Layouts.Vertical.DynamicColumn
import com.myproject.composeflow.Components.Text.MultiLineInputText
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.SubtitleText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.TitleText
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.Authentication.extractType
import com.myproject.testingframework.itemlist.parseJson

@Composable
fun DynamicScreen(modifier: Modifier = Modifier, NavController: NavController) {
    val context = LocalContext.current

    val template = parseJson()[0]
    val content = parseJson()[1]

    val name = template["name"] as String
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
                                                                    isRequired = false,
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

                                                    "horizontalContainer" -> {
                                                        HorizontalContainer(content = {})
                                                    }

                                                    "layout" -> {
                                                        val layoutType =
                                                            tempItem1["orientation"] as String
                                                        val layoutItem =
                                                            tempItem1["items"] as List<Map<*, *>>
                                                        val count =
                                                            (tempItem1["itemCount"])?.toString()
                                                                ?.toFloatOrNull()?.toInt()
                                                                ?: layoutType.length
                                                        when (layoutType) {
                                                            "vertical" -> {
                                                                DynamicColumn(itemCount = count) {
                                                                    layoutItem.forEach {
                                                                        when (it["type"]) {
                                                                            "horizontalContainer" -> {
                                                                                val items =
                                                                                    it["items"] as List<Map<*, *>>
                                                                                HorizontalContainer(
                                                                                    modifier = Modifier.padding(
                                                                                        vertical = 5.dp
                                                                                    ),
                                                                                    content = {
                                                                                        items.forEach { containerItem ->
                                                                                            val type =
                                                                                                containerItem["type"] as String
                                                                                            when (type) {
                                                                                                "image" -> {
                                                                                                    val image_url =
                                                                                                        containerItem["image_url"] as String
                                                                                                    val imagePadding =
                                                                                                        paddingValues(
                                                                                                            containerItem["margins"]
                                                                                                        )
                                                                                                    AsyncImage(
                                                                                                        model = image_url,
                                                                                                        contentDescription = null,
                                                                                                        modifier = Modifier
                                                                                                            .height(
                                                                                                                60.dp
                                                                                                            )
                                                                                                            .then(
                                                                                                                imagePadding
                                                                                                            )
                                                                                                            .clip(
                                                                                                                shape = RoundedCornerShape(
                                                                                                                    10.dp
                                                                                                                )
                                                                                                            )
                                                                                                    )
                                                                                                }

                                                                                                "container" -> {
                                                                                                    val item =
                                                                                                        containerItem["items"] as List<Map<*, *>>
                                                                                                    VerticalContainer(
                                                                                                        modifier = Modifier.weight(
                                                                                                            1f
                                                                                                        ),
                                                                                                        contents = {
                                                                                                            item.forEach {
                                                                                                                val type =
                                                                                                                    when (val type =
                                                                                                                        extractType(
                                                                                                                            it["type"] as String
                                                                                                                        )) {
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
                                                                                                                            it["font_size"].toString()
                                                                                                                                .toFloat()
                                                                                                                                .toInt()
                                                                                                                        val font_weight =
                                                                                                                            fontWeightMap[it["font_weight"]]
                                                                                                                                ?: FontWeight.Black
                                                                                                                        val paddings =
                                                                                                                            paddingValues(
                                                                                                                                path = it["margins"]
                                                                                                                            )
                                                                                                                        val contentId =
                                                                                                                            it["#text"] as String

                                                                                                                        val text =
                                                                                                                            contItem1[contentId].toString()

                                                                                                                        TitleText(
                                                                                                                            text = text,
                                                                                                                            fontWeight = font_weight,
                                                                                                                            fontSize = font_size,
                                                                                                                            modifier = Modifier.then(
                                                                                                                                paddings
                                                                                                                            )
                                                                                                                        )
                                                                                                                    }

                                                                                                                    "SubtitleText" -> {
                                                                                                                        val font_size =
                                                                                                                            it["font_size"].toString()
                                                                                                                                .toFloat()
                                                                                                                                .toInt()
                                                                                                                        val font_weight =
                                                                                                                            fontWeightMap[it["font_weight"]]
                                                                                                                                ?: FontWeight.Black
                                                                                                                        val paddings =
                                                                                                                            paddingValues(
                                                                                                                                path = it["margins"]
                                                                                                                            )
                                                                                                                        val contentId =
                                                                                                                            it["#text"] as String

                                                                                                                        val text =
                                                                                                                            contItem1[contentId].toString()

                                                                                                                        SubtitleText(
                                                                                                                            text = text,
                                                                                                                            fontSize = font_size,
                                                                                                                            fontWeight = font_weight,
                                                                                                                            modifier = Modifier.then(
                                                                                                                                paddings
                                                                                                                            )
                                                                                                                        )
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    )
                                                                                                }

                                                                                                "button" -> {
                                                                                                    val buttonType =
                                                                                                        containerItem["subtype"]
                                                                                                            ?: "elevated"
                                                                                                    val buttonPadding =
                                                                                                        paddingValues(
                                                                                                            path = containerItem["margins"]
                                                                                                        )
                                                                                                    val action =
                                                                                                        containerItem["action"] as Map<*, *>
                                                                                                    val onclick: () -> Unit =
                                                                                                        {
                                                                                                            action.let { action ->
                                                                                                                when (action["type"]) {
                                                                                                                    "toast" -> {
                                                                                                                        val message =
                                                                                                                            action["message"] as String
                                                                                                                        val duration =
                                                                                                                            action["duration"] as String

                                                                                                                        ActionType_SnackBar(
                                                                                                                            context = context,
                                                                                                                            message = message,
                                                                                                                            duration = duration
                                                                                                                        )
                                                                                                                    }

                                                                                                                }
                                                                                                            }
                                                                                                        }


                                                                                                    when (buttonType) {
                                                                                                        "icon" -> {
                                                                                                            val icon =
                                                                                                                containerItem["icon"] as String
                                                                                                            ButtonIcon(
                                                                                                                onclick = onclick,
                                                                                                                icon = icon,
                                                                                                                modifier = Modifier.then(
                                                                                                                    buttonPadding
                                                                                                                )
                                                                                                            )
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    })
                                                                            }
                                                                            "verticalContainer" -> {

                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            "horizontal" -> {

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


fun replacePlaceholders(jsonString: String, replacementString: String): String {
    return jsonString.replace(Regex("\\{\\{(.*?)\\}\\}"), replacementString)
}
