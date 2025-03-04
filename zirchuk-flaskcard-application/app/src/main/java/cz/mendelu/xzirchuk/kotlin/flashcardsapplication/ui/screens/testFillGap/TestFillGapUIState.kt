package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsUIState

sealed class TestFillGapUIState {
    object Default:TestFillGapUIState()
    object Loading: TestFillGapUIState()
    class Success(var deck: Deck): TestFillGapUIState()
    object CurrentCardChanged: TestFillGapUIState()

    object ListEmpty:TestFillGapUIState()
    object AnswerFieldChange:TestFillGapUIState()
}