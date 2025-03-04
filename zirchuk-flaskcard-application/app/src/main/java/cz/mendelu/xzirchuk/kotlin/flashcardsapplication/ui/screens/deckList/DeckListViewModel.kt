package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DeckListViewModel(private val repo:IDecksRepository):BaseViewModel() {
    var UIState: MutableState<DeckListUIState> = mutableStateOf(DeckListUIState.Loading)
    var deckListData = DeckListData()
    fun loadDecks(){
        launch {
            repo.getAllDecks().collect(){
                UIState.value = DeckListUIState.Success(it)
            }
        }
    }

    fun deleteDeck(deck: Deck){
        launch {
            repo.deleteDeck(deck)
        }
        UIState.value = DeckListUIState.Loading
    }


}