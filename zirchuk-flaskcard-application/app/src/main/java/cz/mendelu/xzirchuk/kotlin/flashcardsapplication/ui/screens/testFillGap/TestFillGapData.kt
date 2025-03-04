package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck

class TestFillGapData {
    var deck: Deck = Deck("", arrayListOf(),"","")
    var answer:String = ""
    var result:String=""
    var areThereDecksToRevise:Boolean = false

    // need to add a deck that would contain cards that were not answered correctly
}