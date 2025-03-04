package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    initialSheetState:ModalBottomSheetState,
    sheetContent:@Composable ()->Unit,
    content:@Composable ()->Unit
    ) {



    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = initialSheetState,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            sheetContent()
        }
        ) {

        content()
    }
}