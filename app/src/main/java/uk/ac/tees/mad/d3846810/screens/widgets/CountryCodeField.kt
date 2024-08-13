package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.theme.c10


@Composable
fun CountryCodeField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    error: MutableState<Boolean> = mutableStateOf(false),
    enabled: Boolean = true,
    onValueChange: (value: String) -> Unit = {}
) {
    val defaultCountryCode = stringResource(id = R.string.default_country_code)
    var cc by remember {
        mutableStateOf(
            TextFieldValue(
                value.ifEmpty { "+$defaultCountryCode" }, selection = TextRange(value.length)
            )
        )
    }
    OutlinedTextField(modifier = modifier.wrapContentWidth(),
        value = cc,
        isError = error.value,
        enabled = enabled,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
            error.value = false
            if (it.text.isEmpty()) {
                cc = TextFieldValue("+", selection = TextRange(1))
                onValueChange(cc.text)
                return@OutlinedTextField
            } else {
                cc = TextFieldValue(it.text, selection = TextRange(it.text.length))
                if (cc.text.length > 3) {
                    cc = TextFieldValue(cc.text.substring(0, 3), selection = TextRange(3))
                }
                cc = TextFieldValue(cc.text.replace("+", ""), selection = TextRange(2))
                cc = TextFieldValue("+${cc.text}", selection = TextRange(3))
                onValueChange(cc.text)
            }

//            if(it.text.contains("+")){
//                cc = TextFieldValue(it.text.replace("+", ""))
//            }
//            if (cc.text.length > 3) {
//                cc = TextFieldValue(cc.text.substring(0, 3))
//            }
//            cc = TextFieldValue("+${cc.text}")
//            onValueChange(cc.text)
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
    var cc by remember {
        mutableStateOf("")
    }
    CountryCodeField(value = cc, label = "") {}
}