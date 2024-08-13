package uk.ac.tees.mad.d3846810.screens.navigation

data class TabBarItem(
        val title: String,
        val selectedIcon: Int,
        val unselectedIcon: Int,
        val badgeAmount: Int? = null
    )