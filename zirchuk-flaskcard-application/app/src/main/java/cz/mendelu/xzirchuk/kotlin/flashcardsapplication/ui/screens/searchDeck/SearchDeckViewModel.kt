package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchDeckViewModel(private var repo:IDecksRepository):BaseViewModel() {
    var data: SearchDeckData = SearchDeckData()
    var uiState: MutableState<SearchScreenUIState> = mutableStateOf(SearchScreenUIState.Default)

    fun onTextChange(text:String){
        data.name = text
        uiState.value = SearchScreenUIState.TextFieldChanged
    }

    suspend fun getPrevSearchData(context:Context){
        var dataStore = DataStore(context)

        data.name = dataStore.getSearchName.first()

        uiState.value = SearchScreenUIState.TextFieldChanged

    }
    fun getDeck(name:String){
        launch {
            var decks = repo.getAllDecks().first()
            var pattern = ".*${name}.*"
            if(name.isEmpty()){
                pattern = " "
            }
            var regex = Regex("${pattern}")

            var decks_filtered = decks.filter {
                 it.name.contains(regex)
            }
                uiState.value = SearchScreenUIState.Success(decks_filtered)

        }

    }

    fun deleteDeck(deck: Deck){
        launch {
            repo.deleteDeck(deck)
        }
        uiState.value = SearchScreenUIState.TextFieldChanged
    }
}