package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck

sealed class AddDeckUIState {
    object Default:AddDeckUIState()
    object FieldChanged:AddDeckUIState()

}