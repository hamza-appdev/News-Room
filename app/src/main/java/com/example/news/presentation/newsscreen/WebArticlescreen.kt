package com.example.news.presentation.newsscreen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebArticlescreen(
    modifier: Modifier = Modifier,
    url: String?,
    onBackpress: () -> Unit
    ) {
    var isLoading by remember { mutableStateOf(true) }
    var context = LocalContext.current

    Scaffold(
        modifier= Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Article", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackpress) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "BackButton",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) {innerpadding->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerpadding),
            contentAlignment = Alignment.Center
        ){
            AndroidView(factory = {
                WebView(context).apply {
                    webViewClient = object : WebViewClient(){
                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading=false
                        }
                    }
                    loadUrl(url?:"")
                }
            })
            if (isLoading && url !=null){
                CircularProgressIndicator()
            }

        }
    }
}