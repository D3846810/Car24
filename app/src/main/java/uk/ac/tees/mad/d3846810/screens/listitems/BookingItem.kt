package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.BookingModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.theme.blue
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.textColor
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun BookingItem(
    modifier: Modifier = Modifier,
    booking: BookingModel,
    productModel: ProductModel,
    onItemClick: () -> Unit
) {
    ConstraintLayout(modifier = modifier
        .onClick {
            onItemClick()
        }
        .padding(8.dp)) {
        val (imageLayout, midLayout, endLayout) = createRefs()
        Card(
            modifier = modifier.constrainAs(endLayout) {
                start.linkTo(parent.start)
                top.linkTo(midLayout.bottom, margin = ((-16).dp))
                end.linkTo(parent.end)
            },
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.White)
        ) {
            Column(
                modifier = modifier
            ) {
                CustomText(
                    text = "Booking Details",
                    fontSize = 20.sp,
                    color = c10,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = modifier
                        .padding(top = 16.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Column(
                    modifier = modifier.padding(horizontal = 16.dp)
                ) {
                    InfoRow(label = "Name :", info = booking.name)
                    InfoRow(label = "Mobile :", info = booking.phoneNumber)
                    InfoRow(label = "Email :", info = booking.email)
                    InfoRow(label = "Option :", info = booking.reason)
                    val date = "This Request booked on ${booking.timestamp}"

                    CustomText(
                        modifier = modifier
                            .alpha(0.7f)
                            .padding(bottom = 8.dp),
                        text = date,
                        fontSize = 14.sp,
                        color = textColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Card(modifier = modifier
            .constrainAs(imageLayout) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(190.dp), elevation = CardDefaults.cardElevation(4.dp)) {
            Box(modifier = modifier) {
                val url = productModel.coverImg.ifEmpty {
                    R.drawable.placeholder
                }
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )
                CustomText(
                    modifier = modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopStart)
                        .background(
                            color = blue,
                            shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                        )
                        .padding(start = 4.dp, end = 8.dp),
                    text = productModel.tag,
                    color = Color.White,
                    fontSize = 12.sp
                )
                Text(
                    text = booking.status,
                    modifier = modifier
                        .padding(top = 8.dp, end = 16.dp)
                        .align(Alignment.TopEnd)
                        .background(
                            color = blue, shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }

        Card(modifier = modifier
            .constrainAs(midLayout) {
                start.linkTo(parent.start)
                top.linkTo(imageLayout.bottom, margin = (-16).dp)
                end.linkTo(parent.end)
            },
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors().copy(containerColor = Color.White)) {
            Column(
                modifier = modifier
            ) {
                val title = "${productModel.year} ${productModel.title}"

                CustomText(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                val subTitle =
                    "${productModel.runKm} km • ${productModel.engineType} • ${productModel.gearType}"

                CustomText(
                    text = subTitle,
                    fontSize = 12.sp,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 16.dp)
                ) {
                    CustomText(
                        text = stringResource(id = R.string.currencySymbol),
                        color = textColor, fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = modifier.width(4.dp))
                    CustomText(
                        text = productModel.price,
                        fontSize = 20.sp,
                        color = textColor,
                        fontWeight = FontWeight.ExtraBold,
                    )
                }
                Spacer(modifier = modifier.height(8.dp))
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = Color.Gray)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = textColor,
                        modifier = modifier.size(12.dp)
                    )
                    Spacer(modifier = modifier.width(8.dp))
                    CustomText(
                        text = productModel.location,
                        fontSize = 12.sp,
                        color = textColor,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, label: String, info: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(vertical = 8.dp)
    ) {
        CustomText(
            text = label, fontSize = 16.sp, color = textColor, fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = modifier.width(8.dp))
        CustomText(
            text = info,
            fontSize = 16.sp,
            color = textColor,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.weight(1f)
        )
    }
}

@Preview
@Composable
private fun PreviewBookingItem() {
    BookingItem(
        booking = BookingModel(
            "1",
            "1",
            "1",
            "name",
            "username@gmail.com",
            "1234567890",
            "",
            "Visit",
            "123188238238",
            "Pending",
        ), productModel = ProductModel(
            "1",
            "",
            "Honda Vezel",
            "2016",
            "",
            "",
            "500000",
            "location",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "New Car",
        )
    ) {

    }
}