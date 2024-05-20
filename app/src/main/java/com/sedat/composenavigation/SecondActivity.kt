package com.sedat.composenavigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sedat.composenavigation.ComposableAnimExtension.exitVerticalTransition

@Composable
fun Screen1(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Red),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SCREEN 1")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate("Screen2")
            }
        ) {
            Text(text = "Go to Screen 2")
        }
    }
}

@Composable
fun Screen2(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.DarkGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SCREEN 2")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate("Screen3")
            }
        ) {
            Text(text = "Go to Screen 3")
        }
    }
}

@Composable
fun Screen3(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "SCREEN 3")
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate("Screen1")
            }
        ) {
            Text(text = "Go to Screen 1")
        }
    }
}

@Composable
fun NavigationApp2() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Screen1"){
        navigateWithAnimation("Screen1"){ Screen1(navController = navController)}
        navigateWithAnimation("Screen2"){ Screen2(navController = navController)}
        navigateWithAnimation("Screen3"){ Screen3(navController = navController)}
    }
}

object ComposableAnimExtension{
    val defaultExitTransition = slideOutHorizontally(
        targetOffsetX = {5000},
        animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing)
    ) + fadeOut(animationSpec = tween(3000)) //çıkış animasyonunu daha yumuşak yapar, çıkarken ekran transparan olarak çıkar.

    val defaultPopEnterTransition = slideInHorizontally(
        initialOffsetX = {-5000}, //yeni ekranın ekranı kaplama hızı (-+ değere göre ekranın sağından ya da solundan animasyon başlar)
        animationSpec = tween(
            2000, //animasyonun gerçekleşme süresi
            easing = FastOutSlowInEasing) //animasyon çeşitleri burada ayarlanır (kübik, yay vb.)
    ) + fadeIn(animationSpec = tween(3000))

    val exitVerticalTransition = slideOutVertically(
        targetOffsetY = {-5000},
        animationSpec = tween(3000, easing = FastOutSlowInEasing)
    )
}

fun NavGraphBuilder.navigateWithAnimation(
    route: String,
    exitTransition: ExitTransition = ComposableAnimExtension.defaultExitTransition,
    enterTransition: EnterTransition = ComposableAnimExtension.defaultPopEnterTransition,
    content: @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit)
){
    composable(
        route = route,
        exitTransition = { exitVerticalTransition /*exitTransition*/ },
        enterTransition = { enterTransition }
    ){
        content(this, it)
    }
}