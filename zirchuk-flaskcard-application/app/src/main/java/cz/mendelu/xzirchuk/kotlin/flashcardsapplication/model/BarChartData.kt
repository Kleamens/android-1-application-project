package cz.mendelu.xzirchuk.kotlin.flashcardsapplication.model

data class BarChartData(
    var barName:String,
    var varValue:Float?
)

var getBarChartData = listOf<BarChartData>(
    BarChartData("One",2f),
    BarChartData("Two",4f)
)