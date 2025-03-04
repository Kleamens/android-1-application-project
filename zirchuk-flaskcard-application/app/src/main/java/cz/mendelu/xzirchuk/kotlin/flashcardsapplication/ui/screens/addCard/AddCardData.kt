package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import java.time.LocalDateTime

class AddCardData {
    @RequiresApi(Build.VERSION_CODES.O)
    var deckEdited = Deck(name = "placeholder",
        cards = arrayListOf<Card>(),
        lastVisited = LocalDateTime.now().toString(), description = "")
    var imageInserted :Uri?=null

    var editedCard = Card("null", "","",LocalDateTime.MIN.toString(),false)

    var frontError:String = "Front cant be empty"
    var backError:String = "Back cant be empty"
}