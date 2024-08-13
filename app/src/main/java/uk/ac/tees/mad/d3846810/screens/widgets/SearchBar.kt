package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3846810.theme.c60
import uk.ac.tees.mad.d3846810.theme.gray
import uk.ac.tees.mad.d3846810.theme.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String = "",
    isActive: Boolean = false,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = { },
    onActiveChange: (Boolean) -> Unit = {}
) {
    SearchBar(modifier = modifier.fillMaxWidth(),
        query = query,//text showed on SearchBar
        onQueryChange = {
            onQueryChange(it)
        }, //update the value of searchText
        onSearch = {
            onSearch(it)
        }, //the callback to be invoked when the input service triggers the ImeAction.Search action
        active = isActive, //whether the user is searching or not
        onActiveChange = { onActiveChange(isActive) }, //the callback to be invoked when this search bar's active state is changed
        shape = RoundedCornerShape(8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = c60,
            inputFieldColors = TextFieldDefaults.colors(
                unfocusedContainerColor = c60,
                focusedContainerColor = c60,
                focusedIndicatorColor = c60,
                unfocusedIndicatorColor = c60,
                disabledIndicatorColor = c60,
            )
        ),
                placeholder = {
            Text(text = "Search Here", color = Color(0xFF7B8E9D))
        }, leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = Color(0xFF49454F))
        }, trailingIcon = {
            if (isActive) {
                Icon(
                    imageVector = Icons.Filled.Close, contentDescription = ""
                )
            }
        }) {}
}

@Preview
@Composable
private fun PreviewSearchBar() {
    CustomSearchBar()
}