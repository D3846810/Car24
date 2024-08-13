package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.FavoriteModel
import uk.ac.tees.mad.d3846810.model.ProductImageUrlModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.listitems.CarItem
import uk.ac.tees.mad.d3846810.screens.listitems.DetailImageItem
import uk.ac.tees.mad.d3846810.screens.listitems.SimilarProductItem
import uk.ac.tees.mad.d3846810.screens.widgets.CustomButton
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.green
import uk.ac.tees.mad.d3846810.theme.liteGy
import uk.ac.tees.mad.d3846810.theme.textColor
import uk.ac.tees.mad.d3846810.utils.onClick

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    productModel: ProductModel = ProductModel(),
    isProductFav: Boolean = false,
    similarProductList: List<ProductModel> = arrayListOf(),
    favWithSimilarList: List<FavoriteModel> = arrayListOf(),
    onFavorite: (ProductModel) -> Unit = {},
    onShare: (ProductModel) -> Unit = {},
    onBookTestDrive: (ProductModel) -> Unit = {},
    onSimilarProduct: (ProductModel) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {
            Box(modifier = modifier.fillMaxSize()) {
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
                Box(modifier = modifier.padding(top = 8.dp, start = 8.dp)) {
                    Image(modifier = modifier
                        .onClick { onBackPress() }
                        .size(48.dp)
                        .border(1.dp, liteGy, RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(4.dp),
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "")
                }
                Column(
                    modifier = modifier
                        .padding(top = 8.dp, end = 16.dp)
                        .align(Alignment.TopEnd)
                ) {
                    val favIcon = if (isProductFav) {
                        R.drawable.ic_fav_filled_red
                    } else {
                        R.drawable.ic_fav_outline
                    }

                    Image(modifier = modifier
                        .onClick {
                            onFavorite(productModel)
                        }
                        .background(
                            color = Color.White, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                        painter = painterResource(id = favIcon),
                        contentDescription = "favourite icon")
                    Spacer(modifier = modifier.height(8.dp))
                    Image(modifier = modifier
                        .onClick {
                            onShare(productModel)
                        }
                        .background(
                            color = Color.White, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "favourite icon")
                }

                Box(
                    modifier = modifier
                        .padding(bottom = 8.dp, start = 8.dp)
                        .border(0.75.dp, liteGy, RoundedCornerShape(8.dp))
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.BottomStart)
                ) {
                    CustomText(text = productModel.available, fontSize = 16.sp)
                }
            }
        }

        LazyRow(modifier = modifier.padding(horizontal = 4.dp, vertical = 4.dp)) {
            items(items = productModel.carImages) {
                DetailImageItem(imagePath = it.imageUrl)
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            c10, Color.White
                        )
                    ), RoundedCornerShape(16.dp)
                )
                .border(1.dp, c10, RoundedCornerShape(16.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.White,
                modifier = modifier.size(16.dp)
            )
            Spacer(modifier = modifier.width(8.dp))
            CustomText(
                modifier = modifier,
                text = productModel.location,
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Box(
            modifier = modifier.background(
                color = Color.White, shape = RoundedCornerShape(8.dp)
            )
        ) {
            Column(
                modifier = modifier
            ) {
                val title = "${productModel.year} ${productModel.title}"

                CustomText(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                val subTitle =
                    "${productModel.runKm} km • ${productModel.engineType} • ${productModel.gearType}"

                CustomText(
                    text = subTitle,
                    fontSize = 16.sp,
                    color = textColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = modifier.height(8.dp))
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = modifier
                            .padding(bottom = 8.dp)
                            .border(0.75.dp, liteGy, RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        CustomText(text = productModel.brand, fontSize = 16.sp)
                    }
                    Box(
                        modifier = modifier
                            .padding(bottom = 8.dp, start = 8.dp)
                            .border(0.75.dp, liteGy, RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        CustomText(text = productModel.year, fontSize = 16.sp)
                    }
                    Box(
                        modifier = modifier
                            .padding(bottom = 8.dp, start = 8.dp)
                            .border(0.75.dp, liteGy, RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        CustomText(text = productModel.rtoNum, fontSize = 16.sp)
                    }
                }
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(0.75.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = liteGy)
                )
                Spacer(modifier = modifier.height(8.dp))

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(modifier = modifier) {
                        Row(
                            modifier = modifier, verticalAlignment = Alignment.CenterVertically
                        ) {
                            CustomText(
                                text = stringResource(id = R.string.currencySymbol),
                                color = textColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Spacer(modifier = modifier.width(4.dp))
                            CustomText(
                                text = productModel.price,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        CustomText(
                            modifier = modifier.alpha(.5.toFloat()),
                            text = "The Price can be Negotiable..",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                    Box(
                        modifier = modifier
                            .border(0.75.dp, liteGy, RoundedCornerShape(8.dp))
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        Row(
                            modifier = modifier, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = modifier,
                                painter = painterResource(id = R.drawable.ic_verification),
                                contentDescription = "verified",
                                colorFilter = ColorFilter.tint(green)
                            )
                            Spacer(modifier = modifier.width(4.dp))
                            CustomText(modifier = modifier, text = "Verified", fontSize = 16.sp)

                        }
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
                HorizontalDivider(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(0.75.dp)
                        .padding(horizontal = 16.dp)
                        .background(color = liteGy)
                )
                Spacer(modifier = modifier.height(8.dp))
                CustomText(
                    modifier = modifier.padding(horizontal = 16.dp),
                    text = productModel.description,
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(modifier = modifier.height(8.dp))
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .border(1.dp, liteGy, RoundedCornerShape(8.dp))
                ) {
                    Spacer(modifier = modifier.height(8.dp))
                    CustomText(
                        modifier = modifier.padding(horizontal = 16.dp),
                        text = "Car Overview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    HorizontalDivider(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(0.75.dp)
                            .padding(horizontal = 16.dp)
                            .background(color = liteGy)
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_car,
                        text = "Car Brand",
                        productModel.brand
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_calender,
                        text = "Registration Year",
                        productModel.year
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_gear,
                        text = "Gear Type",
                        productModel.gearType
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_stearing,
                        text = "Driven Kms",
                        productModel.runKm
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_engine,
                        text = "Engine Type",
                        productModel.engineType
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_seat,
                        text = "Seats",
                        productModel.seating
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_insurance,
                        text = "Insurance",
                        productModel.insurance
                    )
                    CarItem(
                        modifier = modifier, R.drawable.ic_rto, text = "RTO", productModel.rtoNum
                    )
                    CarItem(
                        modifier = modifier,
                        R.drawable.ic_star_outline,
                        text = "Condition Rating",
                        productModel.rating
                    )
                }

                Spacer(modifier = modifier.height(8.dp))
                CustomButton(
                    modifier = modifier.padding(horizontal = 16.dp),
                    trailingIcon = R.drawable.ic_booking,
                    label = "Book Test Drive"
                ) {
                    onBookTestDrive(productModel)
                }
                Spacer(modifier = modifier.height(8.dp))
                if (similarProductList.isNotEmpty()) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = modifier
                                .weight(0.31f)
                                .height(0.7.dp)
                                .background(color = liteGy)
                        )
                        CustomText(
                            modifier = modifier.weight(0.38f),
                            text = "Similar Products",
                            textAlign = TextAlign.Center
                        )
                        HorizontalDivider(
                            modifier = modifier
                                .weight(0.31f)
                                .height(0.7.dp)
                                .background(color = liteGy)
                        )
                    }

                    Spacer(modifier = modifier.height(8.dp))

                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)) {
                        for (product in similarProductList) {
                            val isFav = favWithSimilarList.any { it.productId == product.productId }
                            SimilarProductItem(
                                modifier = modifier, productModel = product, isFavorite = isFav
                            ) {
                                onSimilarProduct(it)
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(8.dp))

                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewDetailsScreen() {
    DetailsScreen(
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
            "Gujarat",
            arrayListOf(ProductImageUrlModel(), ProductImageUrlModel(), ProductImageUrlModel()),
        ), similarProductList = arrayListOf(
            ProductModel(
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
                "Gujarat",
            )
        )
    )
}