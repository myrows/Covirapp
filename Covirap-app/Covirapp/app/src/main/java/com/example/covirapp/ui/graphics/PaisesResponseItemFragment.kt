package com.example.covirapp.ui.graphics

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.covirapp.R
import com.example.covirapp.common.Resource
import com.example.covirapp.di.MyApplication
import com.example.covirapp.viewmodel.CovirappCountryViewModel
import com.example.covirapp.viewmodel.CovirappViewModel
import javax.inject.Inject

class PaisesResponseItemFragment : Fragment() {
    @Inject lateinit var countryViewModel: CovirappCountryViewModel
    @Inject lateinit var sharedPref : SharedPreferences

    private lateinit var countryAdapter: MyPaisesResponseItemRecyclerViewAdapter
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApplication).appComponent.inject(this)
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
        countryViewModel.countries.observe(viewLifecycleOwner, Observer { response ->
            when( response ) {
                is Resource.Success -> {
                    response.data.let {
                        countryAdapter.setData( it )
                        recyclerView.scheduleLayoutAnimation()
                    }
                }
                is Resource.Error -> {
                    response.message.let {
                        Toast.makeText(MyApplication.instance, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        })
        return view
    }
}
