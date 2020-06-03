package com.example.covirapp.ui.countries

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.covirapp.R
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.models.CountryResponse
import com.example.covirapp.models.CountryResponseItem
import com.example.covirapp.models.Region

import kotlinx.android.synthetic.main.fragment_country.view.*
import java.util.ArrayList

class MyCountryRecyclerViewAdapter(
) : RecyclerView.Adapter<MyCountryRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var countries: List<CountryResponseItem> = ArrayList()
    lateinit var ctx : Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as CountryResponseItem

            var goCountryDetails = Intent( ctx, CountryActivity::class.java )
            SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("countrySelected", item.country);
            goCountryDetails.putExtra("name", item.country)
            goCountryDetails.putExtra("flag", item.countryInfo.flag)
            goCountryDetails.putExtra("cases", item.cases )
            goCountryDetails.putExtra("deaths", item.deaths )
            goCountryDetails.putExtra("recovered", item.recovered )
            goCountryDetails.putExtra("tests", item.tests )
            ctx.startActivity( goCountryDetails )

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_country, parent, false)

        ctx = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries[position]

        if ( item.countryInfo.flag.isNotEmpty() ) {
            holder.cPhoto.load(item.countryInfo.flag) {
                transformations(CircleCropTransformation())
                allowHardware(false)
            }
        }
        holder.cTextCountry.text = item.country

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = countries.size

    fun setData( listCountries : List<CountryResponseItem>) {
        countries = listCountries
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var cPhoto : ImageView = mView.findViewById(R.id.imageViewCountryApiCovid)
        var cTextCountry : TextView = mView.findViewById(R.id.textViewCountryApi)

    }
}
