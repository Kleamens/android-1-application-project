package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.addDeck

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BottomNavigationWindow
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.MyTextField
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.SquareButton
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.DataStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddDeckScreen(
    navController: NavController ,
    viewModel: AddDeckViewModel = getViewModel()
){
    var data by remember {
        mutableStateOf(viewModel.data)
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val toast  = Toast.makeText(context, stringResource(R.string.deckAdded),Toast.LENGTH_SHORT)
    val dataStore = DataStore(context)
    //can be done without data var but if you want to have actions and not pass viewModel i guess you need it T_T

    viewModel.uiState.value.let {
        when(it){
            AddDeckUIState.Default -> {

            }
            AddDeckUIState.FieldChanged -> {
                if(viewModel.data.first){
                    val savedName  = dataStore.getDeckName.collectAsState(initial = "")
                    val savedDesc  = dataStore.getDeckDesc.collectAsState(initial = "")
                    if(savedName.value != ""){
                        viewModel.data.createdDeck.name = savedName.value!!
                    }
                    if(savedDesc.value != ""){
                        viewModel.data.createdDeck.description = savedDesc.value!!
                    }

                }
                viewModel.data.first = false
                data = viewModel.data





                viewModel.uiState.value = AddDeckUIState.Default
            }

        }
    }


    BottomNavigationWindow(navController =navController, beforeNavigation = {
        if(viewModel.data.createdDeck.name != ""){
            scope.launch {
                dataStore.saveName(viewModel.data.createdDeck.name)
            }
        }
        if(viewModel.data.createdDeck.description != ""){
            scope.launch {
                dataStore.saveDesc(viewModel.data.createdDeck.description)
            }

        }
    }) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                Text(text = stringResource(R.string.addDeck), modifier = Modifier.padding(20.dp),fontSize = 30.sp, fontWeight = FontWeight.Bold)

            }
            MyTextField(value = data.createdDeck.name, labelText = stringResource(R.string.AddDeckName)) {
                viewModel.onNameChange(it)
            }
            MyTextField(value = data.createdDeck.description, labelText = stringResource(R.string.AddDeckDescription)) {
                viewModel.onDescriptionChange(it)
            }


            SquareButton(text = stringResource(R.string.AddDeckAddDeck), enabled = data.nameError.isEmpty()) {
                viewModel.createDeck(name = data.createdDeck.name, description = data.createdDeck.description)
                toast.show()
            }
            var value = remember {
                mutableStateOf(true)
            }



        }

    }
}

//@Composable
//fun AddDeckScreenContent(paddingValues: PaddingValues) {
//    Text(text = "Add Screen")
//}