package com.example.news.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun categoryTabRow(
    pagerState: PagerState,
    categories: List<String>,
    onTabSelected:(Int)-> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        categories.forEachIndexed { index, category->
            Tab(
                selected = pagerState.currentPage==index,
                onClick = {onTabSelected(index)},
                content = {
                    Text(text = category,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                    )
                }
            )

        }
    }


}