package com.example.covirapp.ui.countries

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.covirapp.R
import com.example.covirapp.api.GraphicCovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.models.GraphicCountryResponse
import com.example.covirapp.models.GraphicCountryResponseItem
import com.example.covirapp.ui.graphics.PaisesResponseItemFragment
import com.example.covirapp.ui.graphics.ProvinceActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_country.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var newDataR : LineData
    lateinit var newDataD : LineData
    lateinit var nameCountry : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        var chart : LineChart = findViewById(R.id.lineChartCountry)

        nameCountry = intent.extras?.get("name").toString()
        setTitle(nameCountry)

        var imageCountry : ImageView = findViewById(R.id.imageViewCountryNovel)
        textViewNovel3.text = intent.extras?.get("cases").toString()
        textViewNovel4.text = intent.extras?.get("recovered").toString()
        textViewNovel5.text = intent.extras?.get("deaths").toString()

        imageCountry.load( intent.extras?.get("flag").toString() ) {
            allowHardware(false)
        }


        var values : ArrayList<Entry> = ArrayList()
        var dataApi : MutableList<GraphicCountryResponseItem> = mutableListOf()
        var count : Int = 0

        var generate : ServiceGenerator = ServiceGenerator()
        var service : GraphicCovirappService

        service = generate.createServiceGraphic(GraphicCovirappService::class.java)

        var call : Call<GraphicCountryResponse> = service.getDataOfCountry( nameCountry )
        call.enqueue( object : Callback<GraphicCountryResponse> {
            override fun onResponse(
                call: Call<GraphicCountryResponse>,
                response: Response<GraphicCountryResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()!!.forEach {
                        dataApi.add(it)
                    }
                    infectedData(dataApi, values, chart)
                    recoveredData( dataApi, chart )
                    deathsData( dataApi, chart )

                }

            }
            override fun onFailure(call: Call<GraphicCountryResponse>, t: Throwable) {
                Toast.makeText(this@CountryActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })

        //


    }

    fun infectedData( dataApi : MutableList<GraphicCountryResponseItem>, values : ArrayList<Entry>, chart : LineChart ) {
        var count : Int = 0
        for ( i in dataApi ) {
            values.add(Entry(count.toFloat(), i.Confirmed.toFloat()))
            count++
        }

        var lineDataSet : LineDataSet = LineDataSet(values, "Infectados")
        lineDataSet.color = Color.rgb(247, 156, 45)
        lineDataSet.lineWidth = 2.5f
        lineDataSet.circleRadius = 1f
        lineDataSet.circleSize = 0f
        lineDataSet.setCircleColor(Color.rgb(247, 156, 45))

        var lineData : LineData = LineData(lineDataSet)

        chart.data = lineData
        chart.setOnChartValueSelectedListener(this@CountryActivity);
        chart.setDrawGridBackground(false)
        chart.getDescription().setEnabled(false);
        chart.setNoDataText("No hemos encontrado datos en estos momentos")
        chart.invalidate()
    }

    fun recoveredData( dataApi : MutableList<GraphicCountryResponseItem>, chart : LineChart ) {

        newDataR = chart.data

        var valuesRecovered: ArrayList<Entry> = ArrayList()

        var countR: Int = 0

        for (i in dataApi) {
            valuesRecovered.add(Entry(countR.toFloat(), i.Recovered.toFloat()))
            countR++
        }

        var lineDataSet: LineDataSet = LineDataSet(valuesRecovered, "Recuperados")
        lineDataSet.color = Color.rgb(19, 135, 98)
        lineDataSet.lineWidth = 2.5f
        lineDataSet.circleRadius = 1f
        lineDataSet.circleSize = 0f
        lineDataSet.setCircleColor(Color.rgb(19, 135, 98))

        newDataR.addDataSet(lineDataSet)
        newDataR.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()

    }

    fun deathsData( dataApi : MutableList<GraphicCountryResponseItem>, chart : LineChart ) {

        newDataD = chart.data

        var valuesOfDeaths: ArrayList<Entry> = ArrayList()

        var countD: Int = 0

        for (i in dataApi) {
            valuesOfDeaths.add(Entry(countD.toFloat(), i.Deaths.toFloat()))
            countD++
        }

        var lineDataSet: LineDataSet = LineDataSet(valuesOfDeaths, "Muertes")
        lineDataSet.color = Color.RED
        lineDataSet.lineWidth = 2.5f
        lineDataSet.circleRadius = 1f
        lineDataSet.circleSize = 0f
        lineDataSet.setCircleColor(Color.RED)

        newDataD.addDataSet(lineDataSet)
        newDataD.notifyDataChanged()
        chart.notifyDataSetChanged()
        chart.invalidate()

    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.country_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if ( item.itemId == R.id.goRegions ) {
            var goRegions : Intent = Intent( this@CountryActivity, ProvinceActivity::class.java )
            goRegions.putExtra("nameC", nameCountry)
            startActivity( goRegions )
        }

        return super.onOptionsItemSelected(item)
    }
}
