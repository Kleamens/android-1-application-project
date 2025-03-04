package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable

fun SquareButton(
    text:String,
    enabled:Boolean = true,
    onClick:()->Unit
) {
    Button(enabled=enabled, shape = RoundedCornerShape(10),
        onClick = { onClick() }, modifier = Modifier.padding(8.dp)) {
        Text(text = text, fontSize = 18.sp)
    }
}