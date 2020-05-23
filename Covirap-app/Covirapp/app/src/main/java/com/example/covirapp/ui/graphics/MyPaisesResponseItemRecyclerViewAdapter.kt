package com.example.covirapp.ui.graphics

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.api.load
import com.example.covirapp.R
import com.example.covirapp.models.PaisesResponseItem
import com.example.covirapp.models.UsersResponseItem
import kotlinx.android.synthetic.main.fragment_paises_response_item.view.*

class MyPaisesResponseItemRecyclerViewAdapter() : RecyclerView.Adapter<MyPaisesResponseItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var countries: List<PaisesResponseItem> = ArrayList()
    lateinit var ctx : Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PaisesResponseItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_paises_response_item, parent, false)

        ctx = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = countries[position]

        holder.cPhoto.load(item.countryInfo.flag)
        holder.cTextCountry.text = item.country

        with(holder.mView) {
            tag = item
            Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG)
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = countries.size

    fun setData( listCountries : List<PaisesResponseItem>? ) {
        countries = listCountries!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val cPhoto : ImageView = mView.flagCountry
        val cTextCountry : TextView = mView.textCountry
    }
}
