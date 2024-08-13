package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R

@Composable
fun DetailImageItem(modifier: Modifier = Modifier, imagePath: String = "") {
    val url = imagePath.ifEmpty {
        R.drawable.placeholder
    }
    AsyncImage(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .width(130.dp)
            .height(80.dp),
        model = url,
        contentDescription = null,
        placeholder = painterResource(id = R.drawable.placeholder),
        contentScale = ContentScale.Crop
    )


}

@Preview
@Composable
private fun PreviewDetailsImageItem() {
    DetailImageItem()
}