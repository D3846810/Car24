package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.BookingModel
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.screens.widgets.CountryCodeField
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar
import uk.ac.tees.mad.d3846810.screens.widgets.InputField
import uk.ac.tees.mad.d3846810.theme.c10

@Composable
fun CreateBookingScreen(
    modifier: Modifier = Modifier,
    userModel: UserModel = UserModel(),
    onBookNow: (BookingModel) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    var name by remember { mutableStateOf(userModel.firstName) }
    val nameError = remember { mutableStateOf(false) }
    var email by remember { mutableStateOf(userModel.lastName) }
    val emailError = remember { mutableStateOf(false) }
    var countryCode by remember { mutableStateOf(userModel.countryCode) }
    val countryCodeError = remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf(userModel.phoneNumber) }
    val phoneNumberError = remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val messageError = remember { mutableStateOf(false) }

    var selectedRadioButton by remember { mutableStateOf("") }
    val options = arrayOf("Booking For Test Drive", "Booking for Enquiry", "Other Reason")

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = modifier) {
            CustomToolbar(
                modifier = modifier, backButtonIcon = R.drawable.ic_arrow, title = "Booking Here"
            ) {
                onBackPress()
            }
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = modifier.height(16.dp))
                CustomText(
                    modifier = modifier.fillMaxWidth(),
                    text = "BOOK TEST DRIVE HERE!",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = modifier.height(8.dp))

                InputField(value = name, label = "Enter Name", error = nameError, onValueChange = {
                    nameError.value = false
                    name = it
                })
                InputField(
                    value = email, label = "Enter Email", error = emailError,
                    onValueChange = {
                        emailError.value = false
                        email = it
                    },
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
                        value = phoneNumber,
                        label = "Phone Number",
                        error = phoneNumberError,
                        onValueChange = {
                            phoneNumberError.value = false
                            phoneNumber = it
                        })
                }
                InputField(
                    modifier = modifier.height(200.dp),
                    value = message, label = "Enter Message",
                    error = messageError,
                    onValueChange = {
                        messageError.value = false
                        message = it
                    },
                )
                Spacer(modifier = modifier.height(16.dp))
                CustomText(
                    modifier = modifier.fillMaxWidth(),
                    text = "SELECT AN OPTION",
                    fontSize = 20.sp,
                )
                Spacer(modifier = modifier.height(8.dp))

                Column(modifier.fillMaxWidth()) {
                    options.forEach { item ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .selectable(selected = selectedRadioButton == item,
                                    onClick = { selectedRadioButton = item }),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedRadioButton == item, onClick = {
                                    selectedRadioButton = item
                                }, colors = RadioButtonDefaults.colors(
                                    selectedColor = c10
                                )
                            )
                            CustomText(
                                modifier = modifier.padding(start = 4.dp),
                                text = item,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Spacer(modifier = modifier.height(16.dp))
                CustomButton(label = "BOOK NOW") {
                    if (name.isEmpty()) {
                        nameError.value = true
                    } else if (email.isEmpty()) {
                        emailError.value = true
                    } else if (countryCode.isEmpty() || countryCode.length != 3) {
                        countryCodeError.value = true
                    } else if (phoneNumber.isEmpty() || phoneNumber.length != 10) {
                        phoneNumberError.value = true
                    } else if (message.isEmpty()) {
                        messageError.value = true
                    } else {
                        val bookingModel = BookingModel(
                            name = name,
                            email = email,
                            countryCode = countryCode,
                            phoneNumber = phoneNumber,
                            message = message,
                            reason = selectedRadioButton,
                        )
                        onBookNow(bookingModel)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCreateBookingScreen() {
    CreateBookingScreen()
}