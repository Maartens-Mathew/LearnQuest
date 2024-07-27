package com.example.learnquest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.patrykandpatrick.vico.views.cartesian.CartesianChartView

class ViewProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_progress)
        //to get access to Chart view
        val cartesianChartView: CartesianChartView = findViewById<CartesianChartView>(R.id.cartesianChartView)
    }
}