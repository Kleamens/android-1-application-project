package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavBarDestinations(val route:String, val icon: ImageVector) {
    object DeckList : NavBarDestinations(
        route = "list",
        icon = Icons.Filled.List)
    object AddDeck: NavBarDestinations(
        route = "add",
        icon = Icons.Filled.Add)
    object SearchDeck: NavBarDestinations(
        route = "search",
        icon = Icons.Filled.Search
    )
    object RecentDecks: NavBarDestinations(
        route = "recent",
        icon = Icons.Filled.DateRange
    )

    object Graph:NavBarDestinations(
        route = "graph",
        icon = Icons.Default.Info
    )




}
