package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DeckResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsData
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsUIState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TestFillGapViewModel(private val repo:IDecksRepository):BaseViewModel() {
    var uiState:MutableState<TestFillGapUIState> = mutableStateOf(TestFillGapUIState.Loading)
    var data = TestFillGapData()
    var deckId:Long?=null
    fun getDeck(id: Long) {
        launch {
            var deck = DeckResolver.getDeckWithDateCons(repo=repo,id=deckId!!)



            if(deck.cards.isEmpty()){
                uiState.value = TestFillGapUIState.ListEmpty
            }else{
                uiState.value = TestFillGapUIState.Success(deck)
            }
        }
    }

    fun onAnswerChange(value:String){
        data.answer = value
        uiState.value = TestFillGapUIState.AnswerFieldChange
    }

    fun checkAnswer(value:String,card: Card):Boolean{
        val result = value.lowercase().equals(card.back)
        if(result){
            card.complete = true
            launch {
                DeckResolver.addCardToDeck(repo = repo , card = card, deckId = deckId!!)
            }
            data.result = "You got it"
        }else{
            card.complete = false
            launch {
                DeckResolver.addCardToDeck(repo = repo , card = card, deckId = deckId!!)
            }
            data.result = "Wrong,there is always next time"
            data.areThereDecksToRevise = true
        }
        return result
    }

    //result - if user got the answer right
    fun nextCard(result:Boolean):Boolean//return true - if no more cards , false- if there there are cards
    {
            if(result) {
                updateCardDate(data.deck.cards.first())
            }

            data.deck.cards.remove(data.deck.cards.first())

        uiState.value = TestFillGapUIState.CurrentCardChanged
        return data.deck.cards.isEmpty()
    }

    fun updateCardDate(card: Card){
        launch {
            DeckResolver.updateCardDate(card,repo= repo, deckId = deckId!!)
        }


    }

    fun refreshDeck(){
        launch {
            DeckResolver.refreshDeck(repo=repo, deckId = deckId!!)
            uiState.value = TestFillGapUIState.Loading
        }
    }

    fun getUncompletedCards(){
        launch {
            var cards = DeckResolver.getUncompletedCards(repo = repo,id=deckId!!)
            data.deck.cards = cards
            uiState.value = TestFillGapUIState.Success(data.deck)
        }

    }

    //todo dont show the card if todays date and cards date are the same

}