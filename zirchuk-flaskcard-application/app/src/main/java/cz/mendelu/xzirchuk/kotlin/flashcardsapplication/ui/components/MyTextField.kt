package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    value:String,
    labelText:String,
    onValueChange:(String)->Unit
){

    OutlinedTextField(label = { Text(text = labelText, modifier = Modifier.background(Color.White.copy(alpha = 0.0f)))}
        ,maxLines = 1,
        value = value,
        onValueChange = {
       onValueChange(it)
    },
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.White), modifier = Modifier.padding(10.dp))
}