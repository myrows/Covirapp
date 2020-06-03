package com.example.covirapp.ui.graphics

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.covirapp.R
import com.example.covirapp.api.CovirappCountryService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.NewRegionsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PaisesResponseItemFragment : Fragment() {
    private lateinit var countryAdapter: MyPaisesResponseItemRecyclerViewAdapter
    private var columnCount = 1

    var pattern = "yyyy-MM-dd"
    var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(pattern)
    var date: String = simpleDateFormat.format(Date())

    var generator : ServiceGenerator = ServiceGenerator()
    lateinit var serviceRegions : CovirappCountryService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.setTitle("Regiones")
        serviceRegions = generator.createServiceRegions(CovirappCountryService::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_paises_response_item_list, container, false)

        countryAdapter = MyPaisesResponseItemRecyclerViewAdapter()

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerCountry)

        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = countryAdapter
        }
        //Observer para usuarios
        var call : Call<NewRegionsResponse> = serviceRegions.getRegionsOfCovid( date, activity?.intent?.extras?.get("nameC").toString() )
        call.enqueue( object : Callback<NewRegionsResponse> {
            override fun onResponse(
                call: Call<NewRegionsResponse>,
                response: Response<NewRegionsResponse>
            ) {
                if ( response.isSuccessful ) {
                    response.body().let {
                        countryAdapter.setData( it?.dates?.get(date)?.countries?.get(activity?.intent?.extras?.get("nameC").toString())!!.regions )
                        recyclerView.scheduleLayoutAnimation()
                    }
                }
            }

            override fun onFailure(call: Call<NewRegionsResponse>, t: Throwable) {
                Toast.makeText(MyApplication.instance, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
        })
        return view
    }
}
