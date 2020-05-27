package com.example.covirapp.ui.countries

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import com.example.covirapp.R
import com.example.covirapp.api.GraphicCovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.models.GraphicCountryResponse
import com.example.covirapp.models.GraphicCountryResponseItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.YAxis.AxisDependency
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        var nameCountry = intent.extras?.get("name").toString()
        setTitle(nameCountry)

        var imageCountry : ImageView = findViewById(R.id.imageViewCountryNovel)
        textViewNovel3.text = intent.extras?.get("cases").toString()
        textViewNovel4.text = intent.extras?.get("recovered").toString()
        textViewNovel5.text = intent.extras?.get("deaths").toString()

        imageCountry.load( intent.extras?.get("flag").toString() ) {
            allowHardware(false)
        }

        var chart : LineChart
        var values : ArrayList<Entry> = ArrayList()
        var dataApi : MutableList<GraphicCountryResponseItem> = mutableListOf()
        chart = findViewById(R.id.lineChartCountry)
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

                    Toast.makeText(this@CountryActivity, "${dataApi.size}", Toast.LENGTH_LONG).show()

                    for ( i in dataApi ) {
                        values.add(Entry(count.toFloat(), i.Confirmed.toFloat()))
                        count++
                    }

                    var lineDataSet : LineDataSet = LineDataSet(values, "Infectados")
                    lineDataSet.color = Color.RED
                    lineDataSet.lineWidth = 1f
                    lineDataSet.circleRadius = 0f
                    lineDataSet.circleSize = 0f
                    lineDataSet.setCircleColor(Color.RED)

                    var lineData : LineData = LineData(lineDataSet)

                    chart.data = lineData
                    chart.setOnChartValueSelectedListener(this@CountryActivity);
                    chart.setDrawGridBackground(false)
                    chart.getDescription().setEnabled(false);
                    var description : Description = Description()
                    description.text = "eje X - Días eje Y - Nº Confirmados"
                    chart.setNoDataText("No hemos encontrado datos en estos momentos")
                    chart.invalidate()
                }
            }

            override fun onFailure(call: Call<GraphicCountryResponse>, t: Throwable) {
                Toast.makeText(this@CountryActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })

        //


    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "DataSet 1")
        set.lineWidth = 2.5f
        set.circleRadius = 4.5f
        set.color = Color.rgb(240, 99, 99)
        set.setCircleColor(Color.rgb(240, 99, 99))
        set.highLightColor = Color.rgb(190, 190, 190)
        set.axisDependency = AxisDependency.LEFT
        set.valueTextSize = 10f
        return set
    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }
}
