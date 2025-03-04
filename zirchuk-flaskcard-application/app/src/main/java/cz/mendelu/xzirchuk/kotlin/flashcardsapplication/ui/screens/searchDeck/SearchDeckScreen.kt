package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.UINavigation
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DataStore
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DeckResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray2
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchDeckScreen(navController: NavController,
                     viewModel: SearchDeckViewModel = getViewModel()){
    var data by remember{mutableStateOf(viewModel.data)}// get the data once

    //so we need to use states to update the value
    var decks = remember {
        mutableListOf<Deck>()
    }

    var sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true,
    )
    //coroutine is needed to switch bottomsheet state
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var dataStore = DataStore(context)





    viewModel.uiState.value.let {
        when(it){
            SearchScreenUIState.Default -> {

            }
            SearchScreenUIState.TextFieldChanged ->{

                viewModel.uiState.value = SearchScreenUIState.Default
            }
            is SearchScreenUIState.Success ->{
                decks.clear()
                decks.addAll(it.decks)

            }
            SearchScreenUIState.SelectedDeckChanged -> {
                data = viewModel.data
                viewModel.uiState.value = SearchScreenUIState.Default
            }

        }
    }
    LaunchedEffect( true ){
            viewModel.getPrevSearchData(context)
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
                            text =  stringResource(R.string.RecentDecksAmountOfCards) +viewModel.data.selectedDeck.cards.size,
                            modifier = Modifier.padding(10.dp), fontSize = 15.sp,
                        )
                        Text(
                            text = stringResource(R.string.RecentDecksAproxTime) + viewModel.data.selectedDeck.cards.size*0.8 +"min",
                            modifier = Modifier.padding(10.dp),
                        )
                        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {

                            TestPickButton(text = stringResource(R.string.RecentDecksFlashcards), icon = { Icon(
                                painter = painterResource(id = R.drawable.baseline_note_24),
                                contentDescription = "",
                                modifier = Modifier.padding(horizontal = 5.dp)
                            ) }) {
                                navController.navigate(UINavigation.TestFlashcards.route+"/${viewModel.data.selectedDeck.id}")
                            }

                            TestPickButton(text =  stringResource(R.string.RecentDecksScreenTest), icon = { Icon(
                                painter = painterResource(id = R.drawable.baseline_call_to_action_24),
                                contentDescription = "", modifier = Modifier.padding(horizontal = 5.dp)
                            ) }) {
                                navController.navigate(UINavigation.TestFillGap.route+"/${viewModel.data.selectedDeck.id}")
                            }
                        }

                    }

                }) {


                Column(
                    modifier = Modifier

                        .fillMaxWidth()
                        .background(gray2), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                        Text(text = stringResource(R.string.findDeck), modifier = Modifier.padding(20.dp),fontSize = 30.sp, fontWeight = FontWeight.Bold)

                    }


                    MyTextField(value = data.name, onValueChange = { text ->
                        viewModel.onTextChange(text)

                    }, labelText =  stringResource(R.string.AddDeckName))
                    SquareButton(text = stringResource(R.string.SearchDeckScreenFind)) {
                        viewModel.getDeck(data.name)
                    }




                BottomNavigationWindow(navController = navController, beforeNavigation = {
                    if(data.name != ""){
                        coroutineScope.launch {
                            dataStore.saveSearchName(data.name)
                        }

                    }
                }) {

                LazyColumn() {

                    decks.forEach {
                        item(key = it.id) {
                            DeckCard(
                                deckName = it.name,
                                cardAmount = it.cards.size,
                                remainingAmount = DeckResolver.getRemainingCardsAmount(it.cards),
                                deckDescription = it.description,
                                onCardClick = {
                                    if(it.cards.size>0){

                                        coroutineScope.launch {
                                            sheetState.show()
                                            viewModel.data.selectedDeck = it!!
                                            viewModel.uiState.value = SearchScreenUIState.SelectedDeckChanged
                                        }
                                    }

                                },
                                onDeleteClick = { viewModel.deleteDeck(it) },
                                onAddCardClick =
                                { navController.navigate(UINavigation.AddCardtoDeck.route + "/${it.id}") },
                                onChangeDeckClick = {navController.navigate(UINavigation.EditDeck.route+"/${it.id}")})


                        }

                    }
                }
            }


        }

    }
    }

