package uk.ac.tees.mad.d3846810.screens.listitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.theme.blue
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.theme.textColor
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun SimilarProductItem(
    modifier: Modifier = Modifier,
    productModel: ProductModel = ProductModel(),
    isFavorite: Boolean = false,
    onClick: (ProductModel) -> Unit = {}
) {
    Row(modifier = modifier
        .onClick { onClick(productModel) }
        .fillMaxWidth()
        .height(160.dp)
        .background(Color.White, RoundedCornerShape(8.dp))
        .border(0.7.dp, liteGy, RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Box(
            modifier = modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(8.dp))
        ) {
            val url = productModel.coverImg.ifEmpty {
                R.drawable.placeholder
            }
            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = url,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
            val favIcon = if (isFavorite) {
                R.drawable.ic_fav_filled_red
            } else {
                R.drawable.ic_fav_outline
            }

            Image(
                modifier = modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .background(
                        color = Color.White, shape = RoundedCornerShape(8.dp)
                    )
                    .align(Alignment.TopStart)
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                painter = painterResource(id = favIcon),
                contentDescription = "favourite icon"
            )

            CustomText(
                modifier = modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        color = blue, shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                    )
                    .padding(start = 4.dp, end = 8.dp),
                text = productModel.tag,
                color = Color.White,
                fontSize = 12.sp
            )
        }
        Column(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Spacer(modifier = modifier.width(8.dp))

            val title = "${productModel.year} ${productModel.title}"

            CustomText(
                text = title, fontSize = 16.sp, modifier = modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = modifier.height(8.dp))

            val subTitle =
                "${productModel.runKm} km • ${productModel.engineType} • ${productModel.gearType}"

            CustomText(
                text = subTitle,
                fontSize = 12.sp,
                color = textColor,
                modifier = modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(horizontal = 8.dp)
            ) {
                CustomText(
                    text = stringResource(id = R.string.currencySymbol),
                    color = textColor,
                )
                Spacer(modifier = modifier.width(4.dp))
                CustomText(
                    text = productModel.price,
                    fontSize = 20.sp,
                    color = textColor,
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            HorizontalDivider(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = liteGy)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(horizontal = 8.dp)
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
                )
            }
            Spacer(modifier = modifier.width(8.dp))
        }
    }

}

@Preview
@Composable
private fun PreviewSimilarProduct() {
    SimilarProductItem(
        isFavorite = true,
        productModel = ProductModel(
            "",
            "",
            "Maruti Suzuki VXI",
            "2021",
            "4.1",
            "The Maruti Swift is Equiped with a 1.2-litre petrol engine",
            "321000",
            "Gujarat",
            "Maruti",
            "Gujarat",
            "62400",
            "Petrol",
            "Manual",
            "GJ 12",
            "Gujarat",
            "Gujarat",
            "On Demand",
            "New Car",
        )
    )
}