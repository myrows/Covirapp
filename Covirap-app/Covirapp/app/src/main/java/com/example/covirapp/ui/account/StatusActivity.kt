package com.example.covirapp.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.Constantes
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.models.UserDto
import com.example.covirapp.models.UsersResponseItem
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_status.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        setTitle("Mi estado")

        var testUser : TextView = textViewStatus
        var getUser = intent.extras?.get("nameUser")
        var statusSelected : String = ""
        var buttonSave : Button = buttonStatusAccount

        var serviceGenerator : ServiceGenerator = ServiceGenerator()
        var service : CovirappService

        service = serviceGenerator.createServiceUser(CovirappService::class.java)

        testUser.text = "Buenas $getUser, ¿ te han realizado los agentes sanitarios las pruebas del Covid-19 ?"

        ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerStatusAccount.adapter = arrayAdapter
        }

        spinnerStatusAccount.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                statusSelected = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        buttonSave.setOnClickListener {

            Log.d("Status", "$statusSelected")
            var userDto : UserDto = UserDto( username = "", fullName = "",
                province = "", status = statusSelected, password = "", password2 = "")

            var call : Call<UsersResponseItem> = service.updateStatus( userDto )

            call.enqueue( object : Callback<UsersResponseItem> {
                override fun onResponse(
                    call: Call<UsersResponseItem>,
                    response: Response<UsersResponseItem>
                ) {
                    if ( response.isSuccessful ) {
                        Toast.makeText(this@StatusActivity, "Has actualizado tu estado con éxito", Toast.LENGTH_LONG).show()
                        SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("status", statusSelected)
                    }
                }

                override fun onFailure(call: Call<UsersResponseItem>, t: Throwable) {
                    Toast.makeText(this@StatusActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                }
            })
        }


    }
}
