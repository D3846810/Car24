package uk.ac.tees.mad.d3846810.screens

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import uk.ac.tees.mad.d3846810.screens.widgets.CustomToolbar


@Composable
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier,
    url: String = "https://www.google.com",
    onBackPress: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            CustomToolbar(title = "Privacy Policy") {
                onBackPress()
            }
            CustomWebView(url = url)
        }
    }
}

@Composable
fun CustomWebView(url: String) {

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }, update = {
        it.loadUrl(url)
    })
}

@Preview
@Composable
private fun PreviewWebView() {
    PrivacyPolicyScreen(url = "https://www.google.com")
}