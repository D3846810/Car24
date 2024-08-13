package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.NotificationModel
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.theme.gray

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier, notification: NotificationModel
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Card(modifier = modifier, elevation = CardDefaults.elevatedCardElevation()) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color.White, shape = RoundedCornerShape(4.dp))
            ) {
                Column(modifier = modifier) {
                    if (notification.notificationImg.isNotEmpty()) {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(gray)
                        ) {
                            AsyncImage(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                contentScale = ContentScale.Crop,
                                model = notification.notificationImg,
                                contentDescription = "",
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.placeholder)
                            )
                        }
                    }
                    CustomText(
                        modifier = modifier.padding(top = 16.dp, start = 8.dp),
                        text = notification.notificationTitle,
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold,
                    )
                    CustomText(
                        modifier = modifier
                            .padding(start = 8.dp, bottom = 8.dp)
                            .alpha(0.8.toFloat()),
                        color = gray,
                        text = notification.notificationDescription,
                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun previewNotificationItem() {
    NotificationItem(
        modifier = Modifier, NotificationModel(
            "a",
            "a",
            "Notification",
            "Description",
        )
    )
}