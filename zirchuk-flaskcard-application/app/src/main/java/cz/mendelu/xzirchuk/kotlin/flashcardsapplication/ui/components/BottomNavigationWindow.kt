package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavBarDestinations
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray3
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray4

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationWindow(
    navController: NavController,
    beforeNavigation:()->Unit = {},
    content:@Composable (it:PaddingValues)->Unit
){
    Scaffold(
        bottomBar = { BottomBar(navController, beforeNavigation = beforeNavigation)}
    ) {
        content(it)
    }

}

@Composable
fun BottomBar(navController: NavController,beforeNavigation: () -> Unit){
    val screens = listOf<NavBarDestinations>(
        NavBarDestinations.DeckList,
        NavBarDestinations.AddDeck,
        NavBarDestinations.SearchDeck,

        NavBarDestinations.Graph
    )

    val currentDestionation = navController.currentBackStackEntry?.destination
    //gives the current location

    NavigationBar(containerColor = Color.Gray) {
        screens.forEachIndexed{index,item->
            NavigationBarItem(

                selected =if (item.route == currentDestionation?.route)
                {
                    true
                }
                else{
                          if(item.route == NavBarDestinations.DeckList.route &&
                                  currentDestionation?.route == NavBarDestinations.RecentDecks.route)
                          {
                              true
                          }else{
                              false
                          }

                          },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Gray.copy(alpha = 0.0f)),
                onClick =
                {

                    beforeNavigation()
                    navController.navigate(item.route){
                        popUpTo(route = NavBarDestinations.DeckList.route){

                        }
                        launchSingleTop = true


                    }

                },
                icon = { if(item.route == NavBarDestinations.Graph.route){
                    Icon(painter = painterResource(id = R.drawable.baseline_bar_chart_24),
                        contentDescription = "",
                        tint = if(item.route == currentDestionation?.route){
                        Color.White
                    }else{
                        gray4
                    })
                }else{
                    if (currentDestionation != null) {
                        Icon(imageVector = item.icon,
                            contentDescription = "",
                            tint = if(item.route == NavBarDestinations.DeckList.route
                                && currentDestionation.route == NavBarDestinations.RecentDecks.route){
                                Color.White
                            } else{
                                if(item.route == currentDestionation.route){
                                    Color.White
                                }else{
                                    gray4
                                }

                            }
                        )
                    }
                }

                }


            )



        }

    }


}