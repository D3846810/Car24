package uk.ac.tees.mad.d3846810.screens.auth_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.gray
import uk.ac.tees.mad.d3846810.utils.Constants

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    onSignUp: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier.size(120.dp),
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "app icon"
            )
            Spacer(modifier = modifier.height(8.dp))
            CustomText(
                modifier = modifier,
                text = stringResource(id = R.string.app_name).uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold

            )

            Spacer(modifier = modifier.height(8.dp))
            CustomText(
                modifier = modifier
                    .align(Alignment.Start)
                    .alpha(0.9.toFloat()),
                text = Constants.ABOUT_US,
                fontSize = 14.sp,
                color = gray
            )

            Spacer(modifier = modifier.height(16.dp))

            CustomButton(label = "Login") {
                onLogin()
            }
            Spacer(modifier = modifier.height(16.dp))

            CustomButton(label = "Sign Up", backgroundColor = Color.White, textColor = c10, borderColor = c10) {
                onSignUp()
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWelcomeScreen() {
    WelcomeScreen(onLogin = {}, onSignUp = {})
}