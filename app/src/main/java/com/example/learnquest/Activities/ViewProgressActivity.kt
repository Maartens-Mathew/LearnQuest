package com.example.learnquest.Activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.learnquest.R
import com.google.android.material.transition.MaterialSharedAxis.Axis
import com.patrykandpatrick.vico.core.cartesian.CartesianChart
import com.patrykandpatrick.vico.core.cartesian.axis.AxisPosition
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.shader.ColorShader
import com.patrykandpatrick.vico.views.cartesian.CartesianChartView
import kotlinx.coroutines.launch
import kotlin.random.Random

class ViewProgressActivity : AppCompatActivity() {
//    Notes: Should marksObtained be a DoubleArray with size equal to idealMarks where values are entered
//    when assessment is reached or marksObtained is dynamic list which increases with each new assessment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_progress)
        Log.i("ProgressActivity","Progress has started")
        //to get access to Chart view
        try{
            val cartesianChartView: CartesianChartView = findViewById<CartesianChartView>(R.id.cartesianChartView)
            //Making use of dummy data
            //TODO: get idealMarks and MarksObtained from Database
            //TODO: make use of Threads
            val idealMarks = DoubleArray(5)
            val marksObtained = DoubleArray(5)
            val weights = DoubleArray(5)
            val indices = IntArray(5)
            fun getValue(d: Double) : Double{
                var result : Double
                do{
                    result = Random.nextDouble(d+1)
                } while (result > d)
                return result
            }
            var totalWeight = 1.0
            for (i in idealMarks.indices){
                indices[i] = i
                idealMarks[i] = getValue(1.0)
                weights[i] = getValue(totalWeight)
                totalWeight -= weights[i]
                if ( i < marksObtained.size){
                    marksObtained[i] = getValue(1.0)
                }
                val str = String.format("${i}: IM- %.2f; MO- %.2f; W- %.2f",idealMarks[i],marksObtained[i],weights[i])
                Log.i("ProgressActivity",str)
            }
            val modelProducer : CartesianChartModelProducer = CartesianChartModelProducer()

            val lblIdeal : TextView = findViewById(R.id.lblGoalMark)
            val idealMark = calculateIdealMark(idealMarks, weights).times(100)
            var logStr = String.format("%.0f",idealMark)
            Log.i("ProgressActivity",logStr)
            var str = String.format("%.0f",idealMark)
            lblIdeal.text = str + "%";

            val currProg = calculateCurrentProgress(idealMarks, weights, marksObtained, 2).times(100)
            str = String.format("%.0f",currProg)
            Log.i("ProgressActivity",str)
            val lblProgress : TextView = findViewById(R.id.lblCurrProgress)
            lblProgress.text = str+"%"

            val series2 = DoubleArray(5)
            series2[0] = marksObtained[0]
            series2[1] = marksObtained[1]
            for (i in 3..<idealMarks.size){
                series2[i] = idealMarks[i]
            }

            val colorShader = ColorShader(Color.BLUE)
            val line = LineCartesianLayer.Line(colorShader)
            val line2 = LineCartesianLayer.Line(ColorShader(Color.RED))
            val lineChart = LineCartesianLayer(LineCartesianLayer.LineProvider.series(line,line2))
            lineChart.verticalAxisPosition = AxisPosition.Vertical.Start
            cartesianChartView.chart = CartesianChart(lineChart)
            cartesianChartView.chart!!.startAxis = VerticalAxis.start(LineComponent(Color.GRAY))
            cartesianChartView.chart!!.endAxis = VerticalAxis.end(LineComponent(Color.GRAY))
            cartesianChartView.chart!!.bottomAxis = HorizontalAxis.bottom(LineComponent(Color.GRAY))
            //never recreate charts CartesianChartModelProducer. Will cause errors
            cartesianChartView.modelProducer = modelProducer
            lifecycleScope.launch { modelProducer.runTransaction {
                lineSeries {
                series(indices.toList(),idealMarks.toList())
                series(indices.toList(),series2.toList())
            }
            } }
        }
        catch (e : Exception){
            for ( element in e.stackTrace){
                Log.e("ProgressActivity",element.toString())
            }
        }
    }

    fun calculateIdealMark(idealMarks: DoubleArray, weights: DoubleArray) : Double{
        var totalIdealMark : Double = 0.0
        for (i in 0..<idealMarks.size){
            totalIdealMark += idealMarks[i]*weights[i]
        }
        return totalIdealMark
    }

    fun calculateCurrentProgress(idealMarks: DoubleArray, weights: DoubleArray, marksObtained: DoubleArray, n: Int):Double{
        var currProgress : Double = 0.0
        for (i in 0..n){
            currProgress += marksObtained[i]*weights[i]
        }
        for (i in n+1..<idealMarks.size){
            currProgress += idealMarks[i]*weights[i]
        }
        return currProgress
    }
}