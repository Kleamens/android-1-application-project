package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle

import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.mikephil.charting.charts.BarChart

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.R
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.BarChartData
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.Deck
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model.getBarChartData
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.components.BottomNavigationWindow
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.decksGraph.BarGraphUIStates
import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.screens.decksGraph.BarGraphViewModel

import cz.mendelu.xzirchuk.kotlin.flashcardsapplication.ui.theme.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.koin.androidx.compose.getViewModel
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarGraphScreen(navController: NavController,viewModel:BarGraphViewModel = getViewModel()) {

    var decks by remember {
        mutableStateOf(listOf<Deck>())
    }

    viewModel.uiState.value.let {
        when(it){
            BarGraphUIStates.Default -> {

            }
            BarGraphUIStates.Loading -> {
                viewModel.getDecksWithMostCards(5)
            }
            is BarGraphUIStates.Success ->{
                decks = it.decks
            }
        }
    }
    Scaffold(
        // on below line we are specifying
        // top bar as our action bar.
    ){


            BottomNavigationWindow(navController = navController) {
                Column(modifier = Modifier.padding(it)) {
                    BarChart(data = viewModel.covertDeckListtoBarChartData(decks)){
                        Column(modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                            ){
                            if(!decks.isEmpty()){
                                decks.forEachIndexed{index , it ->
                                    Row {
                                        Text(text = (index+1).toString()+it.name, )
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Text(text = it.cards.size.toString())
                                        Spacer(modifier = Modifier.height(20.dp))
                                    }
                                }
                            }else{
                                Text(text = stringResource(R.string.BarGraphNoCards))
                            }
                                
                            }
                            
                            
                        }
                    }


                }

            }
        }




@Composable
fun BarChart(data:List<BarChartData>,content:@Composable ()-> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .size(400.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start){
                    Text(text =stringResource(R.string.GraphDecksWithMost), modifier = Modifier.padding(20.dp),fontSize = 30.sp, fontWeight = FontWeight.Bold)

                }


                    AndroidView(factory = { context ->

                        BarChart(context).apply {
                            layoutParams = LinearLayout.LayoutParams(
                                // on below line we are specifying layout
                                // params as MATCH PARENT for height and width.
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                            )
                            // on below line we are setting description
                            // enables for our pie chart.

                            this.description.isEnabled = false

                            // on below line we are setting draw hole
                            // to false not to draw hole in pie chart


                            // on below line we are enabling legend.



                            // on below line we are specifying
                            // text size for our legend.
                            this.legend.isEnabled = false


                            // on below line we are specifying
                            // alignment for our legend.
                            this.legend.horizontalAlignment =
                                Legend.LegendHorizontalAlignment.CENTER

                            // on below line we are specifying entry label color as white.

                        }
                    },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(5.dp), update = {

                            updateBarChartWithData(it, data)

                        })

                }
            content()
            }

        }



fun updateBarChartWithData(chart:BarChart,data: List<BarChartData>) {

    val entries = ArrayList<BarEntry>()

    chart.xAxis.setDrawGridLines(false)
    chart.xAxis.setDrawAxisLine(false)
    chart.xAxis.valueFormatter   = IndexAxisValueFormatter(listOf())

    chart.setDrawValueAboveBar(false)
    chart.setFitBars(true)
    chart.setScaleEnabled(false)
    chart.setTouchEnabled(false)
    chart.setExtraOffsets(0f,0f,0f,0f)


    chart.axisRight.setDrawGridLines(false)
    chart.axisRight.setDrawLabels(false)
    chart.axisRight.setDrawAxisLine(false)

    chart.axisLeft.setDrawGridLines(false)
    chart.axisLeft.setDrawLabels(false)
    chart.axisLeft.setDrawGridLines(false)
    chart.axisLeft.setDrawTopYLabelEntry(false)
    chart.axisLeft.setDrawAxisLine(false)


    for (i in data.indices) {
        val item = data.get(i)
        entries.add(BarEntry(i.toFloat(),item.varValue!!))
    }

    val ds = BarDataSet(entries,"")
    ds.valueTextSize = 15f



    ds.colors = arrayListOf(
        cyan.toArgb(),
        magenta.toArgb(),
        yellow.toArgb(),
    )

    val d = BarData(ds)
    chart.data = d
    chart.invalidate()


}