package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

sealed class SearchScreenUIState  {
    object Default: SearchScreenUIState()
    object TextFieldChanged : SearchScreenUIState()

    object SelectedDeckChanged:SearchScreenUIState()
    class Success(var decks:List<Deck>): SearchScreenUIState()
}