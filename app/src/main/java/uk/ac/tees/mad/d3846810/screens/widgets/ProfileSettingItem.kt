package uk.ac.tees.mad.d3846810.screens.widgets

import android.annotation.SuppressLint
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
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun ProfileSettingItem(
    modifier: Modifier = Modifier, iconId: Int, text: String = "", onClick: () -> Unit = {}
) {
    Box(modifier = modifier
        .onClick { onClick() }
        .fillMaxWidth()
        .background(Color.White, RoundedCornerShape(8.dp))
        .border(1.dp, liteGy, RoundedCornerShape(8.dp))) {
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
                .size(28.dp)
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
                fontWeight = FontWeight.SemiBold)

            Icon(modifier = modifier
                .constrainAs(arrow) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(end = 8.dp)
                .size(16.dp),
                painter = painterResource(id = R.drawable.ic_front_arrow),
                contentDescription = "icon edit")

        }
    }
}

@SuppressLint("ResourceType")
@Preview
@Composable
private fun PreviewProfileSettingItem() {
    ProfileSettingItem(iconId = R.drawable.ic_edit, text = "Edit Profile")
}