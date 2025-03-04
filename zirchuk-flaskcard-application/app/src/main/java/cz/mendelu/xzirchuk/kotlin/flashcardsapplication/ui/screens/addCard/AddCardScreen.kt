package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addCard

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BackArrowScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.MyTextField
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.SquareButton
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddCardScreen(id:Long?,
                  navController: NavController,
                  viewModel: AddCardViewModel = getViewModel()){
    //Id of the deck edited
    viewModel.deckId = id
    val context = LocalContext.current
    var data = remember {
        mutableStateOf(viewModel.data)
    }



    val toast  = Toast.makeText(context, stringResource(R.string.cardAdded), Toast.LENGTH_SHORT)
    //Launcher for picking image from gallery
    var launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent())
    {
        viewModel.data.editedCard.image = it.toString()
        viewModel.data.imageInserted = it
        viewModel.uiState.value = AddCardUIState.CardChanged
    }

    viewModel.uiState.value.let {
        when(it){
            AddCardUIState.CardChanged -> {
                data.value = viewModel.data
                viewModel.uiState.value = AddCardUIState.Default
            }
            AddCardUIState.CardSaved -> {

                navController.popBackStack()
            }
            AddCardUIState.Default ->{}
            AddCardUIState.Loading -> {
                viewModel.initCard()
            }
        }
    }
    BackArrowScreen(
        appBarTitle = {
                      Text(text = data.value.deckEdited.name)
        }
         ,
        onBackClick = { navController.popBackStack() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {


            MyTextField(value = data.value.editedCard.front,
                labelText = stringResource(R.string.AddCardFieldFront),
                onValueChange = {
                    viewModel.onCardFrontChange(it)
                })
            MyTextField(value = data.value.editedCard.back,
                labelText = stringResource(R.string.AddCardFieldBack),
                onValueChange = {
                    viewModel.onCardBackChange(it)
                })



            SquareButton(
                text = stringResource(R.string.AddCardButtonAddCard),
                enabled = data.value.frontError.isEmpty() && data.value.backError.isEmpty()
            )
            {
                viewModel.addCardToDeck(card = data.value.editedCard, context = context)
                toast.show()
            }

            Button(onClick = {
                launcher.launch("image/*")
            }) { Text(text = stringResource(R.string.AddCardAddImage)) }
            if (data.value.imageInserted != null) {

                Image(
                    rememberAsyncImagePainter(Uri.parse(data.value.editedCard.image)),
                    contentDescription = "",

                    )
            }
        }
    }
}

@Composable
fun AddCardScreenContent() {

}