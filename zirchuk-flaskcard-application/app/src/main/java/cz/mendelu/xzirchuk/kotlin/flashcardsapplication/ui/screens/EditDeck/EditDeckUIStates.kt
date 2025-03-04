package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardUIState
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState

sealed class EditDeckUIStates {
    object Default: EditDeckUIStates()
    object DeckChanged: EditDeckUIStates()
    object Loading: EditDeckUIStates()
    object DeckSaved: EditDeckUIStates()
    class Success(val deck: Deck) : EditDeckUIStates()
}