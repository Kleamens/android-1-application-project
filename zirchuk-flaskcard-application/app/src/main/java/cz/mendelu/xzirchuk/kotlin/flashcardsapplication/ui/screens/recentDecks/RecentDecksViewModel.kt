package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.recentDecks

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.deckList.DeckListUIState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class RecentDecksViewModel(private var repo: IDecksRepository):BaseViewModel(){

    var uiState : MutableState<RecentDecksUIState> = mutableStateOf(RecentDecksUIState.Loading)
    var data  = RecentDecksData()
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDecks(){
        launch {
            var decks:List<Deck> = repo.getAllDecks().first()



            var fiveRecentDecks: List<Deck>


            var deckDates:ArrayList<LocalDateTime> = decks.map {
                LocalDateTime.parse(it.lastVisited)
            } as ArrayList<LocalDateTime>


            var topfiveDates:ArrayList<LocalDateTime> = arrayListOf()
            if(decks.size>=5){
                for(i in 1..5){
                    var max = deckDates.max()

                    topfiveDates.add(max)
                    deckDates.remove(max)

                }
            }else{
                for(i in 1..decks.size){
                    var max = deckDates.max()

                    topfiveDates.add(max)
                    deckDates.remove(max)

                }
            }

             fiveRecentDecks = decks.filter {
                topfiveDates.toList().contains(LocalDateTime.parse(it.lastVisited))
            }


            uiState.value = RecentDecksUIState.Success(fiveRecentDecks)

        }
    }
    fun deleteDeck(deck: Deck){
        launch {
            repo.deleteDeck(deck)
        }
        uiState.value = RecentDecksUIState.Loading
    }


}