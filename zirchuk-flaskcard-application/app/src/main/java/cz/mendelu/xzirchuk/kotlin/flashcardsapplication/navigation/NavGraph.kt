package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck.EditDeckScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck.AddDeckScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DecksListScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks.RecentDecksScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck.SearchDeckScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.splashScreen.SplashScreen

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap.TestFillGapScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navController :NavHostController = rememberNavController(),
             startDestination:String){
    NavHost(
        navController = navController,
        startDestination = startDestination)
    {
        composable(route = NavBarDestinations.AddDeck.route){
            AddDeckScreen(navController = navController)
        }
        composable(route = NavBarDestinations.DeckList.route){
            DecksListScreen(navController = navController)
        }
        composable(route = NavBarDestinations.SearchDeck.route){
            SearchDeckScreen(navController = navController)
        }
        composable(route = NavBarDestinations.RecentDecks.route){
            RecentDecksScreen(navController = navController)
        }
        composable(route = UINavigation.AddCardtoDeck.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = -1L
            }))
        {
            val id = it.arguments?.getLong("id")
            AddCardScreen(id = id,navController)
        }
        composable(route = NavBarDestinations.Graph.route){
            BarGraphScreen(navController = navController)
        }
        composable(route = UINavigation.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(route = UINavigation.TestFlashcards.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = -1L
            }))
        {
            val id = it.arguments?.getLong("id")
            TestFlashcardsScreen(id = id!!, navController = navController)
        }

        composable(route = UINavigation.EditDeck.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = -1L
            }))
        {
            val id = it.arguments?.getLong("id")
            EditDeckScreen(id = id!!, navController = navController)
        }


        composable(route = UINavigation.TestFillGap.route + "/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.LongType
                defaultValue = -1L
            }))
        {
            val id = it.arguments?.getLong("id")
            TestFillGapScreen(id = id!!, navController = navController)
        }
    }

}