package com.myproject.composeflow.Components.Text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.icons.filled.EditLocationAlt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.util.Logger
import com.myproject.composeflow.Components.Design.paddingValues


@Composable
fun SingleLineInputText(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType,
    font_size: Int = 22,
    fontWeight: FontWeight?,
    suffixIcon: String? = null,
    hintText: String,
    value: String,
    onValueChange: (String) -> Unit,
    isRequired: Boolean,
    onValidationChange: (Boolean) -> Unit = {}
) {

    var isTouched by remember { mutableStateOf(false) }
    val showError = isRequired && isTouched && value.isEmpty()

    LaunchedEffect(showError) {
        onValidationChange((!showError))
    }

    OutlinedTextField(
        textStyle = TextStyle(
            fontSize = font_size.sp,
            color = if (showError) Color.Red else Color.Black,
            fontWeight = fontWeight ?: FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        onValueChange = {
            onValueChange(it)
            if (it.isNotEmpty()) isTouched = true
        },
        isError = showError,
        value = value,
        maxLines = 1,
        leadingIcon = suffixIcon?.lowercase()?.let { icon ->
            mapStringToIcon[icon]?.let {
                { Icon(it, contentDescription = null) }
            }
        },
        trailingIcon = {
            if (showError) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        },
        label = {
            Text(
                hintText,
                style = TextStyle(
                    fontSize = font_size.sp,
                    color = if (showError) Color.Red else Color.Black
                )
            )
        },
        supportingText = {
            if (showError) {
                Text(
                    text = "This field is required",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        },

        modifier = modifier
            .onFocusChanged { focusState ->
                if (!focusState.isFocused) {
                    isTouched = true
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Gray.copy(alpha = 0.1f),
            unfocusedContainerColor = Color.Gray.copy(alpha = 0.1f),
//            errorContainerColor = Color.Red.copy(alpha = 0.1f),
            focusedBorderColor = if (showError) Color.Red else Color.Gray,
            unfocusedBorderColor = if (showError) Color.Red else Color.Gray,
            errorBorderColor = Color.Red
        )
    )
}

@Composable
fun MultiLineInputText(modifier: Modifier = Modifier) {

    var ip by remember { mutableStateOf("") }

    OutlinedTextField(
        textStyle = TextStyle(fontSize = 22.sp, color = Color.Black),
        onValueChange = { ip = it },
        value = ip,
        placeholder = {
            Text(
                "MultiLinePlaceholder...",
                style = TextStyle(fontSize = 20.sp, color = Color.Black)
            )
        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
    )
}

val mapToKeyboardType = mapOf(
    "text" to KeyboardType.Text,
    "number" to KeyboardType.Number,
    "phone" to KeyboardType.Phone,
    "email" to KeyboardType.Email,
    "password" to KeyboardType.Password,
    "decimal" to KeyboardType.Decimal,
    "uri" to KeyboardType.Uri,
)

val mapStringToIcon = mapOf(
    "person" to Icons.Outlined.Person,
    "lock" to Icons.Outlined.Lock,
    "email" to Icons.Outlined.Email,
    "call" to Icons.Outlined.Phone,
    "share" to Icons.Default.Share,
    "settings" to Icons.Default.Settings,
    "info" to Icons.Outlined.Info,
    "location" to Icons.Outlined.LocationOn,
    "arrow_down" to Icons.Outlined.KeyboardArrowDown,
    "arrow_up" to Icons.Outlined.KeyboardArrowUp,
    "message" to Icons.Filled.Sms,
    "whatsapp" to Icons.Filled.Whatsapp,
    "status" to Icons.Filled.AccessTime,
    "activity" to Icons.Filled.CalendarToday,
    "check-in" to Icons.Filled.EditLocationAlt,
    "description" to Icons.Outlined.Description,
    "information" to Icons.Outlined.Info
)