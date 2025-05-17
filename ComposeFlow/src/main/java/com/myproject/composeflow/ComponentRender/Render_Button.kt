package com.myproject.composeflow.ComponentRender

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Alert
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SimpleDialog
import com.myproject.composeflow.Actions.Button_Actions.ActionType_SnackBar
import com.myproject.composeflow.Actions.Button_Actions.ActionType_Url
import com.myproject.composeflow.Components.Button.ButtonElevated
import com.myproject.composeflow.Components.Button.ButtonFilled
import com.myproject.composeflow.Components.Button.ButtonFloatingAction
import com.myproject.composeflow.Components.Button.ButtonIcon
import com.myproject.composeflow.Components.Button.ButtonText
import com.myproject.composeflow.Utils.Padding.paddingValues
import com.myproject.composeflow.Utils.mapFontWeight
import com.myproject.composeflow.Utils.searchData
import kotlin.collections.get

@Composable
fun Render_Button(
    modifier: Modifier = Modifier,
    dataMap: Map<*, *>,
    context: Context,
    NavController: NavController,
    navigationFunc: (() -> Unit)?,
    alertFunc: (String, String) -> Unit,
) {


    val buttonType =
        dataMap["subtype"] ?: "elevated"

    val font_size =
        (dataMap["font_size"] as? Number)?.toInt() ?: 0
    val font_weight =
        mapFontWeight(dataMap["font_weight"] as? String ?: "")
    val buttonPadding =
        paddingValues(path = dataMap["padding"])
    val bgColor =
        (dataMap["backgroundColor"] as? String)
            ?: ""

    val icon =
        dataMap["icon"] as? String ?: ""

    val action =
        dataMap["action"] as Map<*, *>
    val onclick: () -> Unit = {
        action.let { act ->
            when (act["type"]) {
                "toast" -> {
                    val message =
                        act["message"] as? String ?: "No Message"
                    val duration = act["duration"] as? String ?: "short"

                    ActionType_SnackBar(
                        context = context,
                        message = message,
                        duration = duration
                    )
                }

                "navigate" -> {
                    val destinationScreen =
                        act["destination"] as? String ?: ""
                    val actionType = act["actionType"] as? String ?: ""

                    navigationFunc?.invoke()

                    NavController.navigate(destinationScreen)
                }

                "dialog" -> {
                    val subType = act["subtype"] as? String ?: "simple"
                    when (subType) {
                        "alert" -> {
                            val title =
                                act["title"] as? String ?: "No title"
                            val message =
                                act["message"] as? String
                                    ?: "No Message"

                            alertFunc(title, message)

                        }

                        "simple" -> {
                            val title =
                                act["title"] as? String ?: "No title"
                            val message =
                                act["message"] as? String
                                    ?: "No Message"

                            alertFunc(title, message)
                        }
                    }
                }

                "url" -> {
                    val url = act["url"] as? String ?: ""
                    ActionType_Url(url = url, context = context)
                }

                "" -> {
                    if (buttonType == "icon" && icon == "arrow_back") {
                        NavController.popBackStack()
                    }
                }
            }
        }
    }


    val text = if (dataMap["text"] != null) {
        dataMap["text"] as? String ?: ""
    } else {
        searchData(key = (dataMap["\$text"] as? String ?: ""))
    }


    when (buttonType) {
        "elevated" -> {
            ButtonElevated(
                text = text,
                bgColor = bgColor,
                textColor = "#fcfdff",
                fontSize = font_size,
                fontWeight = font_weight,
                onclick = onclick,
                modifier = modifier
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
                modifier = modifier
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
                modifier = modifier
                    .then(buttonPadding),
            )
        }

        "icon" -> {
            ButtonIcon(
                modifier = modifier,
                onclick = onclick,
                icon = icon
            )
        }

        "floatingAction" -> {
            ButtonFloatingAction(
                onclick = onclick,
                icon = icon,
                modifier = modifier
            )
        }
    }
}
