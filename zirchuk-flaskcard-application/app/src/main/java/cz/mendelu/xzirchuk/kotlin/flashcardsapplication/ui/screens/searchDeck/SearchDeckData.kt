package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.searchDeck

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

class SearchDeckData {
    var name:String = ""
    var selectedDeck: Deck = Deck(name = "", cards = arrayListOf(), lastVisited = "", description = "")
}