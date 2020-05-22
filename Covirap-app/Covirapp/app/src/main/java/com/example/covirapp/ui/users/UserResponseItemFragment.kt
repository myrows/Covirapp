package com.example.covirapp.ui.users

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
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.covirapp.R
import com.example.covirapp.common.Resource
import com.example.covirapp.di.MyApplication
import com.example.covirapp.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_user_response_item_list.*
import javax.inject.Inject

class UserResponseItemFragment : Fragment() {
    @Inject lateinit var userViewModel: UsersViewModel
    @Inject lateinit var sharedPref : SharedPreferences

    private lateinit var paginationProgressBar : LottieAnimationView
    private lateinit var userAdapter: MyUserResponseItemRecyclerViewAdapter
    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.applicationContext as MyApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_response_item_list, container, false)

        userAdapter = MyUserResponseItemRecyclerViewAdapter()

        // Set the adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.listRecycler)

            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = userAdapter
            }

        //Observer para usuarios
        userViewModel.users.observe(viewLifecycleOwner, Observer { response ->
            when( response ) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let {
                        userAdapter.setData( it )
                        recyclerView.scheduleLayoutAnimation()
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let {
                        Toast.makeText(MyApplication.instance, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
        return view
    }

    private fun showProgressBar() {
    }

    private fun hideProgressBar() {
    }
}
