package com.myproject.testingframework.SignUpForm

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.myproject.composeflow.Components.Form.DropDownOption
import com.myproject.composeflow.Components.Layouts.Vertical.DynamicColumn
import com.myproject.composeflow.Components.Text.MultiLineInputText
import com.myproject.composeflow.Components.Text.SingleLineInputText
import com.myproject.composeflow.Components.Text.TextBlock
import com.myproject.composeflow.Components.Text.fontWeightMap
import com.myproject.composeflow.Components.Text.mapToKeyboardType
import com.myproject.composeflow.globalMap.textFieldValues
import com.myproject.testingframework.Authentication.extractType
import com.myproject.testingframework.mvvm_Arc.viewmodel.MyViewModel

@Composable
fun SignupScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val template = JsonFileParsing(context = context)[0]
    val content = JsonFileParsing(context = context)[1]

    val name = template["name"] as String
    val orientation = template["orientation"]
    val templatePadding = paddingValues(path = template["paddings"])
    val bgColor =
        template["background"]?.toString()?.runCatching { Color(toColorInt()) }?.getOrNull()
            ?: Color.White

    val templateItems = template["items"] as List<Map<*, *>>
    val contentItems = content["items"] as List<Map<*, *>>

    var showalertdialog by remember { mutableStateOf(false) }
    var showsimpledialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }


    //signup form
    val newUserData = mutableListOf<Pair<String, String>>()

    //quotesApi
//    val vm: viewmodel = hiltViewModel()
//    val quotesData = vm.quote.collectAsState()

//    val data = mapOf(
//        "quote" to (quotesData.value["quote"] ?: ""),
//        "author" to (quotesData.value["author"] ?: "")
//    )

    //MyApi
    val myvm : MyViewModel = hiltViewModel()
    val organizationList = myvm.organizationList.collectAsState()


    Column(
        modifier = Modifier
            .then(templatePadding)
            .let { Modifier.background(bgColor) }
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        contentItems.forEach { contentItem ->
            when (contentItem["type"]) {
                name -> {
                    when (orientation ?: "vertical") {
                        "vertical" -> {
                            VerticalContainer(
                                contents = {
                                    templateItems.forEach { templateItem ->
                                        val type =
                                            when (val type =
                                                extractType(templateItem["type"] as String)) {
                                                Pair("text", "title") -> "TitleText"
                                                Pair("text", "body") -> "SubtitleText"
                                                Pair("text", "Subtitle") -> "SubtitleText"
                                                else -> type.first
                                            }
                                        when (type) {
                                            "verticalContainer" -> {
                                                val items = templateItem["items"] as List<Map<*, *>>
                                                items.forEach { item2 ->
                                                    val type2 = item2["type"] as String
                                                    when (type2) {
                                                        "boxContainer" -> {
                                                            val boxPadding =
                                                                paddingValues(item2["paddings"])
                                                            val wrap =
                                                                if (item2["contentHeight"] == "wrap") true else false
                                                            BoxContainer(modifier = Modifier
                                                                .then(
                                                                    boxPadding
                                                                )
                                                                .clip(
                                                                    shape = RoundedCornerShape(
                                                                        10.dp
                                                                    )
                                                                )
                                                                .background(
                                                                    Color.White,
                                                                    shape = RoundedCornerShape(5.dp)
                                                                ),
                                                                wrapContentHeight = wrap,
                                                                content = {
                                                                    val items =
                                                                        item2["items"] as List<Map<*, *>>
                                                                    Column {
                                                                        items.forEach { item3 ->
                                                                            val type3 =
                                                                                when (val type =
                                                                                    extractType(
                                                                                        item3["type"] as String
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

                                                                                    Pair(
                                                                                        "text",
                                                                                        ""
                                                                                    ) -> "TextBlock"

                                                                                    else -> type.first
                                                                                }
                                                                            when (type3) {
                                                                                "TextBlock" -> {
                                                                                    val font_size =
                                                                                        (item3["font_size"].toString()).toFloat()
                                                                                            .toInt()
                                                                                    val font_weight =
                                                                                        fontWeightMap[item3["font_weight"]]
                                                                                    val bgColor =
                                                                                        item3["bgColor"]
                                                                                    val textPadding =
                                                                                        paddingValues(
                                                                                            item3["margins"]
                                                                                        )

                                                                                    val contentId =
                                                                                        item3["#text"] as String
                                                                                    val text =
                                                                                        contentItem[contentId].toString()

                                                                                    TextBlock(
                                                                                        text = text,
                                                                                        fontSize = font_size,
                                                                                        fontWeight = font_weight,
                                                                                        modifier = Modifier
                                                                                            .fillMaxWidth()
                                                                                            .then(
                                                                                                textPadding
                                                                                            )
                                                                                            .background(
                                                                                                Color(
                                                                                                    bgColor.toString()
                                                                                                        .toColorInt()
                                                                                                )
                                                                                            )
                                                                                    )
                                                                                }

                                                                                "inputText" -> {
                                                                                    when ((item3["maxLines"] as? Number)?.toInt()
                                                                                        ?: 1) {
                                                                                        1 -> {
                                                                                            val keyboardType =
                                                                                                mapToKeyboardType[item3["keyboardType"]]
                                                                                            val font_size =
                                                                                                (item3["font_size"] as Number).toInt()
                                                                                            val font_weight =
                                                                                                fontWeightMap[item3["font_weight"]]
                                                                                            val inputpaddings =
                                                                                                paddingValues(
                                                                                                    path = item3["margins"]
                                                                                                )
                                                                                            val suffixIcon =
                                                                                                item3["suffixIcon"]
                                                                                            val isRequired =
                                                                                                item3["isRequired"] as? Boolean
                                                                                                    ?: false
                                                                                            val contentId =
                                                                                                item3["#text"] as String

                                                                                            val text =
                                                                                                contentItem[contentId].toString()

                                                                                            var controller by remember(
                                                                                                contentId
                                                                                            ) {
                                                                                                mutableStateOf(
                                                                                                    ""
                                                                                                )
                                                                                            }

                                                                                            SingleLineInputText(
                                                                                                keyboardType = keyboardType
                                                                                                    ?: KeyboardType.Text,
                                                                                                font_size = font_size,
                                                                                                fontWeight = font_weight,
                                                                                                suffixIcon = suffixIcon.toString(),
                                                                                                hintText = text,
                                                                                                value = controller,
                                                                                                isRequired = isRequired,
                                                                                                onValueChange = { newValue ->
//                                                                                                    val index =
//                                                                                                        textFieldValues.indexOfFirst { it.first == contentId }
//                                                                                                    if (index != -1) {
//                                                                                                        textFieldValues[index] =
//                                                                                                            textFieldValues[index].copy(
//                                                                                                                second = newValue
//                                                                                                            )
//                                                                                                    } else {
//                                                                                                        textFieldValues.add(
//                                                                                                            contentId to newValue
//                                                                                                        )
//                                                                                                    }
                                                                                                    controller =
                                                                                                        newValue

                                                                                                    val index =
                                                                                                        newUserData.indexOfFirst { it.first == text }
                                                                                                    if (index != -1) {
                                                                                                        newUserData[index] =
                                                                                                            text to newValue
                                                                                                    } else {
                                                                                                        newUserData.add(
                                                                                                            text to newValue
                                                                                                        )
                                                                                                    }
                                                                                                },
                                                                                                modifier = Modifier
                                                                                                    .then(
                                                                                                        inputpaddings
                                                                                                    )
                                                                                                    .fillMaxWidth()
                                                                                            )

                                                                                        }

                                                                                        else -> {
                                                                                            MultiLineInputText()
                                                                                        }
                                                                                    }
                                                                                }

                                                                                "horizontalContainer" -> {
                                                                                    val horizontalItem =
                                                                                        item3["items"] as List<Map<*, *>>
                                                                                    HorizontalContainer(
                                                                                        content = {
                                                                                            horizontalItem.forEach {
                                                                                                val type4 =
                                                                                                    it["type"] as String
                                                                                                when (type4) {
                                                                                                    "inputText" -> {
                                                                                                        when ((it["maxLines"] as? Number)?.toInt()
                                                                                                            ?: 1) {
                                                                                                            1 -> {
                                                                                                                val keyboardType =
                                                                                                                    mapToKeyboardType[it["keyboardType"]]
                                                                                                                val font_size =
                                                                                                                    (it["font_size"] as Number).toInt()
                                                                                                                val font_weight =
                                                                                                                    fontWeightMap[it["font_weight"]]
                                                                                                                val inputpaddings =
                                                                                                                    paddingValues(
                                                                                                                        path = it["margins"]
                                                                                                                    )
                                                                                                                val suffixIcon =
                                                                                                                    it["suffixIcon"]
                                                                                                                val contentId =
                                                                                                                    it["#text"] as String
                                                                                                                val isRequired =
                                                                                                                    it["isRequired"] as? Boolean
                                                                                                                        ?: false

                                                                                                                val text =
                                                                                                                    contentItem[contentId].toString()

                                                                                                                var controller by remember(
                                                                                                                    contentId
                                                                                                                ) {
                                                                                                                    mutableStateOf(
                                                                                                                        ""
                                                                                                                    )
                                                                                                                }

                                                                                                                SingleLineInputText(
                                                                                                                    keyboardType = keyboardType
                                                                                                                        ?: KeyboardType.Text,
                                                                                                                    font_size = font_size,
                                                                                                                    fontWeight = font_weight,
                                                                                                                    suffixIcon = suffixIcon.toString(),
                                                                                                                    hintText = text,
                                                                                                                    value = controller,
                                                                                                                    isRequired = isRequired,
                                                                                                                    onValueChange = { newValue ->
                                                                                                                        controller =
                                                                                                                            newValue

                                                                                                                        val index =
                                                                                                                            newUserData.indexOfFirst { it.first == text }
                                                                                                                        if (index != -1) {
                                                                                                                            newUserData[index] =
                                                                                                                                text to newValue
                                                                                                                        } else {
                                                                                                                            newUserData.add(
                                                                                                                                text to newValue
                                                                                                                            )
                                                                                                                        }
                                                                                                                    },
                                                                                                                    modifier = Modifier
                                                                                                                        .then(
                                                                                                                            inputpaddings
                                                                                                                        )
                                                                                                                        .weight(
                                                                                                                            1f
                                                                                                                        )
                                                                                                                )
                                                                                                            }

                                                                                                            else -> {
                                                                                                                MultiLineInputText()
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    )
                                                                                }

                                                                                "dropdown" -> {
                                                                                    val contentId =
                                                                                        item3["#text"] as String

                                                                                    var selectedOption by remember(
                                                                                        contentId
                                                                                    ) {
                                                                                        mutableStateOf(
                                                                                            ""
                                                                                        )
                                                                                    }
                                                                                    var expanded by remember(
                                                                                        contentId
                                                                                    ) {
                                                                                        mutableStateOf(
                                                                                            false
                                                                                        )
                                                                                    }

                                                                                    val font_size =
                                                                                        (item3["font_size"] as Number).toInt()
                                                                                    val font_weight =
                                                                                        fontWeightMap[item3["font_weight"]]
                                                                                    val dropdownPadding =
                                                                                        paddingValues(
                                                                                            path = item3["margins"]
                                                                                        )

                                                                                    val hintText =
                                                                                        contentItem[contentId].toString()

                                                                                    val options =
                                                                                        item3["options"] as List<String>
                                                                                    val isRequired =
                                                                                        item3["isRequired"] as? Boolean
                                                                                            ?: false

                                                                                    DropDownOption(
                                                                                        modifier = Modifier
                                                                                            .fillMaxWidth()
                                                                                            .then(
                                                                                                dropdownPadding
                                                                                            ),
                                                                                        fontWeight = font_weight,
                                                                                        font_size = font_size,
                                                                                        isRequired = isRequired,
                                                                                        hintText = hintText,
                                                                                        options = options,
                                                                                        selectedOption = selectedOption,
                                                                                        onOptionSelected = {
                                                                                            selectedOption =
                                                                                                it
                                                                                            val index =
                                                                                                newUserData.indexOfFirst { it.first == hintText }
                                                                                            if (index != -1) {
                                                                                                newUserData[index] =
                                                                                                    hintText to it
                                                                                            } else {
                                                                                                newUserData.add(
                                                                                                    hintText to it
                                                                                                )
                                                                                            }
                                                                                        },
                                                                                        expanded = expanded,
                                                                                        onExpandedChange = {
                                                                                            expanded =
                                                                                                !expanded
                                                                                        }
                                                                                    )
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                            "boxContainer" -> {
                                                val bgColor =
                                                    if (templateItem["bgColor"] != null) templateItem["bgColor"] as String else null
                                                val wrap =
                                                    if (templateItem["contentHeight"] == "wrap") true else false
                                                val paddings =
                                                    paddingValues(path = templateItem["paddings"])
                                                val items = templateItem["items"] as List<Map<*, *>>
                                                val alignment =
                                                    if (templateItem["align"] != null) mapStringToAlignment(
                                                        templateItem["align"] as String
                                                    ) else null


                                                BoxContainer(
                                                    wrapContentHeight = wrap,
                                                    alignment = alignment,
                                                    modifier = Modifier
                                                        .then(paddings)
                                                        .let {
                                                            if (bgColor != null) Modifier.background(
                                                                Color(bgColor.toColorInt())
                                                            ) else Modifier
                                                        },
                                                    content = {
                                                        items.forEach { boxitem ->
                                                            val itemtype =
                                                                when (val type =
                                                                    extractType(boxitem["type"] as String)) {
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
                                                            when (itemtype) {
                                                                "boxContainer" -> {
                                                                    val bgColor =
                                                                        if (boxitem["bgColor"] != null) boxitem["bgColor"] as String else null
                                                                    val wrap =
                                                                        if (boxitem["contentHeight"] == "wrap") true else false
                                                                    val paddings =
                                                                        paddingValues(path = boxitem["paddings"])
                                                                    val items =
                                                                        boxitem["items"] as List<Map<*, *>>
                                                                    val alignment =
                                                                        if (boxitem["align"] != null) mapStringToAlignment(
                                                                            boxitem["align"] as String
                                                                        ) else null

                                                                    BoxContainer(
                                                                        alignment = alignment,
                                                                        wrapContentHeight = wrap,
                                                                        modifier = Modifier
                                                                            .then(
                                                                                paddings
                                                                            )
                                                                            .let {
                                                                                if (bgColor != null) Modifier.background(
                                                                                    Color(bgColor.toColorInt())
                                                                                ) else Modifier
                                                                            },
                                                                        content = {
                                                                            items.forEach { boxitem2 ->
                                                                                val type2 =
                                                                                    when (val type =
                                                                                        extractType(
                                                                                            boxitem2["type"] as String
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
                                                                                when (type2) {
                                                                                    "verticalContainer" -> {
                                                                                        val items =
                                                                                            boxitem2["items"] as List<Map<*, *>>
                                                                                        VerticalContainer(
                                                                                            wrapContentHeight = true,
                                                                                            contents = {
                                                                                                items.forEach { containerItem ->
                                                                                                    val type3 =
                                                                                                        when (val type =
                                                                                                            extractType(
                                                                                                                containerItem["type"] as String
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
                                                                                                    when (type3) {
                                                                                                        "SubtitleText" -> {
                                                                                                            val font_size =
                                                                                                                (containerItem["font_size"] as Number).toInt()
                                                                                                            val font_weight =
                                                                                                                fontWeightMap[containerItem["font_weight"]]
                                                                                                            val paddings =
                                                                                                                paddingValues(
                                                                                                                    containerItem["margins"]
                                                                                                                )
                                                                                                            val align =
                                                                                                                if (containerItem["align"] != null) mapStringToAlignment(
                                                                                                                    containerItem["align"] as String
                                                                                                                ) else null
                                                                                                            val text =
                                                                                                                ""

                                                                                                            TextBlock(
                                                                                                                text = text,
                                                                                                                fontSize = font_size,
                                                                                                                fontWeight = font_weight,
                                                                                                                alignment = align,
                                                                                                                modifier = Modifier
                                                                                                                    .then(
                                                                                                                        paddings
                                                                                                                    )
                                                                                                                    .fillMaxWidth()
                                                                                                            )
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            },
                                                                                            modifier = Modifier
                                                                                        )
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                            )
                        }

                        "horizontal" -> {

                        }
                    }
                }

                else -> {
                    val type = contentItem["type"] as String
                    when (type) {
                        "button" -> {
                            val buttonType =
                                contentItem["subtype"] ?: "elevated"

                            val font_size =
                                (contentItem["font_size"] as Number).toInt()
                            val font_weight =
                                fontWeightMap[contentItem["font_weight"]]
                                    ?: FontWeight.Normal
                            val buttonPadding =
                                paddingValues(path = contentItem["margins"])
                            val bgColor =
                                (contentItem["backgroundColor"] as String)
                            val action =
                                contentItem["action"] as Map<*, *>
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
                                                    dialogMessage = newUserData.toString()
                                                    showalertdialog = true
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

                            val text = contentItem["#text"] as String


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
                                        contentItem["icon"] as String
                                    ButtonIcon(
                                        onclick = onclick,
                                        icon = icon
                                    )
                                }

                                "floatingAction" -> {
                                    val icon =
                                        contentItem["icon"] as String
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
        }
    }
}

fun mapStringToAlignment(alignment: String): Alignment {
    return when (alignment.lowercase()) {
        "topstart" -> Alignment.TopStart
        "topcenter" -> Alignment.TopCenter
        "end" -> Alignment.TopEnd
        "centerstart" -> Alignment.CenterStart
        "center" -> Alignment.Center
        "centerend" -> Alignment.CenterEnd
        "bottomstart" -> Alignment.BottomStart
        "bottomcenter" -> Alignment.BottomCenter
        "bottomend" -> Alignment.BottomEnd
        else -> Alignment.TopStart // Default fallback
    }
}