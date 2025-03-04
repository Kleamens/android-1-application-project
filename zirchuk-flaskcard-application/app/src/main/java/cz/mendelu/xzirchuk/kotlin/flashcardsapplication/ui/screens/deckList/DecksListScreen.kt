package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.BuildConfig
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.UINavigation
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DeckResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray3
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray4
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DecksListScreen(navController: NavController,viewModel: DeckListViewModel = getViewModel()) {
    var decks = remember {
        mutableListOf<Deck>()
    }
    //initial state of bottom sheet
    var sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    //coroutine is needed to switch bottomsheet state
    val coroutineScope = rememberCoroutineScope()



    viewModel.UIState.value.let {
        when (it) {
            DeckListUIState.Default -> {

            }
            is DeckListUIState.Success -> {
                decks.clear()
                decks.addAll(it.decks)
                viewModel.UIState.value = DeckListUIState.Default
            }
            DeckListUIState.Loading -> {
                viewModel.loadDecks()
            }
            DeckListUIState.SelectedDeckChanged -> {
                viewModel.UIState.value = DeckListUIState.Default
            }
        }
    }
    LaunchedEffect(viewModel.UIState){
        viewModel.UIState.value = DeckListUIState.Loading
    }
    
        BottomSheet(
            initialSheetState = sheetState,
            //what is displayed on the bottom sheet
            sheetContent = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = viewModel.deckListData.selectedDeck.name,
                        modifier = Modifier.padding(10.dp),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(R.string.RecentDecksAmountOfCards)+viewModel.deckListData.selectedDeck.cards.size,
                        modifier = Modifier.padding(10.dp), fontSize = 15.sp,
                    )
                    Text(
                        text = stringResource(R.string.RecentDecksAproxTime) +viewModel.deckListData.selectedDeck.cards.size*0.8 + "min",
                        modifier = Modifier.padding(10.dp),
                        )
                    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                        TestPickButton(text = stringResource(R.string.RecentDecksFlashcards), icon = { Icon(
                            painter = painterResource(id = R.drawable.baseline_note_24),
                            contentDescription = "",
                            modifier = Modifier.padding(horizontal = 5.dp)
                        ) }) {
                            navController.navigate(UINavigation.TestFlashcards.route+"/${viewModel.deckListData.selectedDeck.id}")
                        }

                        TestPickButton(text = stringResource(R.string.RecentDecksScreenTest), icon = { Icon(
                            painter = painterResource(id = R.drawable.baseline_call_to_action_24),
                            contentDescription = "", modifier = Modifier.padding(horizontal = 5.dp)
                        ) }) {
                            navController.navigate(UINavigation.TestFillGap.route+"/${viewModel.deckListData.selectedDeck.id}")
                        }
                    }

                }

            }) {
            //main content
            BottomNavigationWindow(navController = navController) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    if(decks.isEmpty()){
                        Text(text = "You dont have any decks")
                    }else{
                        Text(text = BuildConfig.VERSION_NAME, color = gray4)
                        DecksToggleButton(navController = navController)



                        LazyColumn( modifier = Modifier.padding(8.dp)) {

                            decks.forEach {
                                var remainingAmount = DeckResolver.getRemainingCardsAmount(it.cards)
                                item(key = it.id){
                                    DeckCard(deckName = it.name,
                                        cardAmount = it.cards.size,
                                        deckDescription = it.description,
                                        remainingAmount = remainingAmount,
                                        onCardClick = {

                                            if(it.cards.size>0){
                                                coroutineScope.launch {
                                                    viewModel.deckListData.selectedDeck = it!!
                                                    viewModel.UIState.value = DeckListUIState.SelectedDeckChanged
                                                    sheetState.show()


                                                }}


                                        }

                                        , onDeleteClick = {viewModel.deleteDeck(it)}
                                        , onAddCardClick = {navController.navigate(UINavigation.AddCardtoDeck.route+"/${it.id}")},
                                        onChangeDeckClick = {navController.navigate(UINavigation.EditDeck.route+"/${it.id}")}
                                    )
                                }

                            }

                        }
                    }

                }



            }
        }
}
@Composable
fun DecksListScreenContent(paddingValues: PaddingValues) {


}