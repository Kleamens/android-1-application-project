package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFillGap

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BackArrowScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.MyTextField
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.SquareButton
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.TestPickButton
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.ImageResolver
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards.TestFlashcardsUIState
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime

@Composable
fun TestFillGapScreen(id:Long,navController: NavController,viewModel: TestFillGapViewModel = getViewModel()) {

    viewModel.deckId = id

    var finished by remember {
        mutableStateOf(false)
    }
    var deck by remember {
        mutableStateOf(viewModel.data.deck)
    }
    var context = LocalContext.current

    var currentCard: Card by remember {
        mutableStateOf(Card("", "", "", LocalDateTime.MIN.toString(), complete = false))
    }
    var inputAnswer by remember {
        mutableStateOf(viewModel.data.answer)
    }

    var showAnswer by remember {
        mutableStateOf(false)
    }
    var uncompCards by remember {
        mutableStateOf(viewModel.data.areThereDecksToRevise)
    }



    viewModel.uiState.value.let {
        when (it) {
            TestFillGapUIState.Default -> {

            }
            TestFillGapUIState.Loading -> {
                viewModel.getDeck(id)

            }
            is TestFillGapUIState.Success -> {
                viewModel.data.deck = it.deck
                currentCard = viewModel.data.deck.cards.first()

                deck = viewModel.data.deck
                viewModel.uiState.value = TestFillGapUIState.Default
            }
            TestFillGapUIState.CurrentCardChanged -> {
                deck = viewModel.data.deck
                uncompCards = viewModel.data.areThereDecksToRevise
                if (!finished) {
                    currentCard = viewModel.data.deck.cards.first()
                }
                viewModel.uiState.value = TestFillGapUIState.Default
            }
            TestFillGapUIState.AnswerFieldChange -> {
                inputAnswer = viewModel.data.answer
                viewModel.uiState.value = TestFillGapUIState.Default
            }
            TestFillGapUIState.ListEmpty -> {

            }
        }
    }
    BackArrowScreen(
        appBarTitle = { Text(text = viewModel.data.deck.name) },
        onBackClick = { navController.popBackStack() }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!viewModel.uiState.value.equals(TestFillGapUIState.ListEmpty)) {

                if (!finished) {
                    if (!showAnswer) {
                        Text(text = currentCard.front)
                        MyTextField(value = inputAnswer, labelText = stringResource(R.string.TestFillGapAnswer), onValueChange ={
                            viewModel.onAnswerChange(it)
                        } )





                        SquareButton(text = stringResource(R.string.TestFillGapCheckAnswer)) {
                            showAnswer = true;viewModel.checkAnswer(inputAnswer,currentCard)
                        }

                        Image(
                            painter =
                            rememberAsyncImagePainter(
                                model = ImageResolver.getUriFromFilename(
                                    context,
                                    currentCard.image ?: ""
                                )
                            ),
                            contentDescription = ""
                        )


                    } else {

                        Text(text = viewModel.data.result)

                        SquareButton(text = stringResource(R.string.TestNext)) {
                            showAnswer = false

                            finished = viewModel.nextCard(viewModel.checkAnswer(inputAnswer,currentCard))
                            inputAnswer = ""
                        }

                    }
                } else {
                    Text(text = stringResource(R.string.TestComplete))
                    Row(horizontalArrangement = Arrangement.Center) {
                        TestPickButton(
                            text = stringResource(R.string.TestReload),
                            icon = { Icon(imageVector = Icons.Default.Refresh, contentDescription = "") }) {
                            viewModel.refreshDeck()
                            finished = false
                        }

                        if (uncompCards) {
                            TestPickButton(
                                text = stringResource(R.string.TestReviseUncompCards),
                                icon = {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = ""
                                    )
                                })
                            {
                                viewModel.getUncompletedCards()
                                finished = false
                                uncompCards = false
                            }
                        }
                    }


                }
            } else {
                Text(text = stringResource(R.string.TestComplete))
                TestPickButton(
                    text = stringResource(R.string.TestReload),
                    icon = { Icon(imageVector = Icons.Default.Refresh, contentDescription = "") }) {
                    viewModel.refreshDeck()
                }
            }


        }
    }
}

