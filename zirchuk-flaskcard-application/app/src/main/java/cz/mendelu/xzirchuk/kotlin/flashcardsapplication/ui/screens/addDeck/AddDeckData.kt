package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck

import android.os.Build
import androidx.annotation.RequiresApi
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import java.time.LocalDateTime

class AddDeckData {

    var createdDeck:Deck = Deck(name="", cards = arrayListOf(), lastVisited = LocalDateTime.now().toString(), description = "")
    var nameError:String = "Input name"
    var first:Boolean = true
}