package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.architecture.BaseViewModel
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.database.IDecksRepository
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.ImageResolver
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class AddCardViewModel(private var repo:IDecksRepository):BaseViewModel(){

    var data = AddCardData()
    var deckId:Long? = null
    var uiState: MutableState<AddCardUIState> =
        mutableStateOf(AddCardUIState.Loading)




    fun initCard(){
        launch {
            data.deckEdited = repo.getDeckById(deckId!!)
            uiState.value = AddCardUIState.CardChanged
        }
    }
    fun  addCardToDeck(context:Context,card: Card){
        launch {
            if(card.image !="null"){
                val imageUri: Uri = Uri.parse(card.image)
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri)

                card.image = ImageResolver.getRandomString(10)
                ImageResolver.saveImageToInternalStorage(card.image!!,bitmap,context)
                uiState.value = AddCardUIState.CardChanged
            }





                var cards = repo.getDeckById(deckId!!).cards
                cards.add(card)
                repo.insertCard(cards,deckId!!)


        }
    }

    fun onCardFrontChange(value:String){
        if(value.isEmpty()){
            data.frontError = "Front cant be empty"
        }else{
            data.frontError =""
        }
        data.editedCard.front = value
        uiState.value = AddCardUIState.CardChanged
    }

    fun onCardBackChange(value:String){
        if(value.isEmpty()){
            data.backError = "Back cant be emepty"
        }else{
            data.backError = ""
        }
        data.editedCard.back = value
        uiState.value = AddCardUIState.CardChanged
    }





}