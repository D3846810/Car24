package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun NavigationItem(
    modifier: Modifier = Modifier, iconId: Int, text: String = "", onClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .onClick { onClick() }
        .fillMaxWidth()
        .background(Color.White, RoundedCornerShape(8.dp))){
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
        ) {
            val (icon, name, arrow) = createRefs()

            Icon(modifier = modifier
                .padding(start = 8.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .size(32.dp)
                .fillMaxSize(),
                painter = painterResource(id = iconId),
                contentDescription = "icon edit")

            CustomText(modifier = modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    start.linkTo(icon.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 16.dp),
                text = text,
                fontSize = 16.sp,
                color = Color.Black
            )

            Icon(modifier = modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    start.linkTo(name.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 16.dp)
                .size(16.dp),
                painter = painterResource(id = R.drawable.ic_front_arrow),
                contentDescription = "icon edit")

        }
    }
}

@Preview
@Composable
private fun PreviewNavigationItem() {
    NavigationItem(iconId = R.drawable.ic_booking, text = "Your Bookings")
}