package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.decksGraph

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

sealed class BarGraphUIStates {
    object Default : BarGraphUIStates()
    class Success(var decks:List<Deck>) : BarGraphUIStates()
    object Loading:BarGraphUIStates()
}