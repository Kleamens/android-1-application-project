package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun DeckCard(
    deckName:String,
    cardAmount:Int,
    deckDescription:String,
    date:String="",
    remainingAmount:Int,
    onCardClick:()->Unit,
    onDeleteClick:()->Unit,
    onAddCardClick:()->Unit,
    onChangeDeckClick:()->Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    var formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy hh:mm")



    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onCardClick() },
        backgroundColor = Color.White
    )
    {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {

                Text(text = deckName, fontWeight = FontWeight.Bold, fontSize = 25.sp)


                Text(text = cardAmount.toString() +" "+stringResource(R.string.DeckCardsCards), fontSize = 16.sp, color = cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray3)
                Text(text = remainingAmount.toString()+" "+ stringResource(R.string.DeckCardRemaining), fontSize = 16.sp, color = cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray3)
                Spacer(modifier = Modifier.padding(2.dp))


                Text(text = deckDescription, fontSize = 20.sp)



                }
            if(!date.isEmpty()) {
                Text(text = LocalDateTime.parse(date).format(formatter))
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.8f))
            IconButton(onClick = { expanded=!expanded}) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
            }
            DropdownMenu(

                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                offset = DpOffset(x = (-60).dp, y = (-40).dp)
            ) {
                DropdownMenuItem(onClick = {onDeleteClick() }) {
                    Text(text = stringResource(R.string.DeckCardDelete))
                }
                DropdownMenuItem(onClick = { onAddCardClick() }) {
                    Text(text = stringResource(R.string.DeckCardAddCard))
                }
                DropdownMenuItem(onClick = { onChangeDeckClick() }) {
                    Text(text = stringResource(R.string.AddCardChangeDeck))
                }
                // adding items

            }


            }



    }
}






