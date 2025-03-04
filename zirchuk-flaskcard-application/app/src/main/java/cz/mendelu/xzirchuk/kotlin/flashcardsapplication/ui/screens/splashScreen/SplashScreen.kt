package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.splashScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavBarDestinations
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.UINavigation
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController:NavController) {
    var startAnimation by remember{
        mutableStateOf(false)
    }
    val alphaAnimation = animateFloatAsState(targetValue =
        if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        )

        )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(2200)
        navController.navigate(NavBarDestinations.DeckList.route)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R.drawable.logo),
            contentDescription = "",
        modifier = Modifier.padding(16.dp).fillMaxWidth().alpha(alphaAnimation.value))
    }
}