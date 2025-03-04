package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database

import androidx.room.*
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.flow.Flow

@Dao
interface DecksDao {
     @Query ("SELECT * FROM decks")
     fun getAllDecks():Flow<List<Deck>>

     @Query("UPDATE decks SET cards = :cards WHERE id= :id")
     suspend fun insertCard(cards:ArrayList<Card> , id:Long)

     @Update
    suspend fun updateDeck(deck:Deck)


     @Query("UPDATE decks SET name = :name WHERE id= :id")
     suspend fun updateDeckName(name:String , id:Long)
     @Insert
     suspend fun insertDeck(deck: Deck):Long

     @Query("SELECT * FROM decks WHERE id= :id")
     suspend fun getDeckById(id:Long):Deck

     @Query("SELECT * FROM decks WHERE name= :name")
     suspend fun getDeckByName(name:String):List<Deck>

     @Delete
      suspend fun deleteDeck(deck: Deck)




}