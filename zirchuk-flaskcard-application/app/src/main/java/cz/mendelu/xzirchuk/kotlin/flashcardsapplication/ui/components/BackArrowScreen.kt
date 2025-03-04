package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray2
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.gray3


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackArrowScreen(
    appBarTitle:@Composable ()-> Unit,
    onBackClick: () -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                appBarTitle()
            },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription ="")
                    }

                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = gray3)
            )
        }
    ) {
        Column(modifier = Modifier.padding(it), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            content(it)
        }

    }


}
