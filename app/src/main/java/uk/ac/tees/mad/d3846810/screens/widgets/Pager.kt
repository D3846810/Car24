package uk.ac.tees.mad.d3846810.screens.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.d3846810.model.SliderModel
import uk.ac.tees.mad.d3846810.screens.listitems.SliderItem
import uk.ac.tees.mad.d3846810.theme.c10
import uk.ac.tees.mad.d3846810.theme.c60
import kotlinx.coroutines.delay

@Composable
fun Pager(modifier: Modifier = Modifier, sliderList: List<SliderModel> = arrayListOf()) {
    val pagerState = rememberPagerState(pageCount = { sliderList.size })

    if (sliderList.isNotEmpty()) {
//    // Auto play
        LaunchedEffect(key1 = true) {
            while (true) {
                delay(5000)
                with(pagerState) {
                    scrollToPage(page = (currentPage + 1) % sliderList.size)
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier.fillMaxWidth()
        ) { page ->
            // Our page content
            val slide = sliderList[page]
            SliderItem(sliderModel = slide) {}
        }
        Row(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        ) {
            repeat(sliderList.size) {
                if (pagerState.currentPage == it) {
                    Box(
                        modifier = modifier
                            .width(24.dp)
                            .height(10.dp)
                            .padding(2.dp)
                            .background(c10, RoundedCornerShape(100.dp))
                    )

                } else {
                    Box(
                        modifier = modifier
                            .width(10.dp)
                            .height(10.dp)
                            .padding(2.dp)
                            .background(c60, RoundedCornerShape(100.dp))
                    )
                }
            }
        }
    }
}