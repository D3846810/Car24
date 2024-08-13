package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.theme.c10

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    label: String,
    trailingIcon: Int? = null,
    isColorIcon: Boolean = false,
    textColor: Color = Color.White,
    backgroundColor: Color = c10,
    borderColor: Color = Color.Transparent,
    onClick: () -> Unit
) {
    Button(modifier = modifier
        .fillMaxWidth()
        .height(48.dp)
        .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, backgroundColor = backgroundColor,
        ),
        onClick = {
            onClick()
        }) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            trailingIcon?.let {
                if (isColorIcon) {
                    Image(
                        modifier = modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "color icon"
                    )
                } else {
                    Icon(
                        modifier = modifier.size(20.dp),
                        painter = painterResource(id = it),
                        contentDescription = "button icon",
                        tint = Color.White
                    )
                }
                Spacer(modifier = modifier.width(8.dp))
            }

            Text(label, color = textColor)
        }
    }
}

@Preview
@Composable
private fun PreviewCustomButton() {
    CustomButton(trailingIcon = R.drawable.ic_google, isColorIcon = false, label = "Button") {

    }
}