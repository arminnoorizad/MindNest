package com.example.mindNest.view.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("splash_screen_animation.json")
    )


    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )



    LaunchedEffect(key1 = true) {
        delay(3000)
        onSplashFinished()
    }



    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.fillMaxSize()
    )
}





