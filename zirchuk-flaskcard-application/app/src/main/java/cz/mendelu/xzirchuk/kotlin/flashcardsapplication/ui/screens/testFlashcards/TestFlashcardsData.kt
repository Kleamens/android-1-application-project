package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

class TestFlashcardsData{
     var deck:Deck = Deck("", arrayListOf(),"","")//current itteration deck (the one that is used to display data)
     var areThereDecksToRevise:Boolean = false

     // need to add a deck that would contain cards that were not answered correctly
}