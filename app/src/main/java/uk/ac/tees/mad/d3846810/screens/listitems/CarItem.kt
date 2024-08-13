package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.background
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
import uk.ac.tees.mad.d3846810.theme.textColor

@Composable
fun CarItem(
    modifier: Modifier = Modifier,
    iconId: Int = R.drawable.ic_car,
    text: String = "",
    brand: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            val (icon, name, arrow) = createRefs()

            Icon(modifier = modifier
                .constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
                .size(20.dp)
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
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)

            CustomText(modifier = modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }, text = brand, color = textColor
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCarItem() {
    CarItem(
        text = "Car Brand", brand = "Maruti"
    )
}