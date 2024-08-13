package uk.ac.tees.mad.d3846810.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import uk.ac.tees.mad.d3846810.R
import uk.ac.tees.mad.d3846810.databinding.DialogExitBinding
import uk.ac.tees.mad.d3846810.fragments.FavoritesFragment
import uk.ac.tees.mad.d3846810.fragments.HomeFragment
import uk.ac.tees.mad.d3846810.fragments.NotificationFragment
import uk.ac.tees.mad.d3846810.fragments.ProfileFragment
import uk.ac.tees.mad.d3846810.screens.navigation.TabBarItem
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.c60
import uk.ac.tees.mad.d3846810.theme.textColor

class HomeMainActivity : AppCompatActivity() {
//    private val binding by lazy { ActivityHomeMainBinding.inflate(layoutInflater) }

    private lateinit var navController: NavHostController
    var selectedRoute = "Home"

    val homeTab = TabBarItem(
        title = "Home",
        selectedIcon = R.drawable.ic_home_filled,
        unselectedIcon = R.drawable.ic_home_outline
    )
    val favorite = TabBarItem(
        title = "Favorite",
        selectedIcon = R.drawable.ic_fav_filled,
        unselectedIcon = R.drawable.ic_fav_outline
    )
    val profile = TabBarItem(
        title = "Profile",
        selectedIcon = R.drawable.ic_profile_filled,
        unselectedIcon = R.drawable.ic_profile_outline
    )
    val alertsTab = TabBarItem(
        title = "Alerts",
        selectedIcon = R.drawable.ic_notification_filled,
        unselectedIcon = R.drawable.ic_notification_outline,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            // creating a list of all the tabs
            val tabBarItems = listOf(homeTab, favorite, profile, alertsTab)
            Surface(
                modifier = Modifier.fillMaxSize(), color = c60
            ) {
                Scaffold(bottomBar = {
                    //                BottomNavigationBar(navController)
                    TabView(tabBarItems, navController)
                }) { paddingValues ->
                    NavHost(navController = navController, startDestination = homeTab.title) {
                        composable(homeTab.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                HomeScreen()
                            }
                        }
                        composable(favorite.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                FavoriteScreen()
                            }
                        }
                        composable(profile.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                ProfileScreen()
                            }
                        }
                        composable(alertsTab.title) {
                            Box(modifier = Modifier.padding(paddingValues)) {
                                AlertScreen()
                            }
                        }
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.graph.startDestinationRoute?.let {
                    Log.d("TAG_NAV", "handleOnBackPressed: $it")
                    if (selectedRoute == homeTab.title) {
                        showExitDialog()
                    } else {
                        navController.navigateUp()
                    }
                }
//                    if (binding.drawerLayout.isOpen) {
//                        closeDrawer()
//                    } else {
//                    showExitDialog()
//                    }
            }

        })
//        setContentView(binding.root)
//        val navView: BottomNavigationView = binding.navView
//        val navController = findNavController(R.id.nav_host_fragment_activity_home_main)
//        navView.setupWithNavController(navController)

        askNotificationPermission()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->

    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showExitDialog() {
        val bottomSheet = BottomSheetDialog(this)
        val layout = DialogExitBinding.inflate(layoutInflater)
        bottomSheet.setContentView(layout.root)
        bottomSheet.setCanceledOnTouchOutside(true)
        layout.btExit.setOnClickListener {
            bottomSheet.dismiss()
            finish()
        }
        layout.btCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.show()
    }

    @Composable
    fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
        var selectedTabIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        NavigationBar(
            containerColor = c60,
        ) {
            // looping over each tab to generate the views and navigation for each item
            tabBarItems.forEachIndexed { index, tabBarItem ->
                var color = textColor
                var fontWeight = FontWeight.Normal
                if (selectedTabIndex == index) {
                    color = Color.Black
                    fontWeight = FontWeight.ExtraBold
                }
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                            badgeAmount = tabBarItem.badgeAmount
                        )
                    },
                    label = { Text(tabBarItem.title, color = color, fontWeight = fontWeight) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, indicatorColor = c10
                    )
                )
            }
        }
    }

    @Composable
    fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: Int,
        unselectedIcon: Int,
        title: String,
        badgeAmount: Int? = null
    ) {
        BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
            Icon(
                painter = painterResource(
                    id = if (isSelected) {
                        selectedIcon
                    } else {
                        unselectedIcon
                    }
                ), contentDescription = title, tint = if (isSelected) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }
    }

    @Composable
    fun TabBarBadgeView(count: Int? = null) {
        if (count != null) {
            Badge {
                Text(count.toString())
            }
        }
    }

    @Composable
    fun HomeScreen() {
        AndroidViewScreen(R.id.fragment_container_view_home, HomeFragment())
    }

    @Composable
    fun FavoriteScreen() {
        AndroidViewScreen(R.id.fragment_container_view_favorite, FavoritesFragment())

    }


    @Composable
    fun ProfileScreen() {
        AndroidViewScreen(R.id.fragment_container_view_profile, ProfileFragment())
    }

    @Composable
    fun AlertScreen() {
        AndroidViewScreen(R.id.fragment_container_view_alert, NotificationFragment())
    }

    @Composable
    fun AndroidViewScreen(containerId: Int, fragment: Fragment) {
        val context = LocalContext.current
        AndroidView(factory = { ctx ->
            FragmentContainerView(ctx).apply {
                id = containerId
                (context as? FragmentActivity)?.supportFragmentManager?.commit {
                    replace(id, fragment)
                }
            }
        })
    }
}

