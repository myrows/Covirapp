package com.example.covirapp.ui.users

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.covirapp.R
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.ui.provinceStats.ProvinceStatsActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_province_stats.*
import kotlinx.android.synthetic.main.activity_region_detail.*

class RegionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_detail)

        var nameRegion : String = intent.extras?.get("name").toString()
        var confirmed : String = intent.extras?.get("confirmed").toString()
        var recovered : String = intent.extras?.get("recovered").toString()
        var deaths : String = intent.extras?.get("deaths").toString()

        var mBarChart = findViewById<PieChart>(R.id.pieChartRegion)
        var txtP3 : TextView = textViewP3Detail
        var txtP4 : TextView = textViewP4Detail
        var txtP5 : TextView = textViewP5Detail
        var photoProvince : ImageView = imageViewRegion

        var data : ArrayList<PieEntry> = arrayListOf()

        setTitle(nameRegion)

        txtP3.text = confirmed
        txtP4.text = recovered
        txtP5.text = deaths

        data.add( PieEntry(confirmed.toFloat()) )
        data.add( PieEntry(recovered.toFloat()) )
        data.add( PieEntry(deaths.toFloat()) )

        var barDataSet : PieDataSet = PieDataSet(data, "Infectados - Recuperados - Muertes")
        var colors : MutableList<Int> = mutableListOf( Color.rgb(247, 160, 20) , Color.rgb(54, 165, 125), Color.rgb(196, 27, 27) )
        barDataSet.colors = colors

        var barData : PieData = PieData(barDataSet)


        mBarChart.visibility = View.VISIBLE
        mBarChart.animateY(1400, Easing.EaseInOutQuad);
        mBarChart.animateY(5000)
        mBarChart.data = barData
        var description : Description = Description()
        description.text = "Personas por Covid-19"
        mBarChart.description = description
        mBarChart.invalidate()
    }
}
