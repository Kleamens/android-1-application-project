package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards

import android.R.attr
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DeckResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.ImageResolver
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class TestFlashCardsViewModel(private val repo:IDecksRepository):BaseViewModel() {
    var deckId:Long?=null
    var uiState: MutableState<TestFlashcardsUIState> = mutableStateOf(TestFlashcardsUIState.Loading)
    var data = TestFlashcardsData()

    fun getDeck(id: Long) {
        launch {
            var deck = DeckResolver.getDeckWithDateCons(repo = repo,deckId!!)


            if(deck.cards.isEmpty()){
                uiState.value = TestFlashcardsUIState.ListEmpty
            }else{
                uiState.value = TestFlashcardsUIState.Success(deck)
            }


        }
    }
    //
    fun onGoodClick(card: Card):Boolean{//false - if still no empty ,true - if is empty
        if(!data.deck.cards.isEmpty()){


            data.deck.cards.remove(card)
            card.complete = true
            updateCardDate(card)

            uiState.value = TestFlashcardsUIState.CurrentCardChanged

            return data.deck.cards.isEmpty()
        }else{
            card.complete = true
            updateCardDate(card)
            uiState.value = TestFlashcardsUIState.ListEmpty
            areThereUncompDecks()

            return true
        }

    }
    fun onBadClick(card: Card):Boolean{//false - if still no empty ,true - if is empty
        if(!data.deck.cards.isEmpty()){


            data.deck.cards.remove(card)

            card.complete = false
            launch {
                DeckResolver.addCardToDeck(repo = repo, card = card, deckId = deckId!!)
            }

            uiState.value = TestFlashcardsUIState.CurrentCardChanged

            return data.deck.cards.isEmpty()
        }else{

            uiState.value = TestFlashcardsUIState.CurrentCardChanged

            card.complete = false
            launch {
                DeckResolver.addCardToDeck(repo = repo, card = card, deckId = deckId!!)
            }

            areThereUncompDecks()

            return true
        }

    }

    fun updateCardDate(card:Card){
        launch {
            DeckResolver.updateCardDate(card = card,repo=repo, deckId = deckId!!)
        }


    }

    fun refreshDeck(){
        launch {
            DeckResolver.refreshDeck(repo = repo, deckId = deckId!!)
            uiState.value = TestFlashcardsUIState.Loading
        }

    }

    fun getUncompletedCards(){
            launch {
                var cards = DeckResolver.getUncompletedCards(repo = repo,id=deckId!!)
                data.deck.cards = cards
                uiState.value = TestFlashcardsUIState.Success(data.deck)
            }

    }

    fun areThereUncompDecks(){
        launch {
            data.areThereDecksToRevise = DeckResolver.getUncompletedCards(repo=repo, id = deckId!!).isEmpty()
        }
    }



    }
//todo dont show the card if todays date and cards date are the same







