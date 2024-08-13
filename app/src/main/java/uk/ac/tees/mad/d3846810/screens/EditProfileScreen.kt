package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.screens.widgets.CountryCodeField
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.InputField
import uk.ac.tees.mad.d3846810.theme.gray
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.theme.textColor
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    userModel: UserModel = UserModel(),
    onUpdateAccount: (UserModel) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    val firstName = remember { mutableStateOf(userModel.firstName) }
    val firstNameError = remember { mutableStateOf(false) }
    val lastName = remember { mutableStateOf(userModel.lastName) }
    val lastNameError = remember { mutableStateOf(false) }
    val phoneNumber = remember { mutableStateOf(userModel.phoneNumber) }
    val phoneNumberError = remember { mutableStateOf(false) }
    var countryCode by remember { mutableStateOf(userModel.countryCode) }
    val countryCodeError = remember { mutableStateOf(false) }
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    modifier = modifier,
                    text = "Update Your Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
                CustomText(
                    modifier = modifier.alpha(0.8.toFloat()),
                    text = "Looks like you want to edit your account\nor connect with social accounts",
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                InputField(
                    value = firstName.value,
                    label = "First Name",
                    error = firstNameError,
                    onValueChange = {
                        firstNameError.value = false
                        firstName.value = it
                    })
                InputField(
                    value = lastName.value,
                    label = "Last Name",
                    error = lastNameError,
                    onValueChange = {
                        lastNameError.value = false
                        lastName.value = it
                    })
                InputField(
                    value = userModel.email, label = "Enter Email", enabled = false,
                )
                Row(
                    modifier = modifier,
                ) {
                    CountryCodeField(modifier = modifier
                        .weight(0.2f)
                        .padding(end = 8.dp),
                        value = countryCode,
                        label = "",
                        error = countryCodeError,
                        onValueChange = {
                            countryCodeError.value = false
                            countryCode = it
                        })
                    InputField(modifier = modifier.weight(0.8f),
                        value = phoneNumber.value,
                        label = "Phone Number",
                        error = phoneNumberError,
                        onValueChange = {
                            phoneNumberError.value = false
                            phoneNumber.value = it
                        })
                }
                Spacer(modifier = modifier.height(8.dp))
                CustomButton(label = "Update Account") {
                    if (firstName.value.isEmpty()) {
                        firstNameError.value = true
                    } else if (lastName.value.isEmpty()) {
                        lastNameError.value = true
                    } else if (countryCode.isEmpty() || countryCode.length != 3) {
                        countryCodeError.value = true
                    }else if (phoneNumber.value.isEmpty() || phoneNumber.value.length != 10) {
                        phoneNumberError.value = true
                    } else {
                        val user = userModel.copy(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            countryCode = countryCode,
                            phoneNumber = phoneNumber.value
                        )
                        onUpdateAccount(user)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewEditProfileScreen() {
    EditProfileScreen(
        userModel = UserModel(
            "",
            "firstName",
            "lastName",
            "username@gmail.com",
            "12345678910",
        )
    )
}