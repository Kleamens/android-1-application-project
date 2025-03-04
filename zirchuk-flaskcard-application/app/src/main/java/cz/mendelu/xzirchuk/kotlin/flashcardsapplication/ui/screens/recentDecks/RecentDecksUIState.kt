package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState

sealed class RecentDecksUIState {
    object Default : RecentDecksUIState()
    object Loading : RecentDecksUIState()

    object SelectedDeckChanged: RecentDecksUIState()


    class Success(var decks:List<Deck>) : RecentDecksUIState()
}