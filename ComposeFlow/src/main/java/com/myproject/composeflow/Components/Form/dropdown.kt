package com.myproject.composeflow.Components.Form

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownOption(
    modifier: Modifier = Modifier,
    font_size: Int = 22,
    fontWeight: FontWeight?,
    hintText: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    isRequired: Boolean = false,
    onValidationChange: (Boolean) -> Unit = {}
) {

    var isTouched by remember { mutableStateOf(false) }
    val showError = isRequired && isTouched && selectedOption.isEmpty()

    LaunchedEffect(showError) {
        onValidationChange(!showError)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            textStyle = TextStyle(
                fontSize = font_size.sp,
                color = if (showError) Color.Red else Color.Black,
                fontWeight = fontWeight ?: FontWeight.Normal
            ),
            readOnly = true,
            value = selectedOption,
            onValueChange = onOptionSelected,
            trailingIcon = {
                if (showError) {
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = "Error",
                            tint = Color.Red
                        )
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        Spacer(Modifier.width(5.dp))
                    }
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }
            },
            placeholder = {
                Text(
                    hintText,
                    style = TextStyle(
                        color = if (showError) Color.Red else LocalContentColor.current
                    )
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = if (showError) Color.Red.copy(alpha = 0.1f)
                else Color.Gray.copy(alpha = 0.1f),
                unfocusedContainerColor = if (showError) Color.Red.copy(alpha = 0.1f)
                else Color.Gray.copy(alpha = 0.1f),
//                errorContainerColor = Color.Red.copy(alpha = 0.1f),
                focusedBorderColor = if (showError) Color.Red else Color.Gray,
                unfocusedBorderColor = if (showError) Color.Red else Color.Gray,
                errorBorderColor = Color.Red
            ),
            isError = showError,
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
                .menuAnchor()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isTouched = true
                    }
                },
            shape = RoundedCornerShape(12.dp),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(expanded) },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        onExpandedChange(expanded)
                        isTouched = true
                    }
                )
            }
        }
    }

}

