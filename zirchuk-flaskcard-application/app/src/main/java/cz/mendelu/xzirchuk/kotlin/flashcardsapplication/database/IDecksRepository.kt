package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database

import androidx.room.Query
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.flow.Flow

interface IDecksRepository {


    fun getAllDecks(): Flow<List<Deck>>

    suspend fun insertCard(cards:ArrayList<Card> , id:Long)

    suspend fun updateDeckName(name:String , id:Long)

    suspend fun insertDeck(deck: Deck):Long

    suspend fun getDeckById(id:Long):Deck

    suspend fun getDeckByName(name:String):List<Deck>

    suspend fun deleteDeck(deck: Deck)

    suspend fun updateDeck(deck:Deck)
}