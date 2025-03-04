package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks.RecentDecksUIState

sealed class DeckListUIState {
    object Default: DeckListUIState()

    object Loading:DeckListUIState()

    object SelectedDeckChanged:DeckListUIState()
    class Success(val decks: List<Deck>) : DeckListUIState()
}