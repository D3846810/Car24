package uk.ac.tees.mad.d3846810.screens.auth_screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.InputField
import uk.ac.tees.mad.d3846810.screens.widgets.PasswordInputField
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    buttonLoginTxt: String,
    onLogin: (String, String) -> Unit,
    onLoginWithGoogle: () -> Unit,
    onCreateAccount: () -> Unit,
    onForgetPassword: () -> Unit,

    ) {
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val pass = remember { mutableStateOf("") }
    val passError = remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        val (inputLayout) = createRefs()
        Column(
            modifier
                .fillMaxWidth()
                .constrainAs(inputLayout) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomText(
                modifier = modifier.padding(top = 8.dp),
                text = "Manage Admin Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            CustomText(
                modifier = modifier.alpha(0.8.toFloat()),
                text = "Please Enter Admin email & password to \nlogin access to Admin app",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
            InputField(value = email.value,
                label = "Enter Email",
                error = emailError,
                onValueChange = {
                    emailError.value = false
                    email.value = it
                })
            PasswordInputField(value = pass.value,
                label = "Enter Password",
                isError = passError,
                onValueChange = {
                    passError.value = false
                    pass.value = it
                })
            Spacer(modifier = modifier.height(8.dp))

            CustomText(modifier = modifier
                .onClick {
                    onForgetPassword()
                }
                .align(Alignment.End)
                .alpha(0.8.toFloat()),
                text = "Forget Password ?",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)
            Spacer(modifier = modifier.height(16.dp))
            CustomButton(label = buttonLoginTxt) {
                if (email.value.isEmpty()) {
                    emailError.value = true
                } else if (pass.value.isEmpty()) {
                    passError.value = true
                } else {
                    onLogin(email.value, pass.value)
                }
            }
            Spacer(modifier = modifier.height(24.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = modifier
                        .weight(0.4f)
                        .height(0.7.dp)
                        .background(color = liteGy)
                )
                CustomText(
                    modifier = modifier.weight(0.2f), text = "Or", textAlign = TextAlign.Center
                )
                HorizontalDivider(
                    modifier = modifier
                        .weight(0.4f)
                        .height(0.7.dp)
                        .background(color = liteGy)
                )
            }
            Spacer(modifier = modifier.height(24.dp))

            CustomButton(
                trailingIcon = R.drawable.ic_google,
                isColorIcon = true,
                textColor = c10,
                borderColor = c10,
                backgroundColor = Color.White,
                label = "Login with Google"
            ) {
                onLoginWithGoogle()
            }
            Spacer(modifier = modifier.height(8.dp))
            CustomButton(
                label = "Create Account", textColor = c10,
                borderColor = c10,
                backgroundColor = Color.White,
            ) {
                onCreateAccount()
            }
            Spacer(modifier = modifier.height(24.dp))

        }
    }
}

@Preview
@Composable
private fun PreviewManageLoginScreen() {
    LoginScreen(modifier = Modifier, "Login", { email, pass ->
    }, {}, {}, {})
}