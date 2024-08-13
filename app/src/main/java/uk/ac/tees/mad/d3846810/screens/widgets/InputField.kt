package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3846810.theme.c10


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    error: MutableState<Boolean> = mutableStateOf(false),
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    errorMessage: String = "Empty",
    onValueChange: (value: String) -> Unit = {}
) {
    OutlinedTextField(modifier = modifier
        .fillMaxWidth(),
        value = value,
        isError = error.value,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        maxLines = 1,
        trailingIcon = {
            if (error.value) {
                Text(
                    modifier = modifier.padding(12.dp), text = errorMessage, color = Color.Red
                )
            }
        },
        onValueChange = {
            error.value = false
            onValueChange(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = c10,
            unfocusedIndicatorColor = Color.Gray,
            cursorColor = c10,
            focusedLabelColor = c10
        ),
        label = { Text(label) })
}

@Preview
@Composable
private fun InputFieldPreview() {
    InputField(value = "name", label = "Name") {}
}