package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.BuildConfig
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.UINavigation
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DeckResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray4
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecentDecksScreen(navController: NavController,
                      viewModel: RecentDecksViewModel = getViewModel()){

    var decks:MutableList<Deck> = remember{
        mutableListOf<Deck>()
    }
    var sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    //coroutine is needed to switch bottomsheet state
    val coroutineScope = rememberCoroutineScope()

    viewModel.uiState.value.let {
        when(it){
            RecentDecksUIState.Default -> {

            }
            RecentDecksUIState.Loading -> {
                viewModel.getDecks()
            }
            is RecentDecksUIState.Success -> {
                decks.clear()
                decks.addAll(it.decks)
                viewModel.uiState.value = RecentDecksUIState.Default
            }
            RecentDecksUIState.SelectedDeckChanged -> {
                viewModel.uiState.value = RecentDecksUIState.SelectedDeckChanged
            }
        }
    }

    LaunchedEffect(viewModel.uiState){
        viewModel.uiState.value = RecentDecksUIState.Loading
    }

    BottomSheet(
        initialSheetState = sheetState,
        //what is displayed on the bottom sheet
        sheetContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = viewModel.data.selectedDeck.name,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.RecentDecksAmountOfCards) +viewModel.data.selectedDeck.cards.size,
                    modifier = Modifier.padding(10.dp), fontSize = 15.sp,
                )
                Text(
                    text = stringResource(R.string.RecentDecksAproxTime) +viewModel.data.selectedDeck.cards.size*0.8+"min",
                    modifier = Modifier.padding(10.dp),
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TestPickButton(text = stringResource(R.string.RecentDecksFlashcards), icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_note_24),
                            contentDescription = "",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }) {
                        navController.navigate(UINavigation.TestFlashcards.route + "/${viewModel.data.selectedDeck.id}")
                    }

                    TestPickButton(text =  stringResource(R.string.RecentDecksScreenTest), icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_call_to_action_24),
                            contentDescription = "", modifier = Modifier.padding(horizontal = 5.dp)
                        )
                    }) {
                        navController.navigate(UINavigation.TestFillGap.route + "/${viewModel.data.selectedDeck.id}")
                    }
                }
            }

        }){
        //main content
        BottomNavigationWindow(navController = navController) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = BuildConfig.VERSION_NAME, color = gray4)
                DecksToggleButton(navController = navController)
                LazyColumn( modifier = Modifier.padding(8.dp)) {

                    decks.forEach {
                        item(key = it.id){
                            DeckCard(deckName = it.name,
                                remainingAmount = DeckResolver.getRemainingCardsAmount(it.cards),
                                date = it.lastVisited,
                                cardAmount = it.cards.size,
                                deckDescription = it.description,
                                onCardClick = {
                                if(it.cards.size>0){
                                    coroutineScope.launch {
                                        viewModel.data.selectedDeck = it!!
                                        viewModel.uiState.value = RecentDecksUIState.SelectedDeckChanged
                                        sheetState.show()


                                    }}
                            }

                                , onDeleteClick = {viewModel.deleteDeck(it)}
                                , onAddCardClick = {navController.navigate(UINavigation.AddCardtoDeck.route+"/${it.id}")}
                                ,onChangeDeckClick = {navController.navigate(UINavigation.EditDeck.route+"/${it.id}")}
                            )
                        }

                    }

                }
            }

        }
    }
}

@Composable
fun RecentDecksScreen(paddingValues: PaddingValues) {
    Text(text = "Recent Decks")
    
}