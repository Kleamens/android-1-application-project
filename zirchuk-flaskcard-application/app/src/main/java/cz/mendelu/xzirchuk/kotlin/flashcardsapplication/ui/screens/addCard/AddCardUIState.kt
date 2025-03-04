package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard

sealed class AddCardUIState {
    object Default: AddCardUIState()
    object CardChanged: AddCardUIState()
    object Loading: AddCardUIState()
    object CardSaved: AddCardUIState()
}