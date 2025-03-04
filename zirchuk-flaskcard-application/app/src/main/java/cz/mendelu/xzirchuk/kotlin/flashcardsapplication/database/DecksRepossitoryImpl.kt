package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.flow.Flow

class DecksRepossitoryImpl(private val decksDao: DecksDao):IDecksRepository {
    override fun getAllDecks(): Flow<List<Deck>> {
        return decksDao.getAllDecks()
    }

    override suspend fun insertCard(cards:ArrayList<Card> , id:Long) {
       return  decksDao.insertCard(cards, id = id)
    }

    override suspend fun updateDeckName(name: String, id: Long) {
        decksDao.updateDeckName(name,id)
    }



    override suspend fun insertDeck(deck: Deck): Long {
        return decksDao.insertDeck(deck)
    }

    override suspend fun getDeckById(id: Long):Deck{
        return decksDao.getDeckById(id)
    }

    override suspend fun getDeckByName(name: String): List<Deck> {
        return decksDao.getDeckByName(name)
    }

    override suspend fun deleteDeck(deck: Deck) {
        return decksDao.deleteDeck(deck)
    }

    override suspend fun updateDeck(deck: Deck) {
        decksDao.updateDeck(deck = deck)
    }
}