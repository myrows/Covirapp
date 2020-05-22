package com.example.covirapp.ui.users

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.api.load
import com.airbnb.lottie.LottieAnimationView
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.Constantes
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.response.LoginResponse

import kotlinx.android.synthetic.main.fragment_user_response_item.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MyUserResponseItemRecyclerViewAdapter(): RecyclerView.Adapter<MyUserResponseItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    private var users: List<UsersResponseItem> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as UsersResponseItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user_response_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = users[position]

        holder.uPhoto.load("${Constantes.API_BASE_URL}/covirapp/files/${item.avatar}") {
            placeholder(R.drawable.ic_add_avatar)
        }

        if ( item.status == "SALUDABLE" ) {
            holder.statusLottie.setAnimation(R.raw.ic_lottie_healthy)
        } else if ( item.status == "ASINTOMATICO" ) {
            holder.statusLottie.setAnimation(R.raw.ic_lottie_asymptomatic)
        } else {
            holder.statusLottie.setAnimation(R.raw.ic_lottie_infected)
        }

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = users.size

    fun setData( listUser : List<UsersResponseItem>? ) {
        users = listUser!!
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val uPhoto : ImageView = mView.imageUser
        val statusLottie = mView.lottieStatus
    }
}
