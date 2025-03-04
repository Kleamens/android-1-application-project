package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import java.time.LocalDateTime

class EditDeckData {
    var editedDeck: Deck = Deck(name="", cards = arrayListOf(), lastVisited = LocalDateTime.now().toString(), description = "")
    var nameError:String = "Input name"
}