package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

sealed class TestFlashcardsUIState {
    object Default:TestFlashcardsUIState()
    object Loading:TestFlashcardsUIState()
    class Success(var deck:Deck):TestFlashcardsUIState()
    object CurrentCardChanged:TestFlashcardsUIState()

    object ListEmpty:TestFlashcardsUIState()



}