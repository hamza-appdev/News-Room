package com.example.news.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.news.R

@Composable
fun RetryContent(
    modifier: Modifier = Modifier,
    error: String,
    onRetry: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(42.dp))

        Text(text = error, color = Color.Red)
        Spacer(modifier= Modifier.height(25.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text("Retry")
        }


    }



}