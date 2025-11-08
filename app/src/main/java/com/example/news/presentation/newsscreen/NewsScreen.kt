package com.example.news.presentation.newsscreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.example.news.R
import com.example.news.data.dto.Article
import com.example.news.presentation.component.Bottomsheetcontent
import com.example.news.presentation.component.NewsArticlecard
import com.example.news.presentation.component.NewsTopBar
import com.example.news.presentation.component.RetryContent
import com.example.news.presentation.component.SearchAppBar
import com.example.news.presentation.component.categoryTabRow
import com.example.news.ui.theme.RoyalBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen (
    state: NewsScreenstate,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadfullstory: (String) -> Unit

) {
    val scrollbehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val category = listOf(
        "General",
        "Business",
        "Health",
        "Technology",
        "Science",
        "Entertainment",
        "Sports"
    )
    val pagerState = rememberPagerState(pageCount = {category.size})

    //listen category changed event
    LaunchedEffect(key1 = Unit) {
        if (state.searchQuery.isNotEmpty()){
            onEvent(NewsScreenEvent.OnScreenQuerychanged(searchQuery = state.searchQuery))
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page->
          onEvent(NewsScreenEvent.OnCategoryChanged(category = category[page]))
        }
    }
    var coroutineScope = rememberCoroutineScope()

    //keyboard
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(enabled = state.isSearchBarVisible) {
        onEvent(NewsScreenEvent.OnCloseIconClicked)
    }

    //Bottom sheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true )
    var shouldBottomSheetshow by remember { mutableStateOf(false) }
    if (shouldBottomSheetshow){
        ModalBottomSheet(
            onDismissRequest = {shouldBottomSheetshow=false},
            sheetState = sheetState,
            content = {
                state.selectedArticle?.let {
                    Bottomsheetcontent(
                        article = it,
                        onReadfullstorybutton = {
                            coroutineScope.launch { sheetState.hide()}.invokeOnCompletion{
                                if (!sheetState.isVisible)shouldBottomSheetshow=false
                            }
                            onReadfullstory(it.url)
                        }
                    )
                }
            }
        )
    }

    //Search bar
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Crossfade(targetState = state.isSearchBarVisible) {isVisiable->
            if (isVisiable){
                Column (){
                    Spacer(modifier = Modifier.height(30.dp))
                    SearchAppBar(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = state.searchQuery,
                        onValueChange ={NewValue->
                            onEvent(NewsScreenEvent.OnScreenQuerychanged(NewValue))
                        } ,
                        oncloseiconclicked ={
                            onEvent(NewsScreenEvent.OnCloseIconClicked)
                        } ,
                        onsearchiconclicked = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                    NewsArticlelist(
                        state = state,
                        onCardClicked = {article->
                            shouldBottomSheetshow=true
                            onEvent(NewsScreenEvent.OnNewCardClicked(article =article ))
                        },
                        onRetry = {
//                            onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                            onEvent(NewsScreenEvent.OnScreenQuerychanged(state.searchQuery))
                        }
                    )
                }
            }else{
                Scaffold(
                    modifier = Modifier
                        .nestedScroll(scrollbehaviour.nestedScrollConnection),
                    topBar = {
                        NewsTopBar(scrollbehaviour, onSearchIconClicked={
                        onEvent(NewsScreenEvent.OnSearchIconClicked)
                            coroutineScope.launch {
                                delay(500)
                                focusRequester.requestFocus()
                            }
                    })}

                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),

                        ){

                        //for categories
                        categoryTabRow(
                            pagerState = pagerState,
                            categories =category,
                            onTabSelected={index->
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)

                                }
                            }
                        )
                        HorizontalPager(
                            state = pagerState
                        ) { page ->
                            // Example content — required for scroll to work
                            NewsArticlelist(
                                state=state,
                                onCardClicked = {article->
                                    shouldBottomSheetshow=true
                                    onEvent(NewsScreenEvent.OnNewCardClicked(article =article ))
                                },
                                onRetry = {
                                    onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                                }
                            )

                        }
                    }
                }

            }

        }
        }
    }



//state:Any value that can be change during the usage of app
//Event:Any action that can user perform

@Composable
fun NewsArticlelist(
    modifier: Modifier = Modifier,
    state: NewsScreenstate,
    onCardClicked:(Article)->Unit,
    onRetry:()->Unit

) {
    Column(
        modifier= Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //for cards
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            items(state.article){article->
                NewsArticlecard(
                    article=article,
                    onCardClicked = onCardClicked
                )
            }
        }

        Box(
            modifier= Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if(state.isLoading){
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadingdots))
                val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){


                // Color filter
                    val dynamicProperties = rememberLottieDynamicProperties(
                        rememberLottieDynamicProperty(
                            property = LottieProperty.COLOR,
                            value = RoyalBlue.toArgb(),
                            keyPath = arrayOf("**") // all layers pe apply karega
                        )
                    )


                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(450.dp),
                        dynamicProperties = dynamicProperties,

                    )}
            }

            if (state.error !=null){
                RetryContent(
                    error = state.error,
                    onRetry = onRetry
                )
            }
        }
    }
}
