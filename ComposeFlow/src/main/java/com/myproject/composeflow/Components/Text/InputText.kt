package com.myproject.composeflow.Components.Text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myproject.composeflow.Components.Design.paddingValues
import com.myproject.composeflow.globalMap.GlobalMap

//val keyboardType
//val font_size
//val font_weight
//val paddings
//val suffixIcon

@Composable
fun SingleLineInputText(
    modifier: Modifier = Modifier,
    id: String,
    keyboardType: KeyboardType,
    font_size: Int = 22,
    fontWeight: FontWeight?,
    suffixIcon: String? = null,
    hintText: String
) {

    val visualTransformation = if (keyboardType == KeyboardType.Password) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    var input by remember { mutableStateOf("") }
    GlobalMap.globalMap.getOrPut(id) { input }

    OutlinedTextField(
        textStyle = TextStyle(
            fontSize = font_size.sp,
            color = Color.Black,
            fontWeight = fontWeight ?: FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        onValueChange = { input = it },
        value = input,
        maxLines = 1,
        leadingIcon = {
            suffixIcon?.lowercase().let { icon ->
                mapStringToIcon[icon]?.let {
                    Icon(it, contentDescription = null)
                }
            }
        },
        placeholder = {
            Text(
                hintText,
                style = TextStyle(fontSize = 20.sp, color = Color.Black)
            )
        },
        modifier = modifier
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
    "phone" to Icons.Outlined.Phone,
)