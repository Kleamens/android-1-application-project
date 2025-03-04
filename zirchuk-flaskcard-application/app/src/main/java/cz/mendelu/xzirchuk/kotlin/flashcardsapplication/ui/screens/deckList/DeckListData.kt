package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

class DeckListData {
    var selectedDeck:Deck = Deck(name = "", cards = arrayListOf(), lastVisited = "", description = "")
}