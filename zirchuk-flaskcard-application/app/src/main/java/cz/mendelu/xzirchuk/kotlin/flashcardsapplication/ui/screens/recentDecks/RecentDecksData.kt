package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import java.time.LocalDateTime

class RecentDecksData {
    var selectedDeck:Deck = Deck(name = "", cards = arrayListOf(), lastVisited = "", description = "")
}