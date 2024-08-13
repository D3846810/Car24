package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.SliderModel

@Composable
fun SliderItem(modifier: Modifier = Modifier, sliderModel: SliderModel, onDelete: () -> Unit) {
    Card(modifier = modifier
        .clickable {
            onDelete()
        }
        .fillMaxWidth()
        .height(200.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val image = createRef()

            AsyncImage(modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(id = R.string.app_name),
                model = sliderModel.imageUrl,
                placeholder = painterResource(id = R.drawable.placeholder))

        }
    }
}

@Preview
@Composable
private fun SliderItemPreview() {
    SliderItem(modifier = Modifier, sliderModel = SliderModel()) {

    }
}