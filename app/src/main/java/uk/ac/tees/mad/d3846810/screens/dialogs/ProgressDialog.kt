package uk.ac.tees.mad.d3846810.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.dialogBackground

@Composable
fun ProgressDialog(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .background(dialogBackground)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.Center)
                .padding(horizontal = 32.dp)
                .background(Color.White, RoundedCornerShape(8.dp)),
        ) {
            CircularProgress(
                modifier = modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CircularProgress(modifier: Modifier = Modifier, isLoading: Boolean = true) {
    var loading by remember { mutableStateOf(isLoading) }

    if (!loading) return

    CircularProgressIndicator(
        modifier = modifier,
        color = c10,
        trackColor = Color.Transparent,
    )
}

@Preview
@Composable
private fun PreviewProgressDialog() {
    ProgressDialog()
}