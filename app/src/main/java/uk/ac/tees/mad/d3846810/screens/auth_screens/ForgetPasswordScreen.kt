package uk.ac.tees.mad.d3846810.screens.auth_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.InputField
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.theme.textColor
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun ForgetPasswordScreen(
    modifier: Modifier = Modifier,
    onConfirmEmail: (String) -> Unit = {},
    onBackPress: () -> Unit = {}
) {

    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = modifier.padding(16.dp)
        ) {
            Image(modifier = modifier
                .onClick { onBackPress() }
                .size(48.dp)
                .background(Color.White)
                .border(1.dp, liteGy, RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))
                .padding(4.dp),
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "")

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
            ) {
                CustomText(
                    modifier = modifier,
                    text = "Forget Password ?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                CustomText(
                    modifier = modifier.alpha(0.8.toFloat()),
                    text = stringResource(id = R.string.password_recovery_msg),
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = modifier.height(8.dp))
                InputField(value = email.value,
                    label = "Enter Registered Email",
                    error = emailError,
                    onValueChange = {
                        emailError.value = false
                        email.value = it
                    })

                Spacer(modifier = modifier.height(16.dp))
                CustomButton(label = "Confirm Email") {
                    if (email.value.isEmpty()) {
                        emailError.value = true
                    }
                    onConfirmEmail(email.value)
                }

                Spacer(modifier = modifier.height(16.dp))

            }
        }
    }
}

@Preview
@Composable
private fun PreviewSignUpScreen() {
    ForgetPasswordScreen()
}