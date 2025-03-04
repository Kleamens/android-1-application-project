package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.di

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck.EditDeckViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck.AddDeckViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.decksGraph.BarGraphViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks.RecentDecksViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck.SearchDeckViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap.TestFillGapViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashCardsViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsScreen
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel { DeckListViewModel(get()) }
    viewModel { AddDeckViewModel(get()) }
    viewModel { AddCardViewModel(get()) }
    viewModel { SearchDeckViewModel(get()) }
    viewModel { RecentDecksViewModel(get()) }
    viewModel {BarGraphViewModel(get())}
    viewModel { TestFlashCardsViewModel(get())}
    viewModel {TestFillGapViewModel(get())}
    viewModel {EditDeckViewModel(get())}
}