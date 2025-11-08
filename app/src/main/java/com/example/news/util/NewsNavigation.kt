package com.example.news.util

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.news.presentation.newsscreen.NewsScreen
import com.example.news.presentation.newsscreen.NewsViewmodels
import com.example.news.presentation.newsscreen.WebArticlescreen

@Composable
fun  NewsNavigation() {
    val argskey = "web_url"
    val navController = rememberNavController()
    NavHost(navController=navController, startDestination = "NewsScreen") {
       composable("NewsScreen") {
           val viewModel: NewsViewmodels = hiltViewModel()
           NewsScreen(
               state = viewModel.state,
               onEvent = viewModel::onEvent,
               onReadfullstory = { url->
                   navController.navigate("WebArticlescreen?$argskey=$url")

               }
           )
       }

        composable(
            "WebArticlescreen?$argskey={$argskey}",
            arguments = listOf(navArgument(name = argskey){
                type= NavType.StringType
            })
            ) {backstackEntry->

            WebArticlescreen(
                url =backstackEntry.arguments?.getString(argskey),
                onBackpress ={
                    navController.navigateUp()
                }
            )
        }
    }

}