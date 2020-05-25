package com.example.covirapp.ui.graphics

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.covirapp.R
import com.example.covirapp.common.Resource
import com.example.covirapp.di.MyApplication
import com.example.covirapp.viewmodel.CountryViewModel
import com.example.covirapp.viewmodel.CovirappCountryViewModel
import com.example.covirapp.viewmodel.CovirappViewModel
import javax.inject.Inject

class PaisesResponseItemFragment : Fragment() {
    lateinit var countryViewModel: CountryViewModel
    private lateinit var countryAdapter: MyPaisesResponseItemRecyclerViewAdapter
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_paises_response_item_list, container, false)

        countryViewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        countryAdapter = MyPaisesResponseItemRecyclerViewAdapter()

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
        countryViewModel.getAllCountries().observe(viewLifecycleOwner, Observer {
            Log.d("CountryRepository","${it.size}")
            countryAdapter.setData(it)
        })
        return view
    }
}
