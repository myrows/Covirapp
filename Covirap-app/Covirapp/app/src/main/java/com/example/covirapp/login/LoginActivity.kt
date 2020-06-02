package com.example.covirapp.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ebanx.swipebtn.OnStateChangeListener
import com.ebanx.swipebtn.SwipeButton
import com.example.covirapp.MainActivity
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.Ubicacion
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.response.LoginResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var ubication : FusedLocationProviderClient
    lateinit var database : FirebaseDatabase
    lateinit var refUbication : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        database = FirebaseDatabase.getInstance()
        refUbication = database.getReference("ubicacion")

        var edtEmail: EditText = editTextEmail
        var edtPassword: EditText? = editTextPassword
        var token: String? = null
        val swipeButton: SwipeButton? = swipe_btn
        var txtRegister: TextView? = textViewRegistrarse
        var service: CovirappService
        var serviceGenerator : ServiceGenerator = ServiceGenerator()

        txtRegister?.setOnClickListener {
            var intent : Intent = Intent( this@LoginActivity, RegisterActivity::class.java )
            startActivity(intent)
        }

        swipeButton?.setOnStateChangeListener(OnStateChangeListener {
                active: Boolean ->

            if ( active ) {
                if (!edtEmail.text.toString().isEmpty() && !edtPassword?.text.toString().isEmpty()){
                    if(token == null){

                        service = serviceGenerator.createServiceLogin(CovirappService::class.java)

                        var call : Call<LoginResponse> = service.login("password", edtEmail.text.toString(), edtPassword?.text.toString())
                        call.enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if ( response.isSuccessful ) {
                                    var intent : Intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)

                                    gpsPermissions()
                                    SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("tokenId", response.body()!!.access_token);

                                    service = serviceGenerator.createServiceUser(CovirappService::class.java)
                                    var callId : Call<UsersResponseItem> = service.getUserDataAuthenticated()
                                    callId.enqueue(object : Callback<UsersResponseItem> {
                                        override fun onResponse(call: Call<UsersResponseItem>, response: Response<UsersResponseItem>
                                        ) {
                                            SharedPreferencesManager.SharedPreferencesManager.setSomeIntValue("userId", response.body()!!.id)
                                            SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("userStatus", response.body()!!.status)
                                        }
                                        override fun onFailure(
                                            call: Call<UsersResponseItem>,
                                            t: Throwable
                                        ) {
                                            Toast.makeText(MyApplication.instance, "Error de conexión", Toast.LENGTH_LONG).show()
                                        }
                                    })
                                } else {
                                    Toast.makeText(MyApplication.instance, "Email y/o contraseña incorrecta", Toast.LENGTH_LONG).show()
                                }
                            }

                            private fun gpsPermissions() {
                                if (ContextCompat.checkSelfPermission(this@LoginActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    Log.d("Permisos", "Permiso GPS ubicación aceptado")
                                } else {
                                    ActivityCompat.requestPermissions(
                                        this@LoginActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 1)
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(MyApplication.instance, "Error de conexión", Toast.LENGTH_LONG)
                            }
                        })
                    }else{
                        var goMain : Intent = Intent(this, MainActivity::class.java);
                        startActivity(goMain);
                    }

                }else{
                    Toast.makeText(this, "Uno de los campos está sin rellenar", Toast.LENGTH_SHORT).show();
                }
            }else{

            }
        })
    }
}
