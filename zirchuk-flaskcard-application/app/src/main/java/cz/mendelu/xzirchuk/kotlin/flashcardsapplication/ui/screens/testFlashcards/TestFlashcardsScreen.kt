package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.testFlashcards

import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Card
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BackArrowScreen
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.SquareButton
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.TestPickButton
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.shared.ImageResolver
import org.koin.androidx.compose.getViewModel
import java.time.LocalDateTime

@Composable
fun TestFlashcardsScreen(id:Long ,
                         navController: NavController,
                         viewModel:TestFlashCardsViewModel = getViewModel()) {
    viewModel.deckId = id
    var finished by remember {
        mutableStateOf(false)
    }
    var cards = remember {
        mutableListOf<Card>()
    }
    var context = LocalContext.current

    var currentCard: Card by remember {
        mutableStateOf(Card("", "", "", LocalDateTime.MIN.toString(), false))
    }

    var showAnswer by remember {
        mutableStateOf(false)
    }
    var uncompCards by remember {
        mutableStateOf(false)
    }




    viewModel.uiState.value.let {
        when (it) {
            TestFlashcardsUIState.Default -> {

            }
            TestFlashcardsUIState.Loading -> {
                viewModel.getDeck(id)

            }
            is TestFlashcardsUIState.Success -> {
                viewModel.data.deck = it.deck

                if (!viewModel.data.deck.cards.isEmpty()) {
                    currentCard = it.deck.cards.first()
                    cards = it.deck.cards
                    viewModel.uiState.value = TestFlashcardsUIState.Default
                } else {

                    viewModel.uiState.value = TestFlashcardsUIState.ListEmpty


                }


            }
            TestFlashcardsUIState.CurrentCardChanged -> {

                cards = viewModel.data.deck.cards

                if (!finished) {

                    currentCard = viewModel.data.deck.cards.first()
                }
                viewModel.uiState.value = TestFlashcardsUIState.Default


            }
            TestFlashcardsUIState.ListEmpty -> {

            }
        }
    }

    BackArrowScreen(
        appBarTitle = { Text(text = viewModel.data.deck.name) },
        onBackClick = { navController.popBackStack() }) {


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!viewModel.uiState.value.equals(TestFlashcardsUIState.ListEmpty)) {
                if (!finished) {
                    if (!(currentCard.image == "")) {
                        Image(
                            painter =
                            rememberAsyncImagePainter(
                                model = ImageResolver.getUriFromFilename(
                                    context,
                                    currentCard.image ?: ""
                                )
                            ),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }


                    Text(text = currentCard.front)


                    if (showAnswer) {
                        Text(text = currentCard.back)
                        Row {
                            SquareButton(text = stringResource(R.string.good)) {
                                finished = viewModel.onGoodClick(currentCard)
                                showAnswer = false
                            }

                            SquareButton(text = stringResource(R.string.badButtonText)) {
                                finished = viewModel.onBadClick(currentCard)
                                showAnswer = false
                                uncompCards = true
                            }

                        }
                    } else {
                        SquareButton(text = stringResource(id = R.string.TestFillGapAnswer)) {
                            showAnswer = true
                        }
                    }







                } else {
                    Text(text = stringResource(id = R.string.TestComplete))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TestPickButton(
                            text = stringResource(R.string.TestReloadAllCards),
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = ""
                                )
                            }) {
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
                Text(text = "You are have completed all your cards for today")
                TestPickButton(
                    text = "Reload",
                    icon = { Icon(imageVector = Icons.Default.Refresh, contentDescription = "") }) {
                    viewModel.refreshDeck()
                    finished = false
                }
            }

        }


    }
}

