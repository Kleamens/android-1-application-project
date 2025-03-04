package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.decksGraph

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.BarChartData
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BarGraphViewModel(private val repo:IDecksRepository):BaseViewModel() {
    var uiState : MutableState<BarGraphUIStates> = mutableStateOf(BarGraphUIStates.Loading)
    fun getDecksWithMostCards(amount:Long){
        launch {
            var decks = repo.getAllDecks().first() as ArrayList<Deck>

            var limit = if (decks.size>=amount) amount else decks.size


                var decksWithMostCards = arrayListOf<Deck>()
                for(i in 0..limit.toInt()-1 as Int){
                    var curBiggestDeck = decks.maxBy {
                        it.cards.size
                    }
                    decksWithMostCards.add(curBiggestDeck)
                    decks.remove(curBiggestDeck)
                }
                uiState.value = BarGraphUIStates.Success(decksWithMostCards.toList())







        }
    }

    fun covertDeckListtoBarChartData(decks:List<Deck>):List<BarChartData>{
        var result = ArrayList<BarChartData>()

        for(i in decks){
            result.add(BarChartData(i.name,i.cards.size.toFloat()))
        }
        return result.toList()
    }


}