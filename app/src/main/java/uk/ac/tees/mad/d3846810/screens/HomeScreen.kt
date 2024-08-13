package uk.ac.tees.mad.d3846810.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.model.CategoryModel
import uk.ac.tees.mad.d3846810.model.ProductModel
import uk.ac.tees.mad.d3846810.model.SliderModel
import uk.ac.tees.mad.d3846810.model.UserModel
import uk.ac.tees.mad.d3846810.screens.listitems.CategoryItem
import uk.ac.tees.mad.d3846810.screens.listitems.FavoriteItem
import uk.ac.tees.mad.d3846810.screens.listitems.NavigationItem
import uk.ac.tees.mad.d3846810.screens.widgets.CustomSearchBar
import uk.ac.tees.mad.d3846810.screens.widgets.CustomText
import uk.ac.tees.mad.d3846810.screens.widgets.Pager
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.utils.onClick
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userModel: UserModel?,
    sliderList: List<SliderModel> = arrayListOf(),
    categoryList: List<CategoryModel> = arrayListOf(),
    productList: List<ProductModel> = arrayListOf(),
    favoriteList: List<ProductModel> = arrayListOf(),
    onCategory: (CategoryModel) -> Unit = {},
    onProduct: (ProductModel) -> Unit = {},
    onFavorite: (ProductModel) -> Unit = {},
    onQuery: (String) -> Unit = {},
    openDrawer: () -> Unit = {},
) {
    val userName = "Hello ${userModel?.firstName} ${userModel?.lastName},"

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ConstraintLayout(
                modifier = modifier
            ) {
                Pager(modifier = modifier, sliderList = sliderList)
                val drawerBtn = createRef()
                Box(modifier = modifier
                    .onClick { openDrawer() }
                    .padding(16.dp)
                    .constrainAs(drawerBtn) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
                    .size(40.dp)
                    .background(c10, RoundedCornerShape(8.dp))) {
                    Image(
                        modifier = modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

            Column(modifier = modifier) {
                Column(modifier = modifier.padding(horizontal = 16.dp)) {
                    var searchQuery by remember { mutableStateOf("") }
                    CustomSearchBar(modifier = modifier.height(48.dp),
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                            onQuery(searchQuery)
                        })
                    Spacer(modifier = modifier.height(16.dp))

                    CustomText(
                        modifier = modifier,
                        text = userName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    CustomText(
                        modifier = modifier.alpha(0.5.toFloat()),
                        text = "Explore Your Dream Car..",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = modifier.height(8.dp))
                    CustomText(
                        modifier = modifier,
                        text = "All Categories",
                        fontSize = 16.sp,
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    LazyRow {
                        items(items = categoryList) {
                            CategoryItem(modifier = modifier.onClick {
                                onCategory(it)
                            }, imagePath = it.imgUrl)
                        }
                    }
                    Spacer(modifier = modifier.height(8.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        CustomText(
                            modifier = modifier,
                            text = "All Cars",
                            fontSize = 16.sp,
                        )
                        CustomText(
                            modifier = modifier.alpha(0.7.toFloat()),
                            text = "View All",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    for (product in productList) {
                        val isFavorite = favoriteList.any { it.productId == product.productId }
                        FavoriteItem(productModel = product,
                            isFavorite = isFavorite,
                            onItemClick = {
                                onProduct(it)
                            },
                            onFavorite = {
                                onFavorite(it)
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    userModel: UserModel?,
    sliderList: List<SliderModel> = arrayListOf(),
    categoryList: List<CategoryModel> = arrayListOf(),
    productList: List<ProductModel> = arrayListOf(),
    favoriteList: List<ProductModel> = arrayListOf(),
    onQuery: (String) -> Unit = {},
    onCategory: (CategoryModel) -> Unit = {},
    onProduct: (ProductModel) -> Unit = {},
    onFavorite: (ProductModel) -> Unit = {},
    onViewBooking: () -> Unit = {},
    onEditProfile: () -> Unit = {},
    onShare: () -> Unit = {},
    onPrivacy: () -> Unit = {},
    onContactUs: () -> Unit = {},
    onAboutUs: () -> Unit = {},
    onRateUs: () -> Unit = {},
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // State composable used to hold the
    // value of the current active screen
    val coroutineScope = rememberCoroutineScope()

    ModalDrawer(
        // Drawer state indicates whether
        // the drawer is open or closed.
        drawerState = drawerState, gesturesEnabled = drawerState.isOpen, drawerContent = {
            //drawerContent accepts a composable to represent
            // the view/layout that will be displayed
            // when the drawer is open.
            DrawerContentComponent(modifier = modifier, onItemClick = {
                Log.d("TAG_NAV", "NavigationDrawer: $it")
                when (it) {
                    1 -> onViewBooking()
                    2 -> onEditProfile()
                    3 -> onShare()
                    4 -> onRateUs()
                    5 -> onPrivacy()
                    6 -> onContactUs()
                    7 -> onAboutUs()
                }
            }, closeDrawer = { coroutineScope.launch { drawerState.close() } })
        }, content = {
            // bodyContent takes a composable to
            // represent the view/layout to display on the
            // screen. We select the appropriate screen
            // based on the value stored in currentScreen.
            BodyContentComponent(modifier = modifier,
                userModel = userModel,
                sliderList = sliderList,
                categoryList = categoryList,
                productList = productList,
                favoriteList = favoriteList,
                onQuery = {
                    onQuery(it)
                },
                onProduct = {
                    onProduct(it)
                },
                onFavorite = {
                    onFavorite(it)
                },
                onCategory = {
                    onCategory(it)
                },
                openDrawer = {
                    coroutineScope.launch { drawerState.open() }
                })
        })

    if (drawerState.isOpen) {
        BackHandler {
            coroutineScope.launch { drawerState.close() }
        }
    }
}

@Composable
fun DrawerContentComponent(
    modifier: Modifier = Modifier, onItemClick: (Int) -> Unit, closeDrawer: () -> Unit
) {

    val items = listOf(
        "Home",
        "Bookings",
        "Edit Profile",
        "Share App",
        "Rate Us",
        "Privacy Policy",
        "Contact Us",
        "About Us"
    )
    val icons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_booking,
        R.drawable.ic_edit,
        R.drawable.ic_share,
        R.drawable.ic_star_outline,
        R.drawable.ic_privacy,
        R.drawable.ic_mail,
        R.drawable.ic_more,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = modifier.height(24.dp))
        Image(
            modifier = modifier.size(100.dp),
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "app icon"
        )
        Spacer(modifier = modifier.height(16.dp))
        Column(modifier = modifier) {
            for (index in DrawerAppScreen.values().indices) {
                NavigationItem(
                    modifier = modifier.onClick {
                        onItemClick(index)
                        closeDrawer()
                    }, iconId = icons[index], text = items[index]
                )
            }
        }
    }
}

@Composable
fun BodyContentComponent(
    modifier: Modifier = Modifier,
    userModel: UserModel?,
    sliderList: List<SliderModel> = arrayListOf(),
    categoryList: List<CategoryModel> = arrayListOf(),
    productList: List<ProductModel> = arrayListOf(),
    favoriteList: List<ProductModel> = arrayListOf(),
    onCategory: (CategoryModel) -> Unit,
    onProduct: (ProductModel) -> Unit = {},
    onFavorite: (ProductModel) -> Unit = {},
    onQuery: (String) -> Unit = {},
    openDrawer: () -> Unit
) {
    HomeScreen(modifier = modifier,
        userModel = userModel,
        sliderList = sliderList,
        categoryList = categoryList,
        productList = productList,
        favoriteList = favoriteList,
        onQuery = {
            onQuery(it)
        },
        onCategory = {
            onCategory(it)
        },
        onProduct = {
            onProduct(it)
        },
        onFavorite = {
            onFavorite(it)
        },
        openDrawer = {
            openDrawer()
        })
}

enum class DrawerAppScreen {
    Home, Bookings, Profile, About, Share, Rate, Privacy, Contact,
}

@Preview
@Composable
private fun PreviewHomeScreen() {
    val sliderList = ArrayList<SliderModel>()
    val productList = ArrayList<ProductModel>()
    val categoryList = ArrayList<CategoryModel>()
    val favoriteList = ArrayList<ProductModel>()
    sliderList.add(SliderModel("", "", "", 1121212))
    sliderList.add(SliderModel("", "", "", 1121212))
    sliderList.add(SliderModel("", "", "", 1121212))
    productList.add(ProductModel("11"))
    productList.add(ProductModel("12"))
    productList.add(ProductModel())
    favoriteList.add(ProductModel("11"))
    favoriteList.add(ProductModel("12"))
    categoryList.add(CategoryModel())
    categoryList.add(CategoryModel())
    categoryList.add(CategoryModel())
    categoryList.add(CategoryModel())
    categoryList.add(CategoryModel())
    categoryList.add(CategoryModel())
    NavigationDrawer(
        userModel = UserModel("", "firstName", "lastName"),
        sliderList = sliderList,
        categoryList = categoryList,
        productList = productList,
        favoriteList = favoriteList
    )
}