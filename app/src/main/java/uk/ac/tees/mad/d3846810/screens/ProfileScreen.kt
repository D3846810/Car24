package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar
import uk.ac.tees.mad.d3846810.screens.widgets.ProfileSettingItem
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    userModel: UserModel,
    onEditProfile: () -> Unit = {},
    onBookingHistory: () -> Unit = {},
    onShareApp: () -> Unit = {},
    onPrivacyPolicy: () -> Unit = {},
    onContactUs: () -> Unit = {},
    onAboutUs: () -> Unit = {},
    onLogout: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomToolbar(title = "Your Profile", isBackButtonVisible = false)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier
                    .clip(shape = CircleShape)
                    .size(100.dp),
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "profile picture"
            )
            CustomText(
                modifier = modifier.padding(top = 8.dp),
                text = "${userModel.firstName} ${userModel.lastName}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            CustomText(
                modifier = modifier.alpha(0.8.toFloat()),
                text = userModel.phoneNumber,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            CustomText(
                modifier = modifier.alpha(0.8.toFloat()),
                text = userModel.email,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Column(
                modifier = modifier.padding(top = 8.dp)
            ) {
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_edit, text = "Edit Profile"
                ) {
                    onEditProfile()
                }
                Spacer(modifier = modifier.height(8.dp))
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_history, text = "Booking History"
                ) {
                    onBookingHistory()
                }
                Spacer(modifier = modifier.height(8.dp))
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_share, text = "Share App"
                ) {
                    onShareApp()
                }
                Spacer(modifier = modifier.height(8.dp))
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_privacy, text = "Privacy Policy"
                ) {
                    onPrivacyPolicy()
                }
                Spacer(modifier = modifier.height(8.dp))
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_mail, text = "Contact Us"
                ) {
                    onContactUs()
                }
                Spacer(modifier = modifier.height(8.dp))
                ProfileSettingItem(
                    modifier = modifier, iconId = R.drawable.ic_info, text = "About Us"
                ) {
                    onAboutUs()
                }
            }
            Spacer(modifier = modifier.height(16.dp))
            CustomButton(label = "Log out") {
                onLogout()
            }
            Spacer(modifier = modifier.height(8.dp))
            Box(modifier.onClick { onDelete() }) {
                CustomText(
                    modifier = modifier.alpha(0.toFloat()),
                    text = "Delete Account",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen(userModel = UserModel())
}