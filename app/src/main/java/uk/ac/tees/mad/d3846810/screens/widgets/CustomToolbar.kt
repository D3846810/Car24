package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    title: String,
    isBackButtonVisible: Boolean = true,
    backButtonIcon: Int = R.drawable.ic_back_arrow,
    onBackPressed: () -> Unit ={}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(c10),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var spacerSize = 16.dp
        if(isBackButtonVisible) {
            Image(
                modifier = modifier
                    .onClick { onBackPressed() }
                    .size(48.dp)
                    .padding(8.dp),
                painter = painterResource(id = backButtonIcon),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "",
            )
            spacerSize = 8.dp
        }
        Spacer(modifier = modifier.width(spacerSize))
        Text(
            modifier = modifier,
            text = title,
            style = MaterialTheme.typography.h6,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.inter_semibold))
        )
    }
}

@Preview
@Composable
private fun PreviewCustomToolbar() {
    CustomToolbar(title = "Custom Toolbar", isBackButtonVisible = false) {

    }
}