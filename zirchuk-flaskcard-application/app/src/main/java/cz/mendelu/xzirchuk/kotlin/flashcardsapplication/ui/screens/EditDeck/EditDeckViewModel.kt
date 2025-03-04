package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardData
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardUIState
import kotlinx.coroutines.launch

class EditDeckViewModel(private val repo:IDecksRepository):BaseViewModel() {
    var data = EditDeckData()
    var deckId:Long? = null
    var uiState: MutableState<EditDeckUIStates> =
        mutableStateOf(EditDeckUIStates.Loading)

    fun getDeck(){
        launch {
            var deck = repo.getDeckById(deckId!!)
            data.editedDeck = deck
            uiState.value = EditDeckUIStates.Success(deck = deck)
        }
    }

    fun onNameChange(value:String){
        data.editedDeck.name = value
        uiState.value = EditDeckUIStates.DeckChanged
    }

    fun onDescChange(value:String){
        data.editedDeck.description = value
        uiState.value = EditDeckUIStates.DeckChanged
    }

    fun save(){
        launch {repo.updateDeck(deck = data.editedDeck)
        }

    }


}