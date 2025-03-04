package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddDeckViewModel(private val repo:IDecksRepository):BaseViewModel() {

    var data = AddDeckData()

   // var uiState:MutableState<>

    var uiState: MutableState<AddDeckUIState> = mutableStateOf(AddDeckUIState.FieldChanged)
    @RequiresApi(Build.VERSION_CODES.O)
    fun createDeck(name:String,description:String){

        launch {
            repo.insertDeck(Deck(name = name, cards = arrayListOf(), lastVisited = LocalDateTime.now().toString(), description = description))
        }

    }

    fun onNameChange(value:String){
        if(value.isEmpty()){
            data.nameError = "Name cant be empty"
        }else{
            data.nameError = ""
        }
        data.createdDeck.name = value
        uiState.value = AddDeckUIState.FieldChanged

    }

    fun onDescriptionChange(value:String){
        data.createdDeck.description = value
        uiState.value = AddDeckUIState.FieldChanged
    }


}