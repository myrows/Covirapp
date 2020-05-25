package com.example.covirapp.ui.provinceStats

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.covirapp.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.Resource
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.viewmodel.CovirappCountryViewModel
import com.example.covirapp.viewmodel.CovirappViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.activity_province_stats.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ProvinceStatsActivity : AppCompatActivity() {
    @Inject lateinit var countryViewModel: CovirappViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_province_stats)

        (applicationContext as MyApplication).appComponent.inject(this)

        var mBarChart = findViewById<PieChart>(R.id.pieChartProvince)
        var txtP3 : TextView = textViewP3
        var txtP4 : TextView = textViewP4
        var txtP5 : TextView = textViewP5
        var photoProvince : ImageView = imageViewProvince
        var provinceActivity : ProvinceStatsActivity

        var personas : ArrayList<PieEntry> = arrayListOf()
        var users : MutableList<UsersResponseItem> = mutableListOf()
        var numInfected : Int = 0
        var numHealthy : Int = 0
        var numRecovered : Int = 0


        countryViewModel.usersProvince.observe(this, Observer { response ->
            when ( response ) {
                is Resource.Success -> {
                    Log.d("SizeResource", "${response.data?.size}")
                    response.data?.forEach {
                        users.add(it)
                    }

                    setTitle(users.get(0).province)

                    photoProvince.load("https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Escudo_de_la_ciudad_de_Ja%C3%A9n.svg/1200px-Escudo_de_la_ciudad_de_Ja%C3%A9n.svg.png")

                    Log.d("userSize", "${users.size}")

                    for ( u in users ) {

                        if ( u.status == "INFECTADO" ) {
                            numInfected++
                        }

                        if ( u.status == "SALUDABLE" ) {
                            numHealthy++
                        }

                        if ( u.status == "RECUPERADO" ) {
                            numHealthy++
                        }
                    }

                    Log.d("Healthy", "$numHealthy")
                    Log.d("HealthyF", "${numHealthy.toFloat()}")

                    // Infected
                    personas.add( PieEntry(numInfected.toFloat()) )
                    txtP3.text = numInfected.toString()
                    //Healthy
                    personas.add( PieEntry(numHealthy.toFloat()) )
                    txtP4.text = numHealthy.toString()

                    //Recovered
                    personas.add( PieEntry(numRecovered.toFloat()) )
                    txtP5.text = numRecovered.toString()

                    var barDataSet : PieDataSet = PieDataSet(personas, "Infectados - Saludable - Recuperados")
                    var colors : MutableList<Int> = mutableListOf( Color.rgb(222, 64, 47), Color.rgb(96, 222, 47), Color.rgb(9, 146, 214) )
                    barDataSet.colors = colors

                    var barData : PieData = PieData(barDataSet)


                    mBarChart.visibility = View.VISIBLE
                    mBarChart.animateY(5000)
                    mBarChart.data = barData
                    var description : Description = Description()
                    description.text = "Personas por Covid-19"
                    mBarChart.description = description
                    mBarChart.invalidate()
                }
            }
        })
    }
}
