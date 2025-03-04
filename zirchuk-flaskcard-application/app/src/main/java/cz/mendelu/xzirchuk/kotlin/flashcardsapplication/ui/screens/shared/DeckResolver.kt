package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard.AddCardUIState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

object DeckResolver {
    fun getRemainingCardsAmount(deck:ArrayList<Card>):Int{
        return deck.filter {
            var now = LocalDateTime.now()
            var cardDate = LocalDateTime.parse(it.lastCompleted)
            if(now.year == cardDate.year){
                if(cardDate.month == now.month){
                    cardDate.dayOfMonth != now.dayOfMonth
                }else{
                    true
                }
            }else{
                true
            }
        }.size
    }

    suspend fun refreshDeck(repo:IDecksRepository,deckId:Long){
        var deck: Deck = repo.getDeckById(deckId!!)


        deck.cards.forEach {
            it.lastCompleted = LocalDateTime.MIN.toString()
        }


        repo.insertCard(deck.cards,deckId!!)

    }

    suspend fun getDeckWithDateCons(repo: IDecksRepository,id:Long,): Deck {
        var deck = repo.getDeckById(id)

        var cardsFiltered = deck.cards.filter {
            var now = LocalDateTime.now()
            var cardDate = LocalDateTime.parse(it.lastCompleted)
            now>cardDate

                if(now.year == cardDate.year){
                    if(cardDate.month == now.month){
                        cardDate.dayOfMonth != now.dayOfMonth
                    }else{
                        true
                    }
                }else{
                    true
                }



        }
        deck.cards.clear()
        deck.cards.addAll(cardsFiltered)

        return deck
    }
    suspend fun getUncompletedCards(repo: IDecksRepository,id:Long):ArrayList<Card>{
        var deck = repo.getDeckById(id)

        var cardsFiltered = deck.cards.filter {
            !it.complete
        }
        deck.cards.clear()
        deck.cards.addAll(cardsFiltered)

        return deck.cards
    }



    suspend fun updateCardDate(card:Card,repo: IDecksRepository,deckId: Long){
        var deck = repo.getDeckById(deckId!!)

        var index = deck.cards.indexOf(card)

        deck.cards.set(index,card.copy(lastCompleted = LocalDateTime.now().toString()))

        repo.insertCard(deck.cards,deckId!!)
    }
    suspend fun addCardToDeck(repo: IDecksRepository, card: Card,deckId: Long){



            var cards = repo.getDeckById(deckId!!).cards
            var index = cards.indexOf(card)
            cards.set(index,card)

            repo.insertCard(cards,deckId!!)


        }


}