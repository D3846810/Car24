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
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.screens.listitems.FavoriteItem
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    loading: String = "Loading...",
    categoryName: String = "Category Name",
    productList: List<ProductModel> = arrayListOf(),
    favoriteList: List<ProductModel> = arrayListOf(),
    onProductClick: (ProductModel) -> Unit = {},
    onFavorite: (ProductModel) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val (constraintLayout, column, loadingTxt) = createRefs()

        CustomToolbar(title = categoryName,
            backButtonIcon = R.drawable.ic_arrow,
            isBackButtonVisible = true,
            modifier = Modifier.constrainAs(constraintLayout) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }) {
            onBackPress()
        }
        if (productList.isEmpty()) {
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
            Column(modifier = modifier) {
                for (product in productList) {
                    val isFavorite = favoriteList.any { it.productId == product.productId }
                    FavoriteItem(productModel = product, isFavorite = isFavorite, onItemClick = {
                        onProductClick(it)
                    }, onFavorite = {
                        onFavorite(it)
                    })
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryScreen() {
    CategoryScreen()
}