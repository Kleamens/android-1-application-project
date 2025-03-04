package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavBarDestinations
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.*

@Composable
fun DecksToggleButton(


    navController:NavController
){
    Row(
        modifier = Modifier
            .fillMaxWidth().padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        var currentDestination = navController.currentBackStackEntry?.destination

        val screens = listOf<NavBarDestinations>(
            NavBarDestinations.DeckList,


            NavBarDestinations.RecentDecks,

        )
        val destinationList = listOf(stringResource(R.string.DecksToggleAll),
                    stringResource(R.string.DeckToggleRecent)
                )
        val cornerRadius = 5.dp

        var selectedIndex by remember { mutableStateOf(-1) }

        screens.forEachIndexed { index, item ->

            OutlinedButton(
                onClick = {navController.navigate(item.route)},
                modifier = when (index) {
                    0 ->
                        Modifier
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedIndex == index) 1f else 0f)
                            .height(35.dp)
                    else ->
                        Modifier
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (selectedIndex == index) 1f else 0f)
                            .height(35.dp)
                },
                shape = when (index) {
                    0 -> RoundedCornerShape(
                        topStart = cornerRadius,
                        topEnd = 0.dp,
                        bottomStart = cornerRadius,
                        bottomEnd = 0.dp
                    )
                    screens.size - 1 -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = cornerRadius,
                        bottomStart = 0.dp,
                        bottomEnd = cornerRadius
                    )
                    else -> RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                },

                colors = if (currentDestination?.route == screens.get(index%2).route && currentDestination?.route == screens.get(0).route) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = cyan.copy(alpha = 0.8f),
                        contentColor = gray5
                    )
                } else if(currentDestination?.route == screens.get(index%2).route && currentDestination?.route == screens.get(1).route){
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = magenta.copy(alpha = 0.8f),
                        contentColor = gray5
                    )
                }else{
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = gray5
                    )
                }
            ) {
                Text( destinationList.get(index), fontSize = 16.sp)
            }
        }
    }
}