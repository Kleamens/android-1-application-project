package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.EditDeck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.navigation.NavBarDestinations
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BackArrowScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.MyTextField
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.SquareButton
import org.koin.androidx.compose.getViewModel

@Composable
fun EditDeckScreen(id:Long?,
                   navController: NavController,
                   viewModel: EditDeckViewModel = getViewModel()) {

    viewModel.deckId = id

    var data by remember {
        mutableStateOf(viewModel.data)
    }

    viewModel.uiState.value.let {
        when(it){
            EditDeckUIStates.DeckChanged -> {
                viewModel.uiState.value = EditDeckUIStates.Default
            }
            EditDeckUIStates.DeckSaved -> {
                navController.popBackStack()
            }
            EditDeckUIStates.Default -> {

            }
            EditDeckUIStates.Loading -> {
                viewModel.getDeck()
            }
            is EditDeckUIStates.Success -> {
                data.editedDeck = it.deck
                viewModel.uiState.value = EditDeckUIStates.Default
            }
        }
    }
    BackArrowScreen(appBarTitle = { Text(text = stringResource(R.string.EditDeckEditDeck)) },
        onBackClick = {navController.popBackStack() }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
             modifier = Modifier.fillMaxSize()) {
            MyTextField(value = data.editedDeck.name, labelText = stringResource(R.string.EditDeckName), onValueChange = {
                viewModel.onNameChange(it)})

            MyTextField(value = data.editedDeck.description,
                labelText = stringResource(R.string.EditDeckDescription),
                onValueChange = {
                    viewModel.onDescChange(it)})

            SquareButton(text = stringResource(R.string.EditDeckSave), enabled = !data.editedDeck.name.isEmpty()) {
                viewModel.save()
                navController.popBackStack()
            }
        }

    }
}