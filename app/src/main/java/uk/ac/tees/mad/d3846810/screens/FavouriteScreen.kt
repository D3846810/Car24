package uk.ac.tees.mad.d3846810.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.listitems.FavoriteItem
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    loading: String = "Loading...",
    favoriteList: List<ProductModel> = arrayListOf(),
    onProductClick: (ProductModel) -> Unit = {},
    onFavorite: (ProductModel) -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (constraintLayout, column, loadingTxt) = createRefs()

        CustomToolbar(title = "Favorite List", isBackButtonVisible = false, modifier = Modifier.constrainAs(constraintLayout) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        })
        if (favoriteList.isEmpty()) {
            CustomText(modifier = modifier
                .constrainAs(loadingTxt) {
                    top.linkTo(constraintLayout.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .alpha(0.8.toFloat()),
                text = loading,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold)
        }
        Column(modifier = modifier
            .constrainAs(column) {
                top.linkTo(constraintLayout.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
            .verticalScroll(rememberScrollState())) {
            for (product in favoriteList) {
                FavoriteItem(productModel = product, isFavorite = true, onItemClick = {
                    onProductClick(it)
                }, onFavorite = {
                    onFavorite(it)
                })
            }
        }
    }
}

@Preview
@Composable
private fun PreviewFavoriteScreen() {
    val favoriteList = arrayListOf<ProductModel>()
    favoriteList.add(
        ProductModel(
            "1",
            "",
            "Honda Vezel",
            "2016",
            "",
            "",
            "500000",
            "Location",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "New Car"
        )
    )
    FavoriteScreen(favoriteList = favoriteList, loading = "Loading...")
}