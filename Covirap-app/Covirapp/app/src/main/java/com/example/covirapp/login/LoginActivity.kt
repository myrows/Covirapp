package com.example.covirapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ebanx.swipebtn.OnStateChangeListener
import com.ebanx.swipebtn.SwipeButton
import com.example.covirapp.MainActivity
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.di.MyApplication
import com.example.covirapp.response.LoginResponse
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
                                    Toast.makeText(MyApplication.instance, "Ha iniciado sesión con éxito", Toast.LENGTH_LONG).show()

                                    SharedPreferencesManager.SharedPreferencesManager.setSomeStringValue("tokenId", response.body()!!.access_token);
                                } else {
                                    Toast.makeText(MyApplication.instance, "Email y/o contraseña incorrecta", Toast.LENGTH_LONG).show()
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
