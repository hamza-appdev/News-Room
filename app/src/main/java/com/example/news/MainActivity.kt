package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.news.presentation.newsscreen.NewsScreen
import com.example.news.presentation.newsscreen.NewsViewmodels
import com.example.news.ui.theme.NewsTheme
import com.example.news.util.NewsNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                NewsNavigation()

            }
        }
    }
}

