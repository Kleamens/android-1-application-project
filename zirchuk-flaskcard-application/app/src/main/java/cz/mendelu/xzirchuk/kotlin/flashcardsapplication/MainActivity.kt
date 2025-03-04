package cz.mendelu.xzirchuk.kotlin.flashcardsapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavBarDestinations
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavGraph
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.UINavigation
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.FlashCardsApplicationTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashCardsApplicationTheme {
                // A surface container using the 'background' color from the theme
                NavGraph(startDestination = UINavigation.SplashScreen.route)
            }
        }
    }
}
