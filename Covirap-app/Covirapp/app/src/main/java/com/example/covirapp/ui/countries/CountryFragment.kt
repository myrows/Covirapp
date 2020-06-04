package com.example.covirapp.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.covirapp.R
import com.example.covirapp.common.Resource
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.CountryResponseItem
import com.example.covirapp.viewmodel.CountryViewModel
import javax.inject.Inject

class CountryFragment : Fragment() {
    @Inject lateinit var countryViewModel : CountryViewModel
    private lateinit var countryAdapter : MyCountryRecyclerViewAdapter
    private var columnCount = 2
    lateinit var listaCountries : MutableList<CountryResponseItem>

    lateinit var searchView : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_country_list, container, false)

        countryAdapter = MyCountryRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.countryWithStatus)

        // Set the adapter
        with(recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = countryAdapter
        }
        countryViewModel.countryApi.observe(viewLifecycleOwner, Observer { response ->
            when( response ) {
                is Resource.Success -> {
                    response.data.let {
                        countryAdapter.setData( it!! )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.country_search_menu, menu)

        var searchItem : MenuItem = menu.findItem(R.id.searchCountry)
        var searchView : SearchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                countryAdapter.filter.filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
