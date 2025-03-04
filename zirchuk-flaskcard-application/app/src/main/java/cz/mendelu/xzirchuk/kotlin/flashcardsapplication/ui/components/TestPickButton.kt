package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray5

@Composable
fun TestPickButton(text:String,icon:@Composable ()->Unit,onClick:()->Unit) {
    OutlinedButton(modifier = Modifier.padding(10.dp),shape = RoundedCornerShape(5.dp), border = BorderStroke(2.dp, gray5),onClick = { onClick() }) {

        Text(text = text)
        icon()
    }
}