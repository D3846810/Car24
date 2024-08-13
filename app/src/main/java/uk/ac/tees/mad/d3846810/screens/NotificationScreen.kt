package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import uk.ac.tees.mad.d3846810.model.NotificationModel
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar
import uk.ac.tees.mad.d3846810.screens.listitems.NotificationItem

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    loading: String = "Loading...",
    notificationList: List<NotificationModel> = arrayListOf(),
) {
    Column(modifier = modifier) {
        CustomToolbar(title = "Notifications", isBackButtonVisible = false)
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (notificationList.isEmpty()) {
                CustomText(
                    modifier = modifier
                        .align(Alignment.Center)
                        .alpha(0.8.toFloat()),
                    text = loading,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(modifier = modifier) {
                for (notification in notificationList) {
                    NotificationItem(notification = notification)
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewNotificationScreen() {
    val list = arrayListOf<NotificationModel>()
    list.add(NotificationModel("a", "a", "title", "description"))
    NotificationScreen(notificationList = list)
}