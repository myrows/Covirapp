package com.example.covirapp.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.Constantes
import com.example.covirapp.models.UserDto
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.ui.provinceStats.ProvinceStatsActivity
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.activity_status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)

        setTitle("Mi perfil")

        perfilAccountSuccess.visibility = View.INVISIBLE

        var edUsername : EditText = findViewById(R.id.editTextMyAccountUsername)
        var edFullName : EditText = findViewById(R.id.editTextMyAccountFullName)
        var edPassword : EditText = findViewById(R.id.editTextMyAccountPassword)
        var imageUser : ImageView = findViewById(R.id.imageViewUserAccount)
        var regionStats : CardView = findViewById(R.id.cardViewAccount1)
        var test : CardView = findViewById(R.id.cardViewAccount2)
        var updateStatus : CardView = findViewById(R.id.cardViewAccount3)
        var saveUser : Button = findViewById(R.id.buttonMyAccountSave)
        var userPassword : String = ""

        var serviceGenerator : ServiceGenerator = ServiceGenerator()
        var service : CovirappService

        service = serviceGenerator.createServiceUser(CovirappService::class.java)

        var call : Call<UsersResponseItem> = service.getUserDataAuthenticated()

        var userId : Long = 0
        var status : String? = null

        call.enqueue( object : Callback<UsersResponseItem> {
            override fun onResponse(
                call: Call<UsersResponseItem>,
                response: Response<UsersResponseItem>
            ) {
                if ( response.isSuccessful ) {

                    imageUser.load("${ Constantes.API_BASE_URL}/covirapp/files/${response.body()?.avatar}" ) {
                        transformations(CircleCropTransformation())
                    }
                    userId = response.body()!!.id.toLong()
                    status = response.body()!!.status
                    Log.d("UserId", "$userId")
                    edUsername.text = response.body()?.username?.toEditable()
                    edFullName.text = response.body()?.fullName?.toEditable()
                    userPassword = response.body()?.password!!
                    //edPassword.inputType = InputType.TYPE_CLASS_TEXT
                }
            }

            override fun onFailure(call: Call<UsersResponseItem>, t: Throwable) {
                Toast.makeText(this@MyAccountActivity, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })

        saveUser.setOnClickListener {

            if( status == null ) {
                status = "SALUDABLE"
            }

            if ( edPassword.text.isEmpty() ) {
                edPassword.text = userPassword.toEditable()
            }

            var userDto : UserDto = UserDto( username = edUsername.text.toString(), fullName = edFullName.text.toString(),
            province = "", status = status!!, password = edPassword.text.toString(), password2 = edPassword.text.toString())

            Log.d("UserIdSave", "$userId")
            var call : Call<ResponseBody> = service.editUser( userId, userDto )

            call.enqueue( object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    perfilAccountSuccess.visibility = View.VISIBLE
                    perfilAccountSuccess.playAnimation()

                    GlobalScope.launch(context = Dispatchers.Main) {
                        delay(2000)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MyAccountActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                }
            })

        }

        regionStats.setOnClickListener {
            var goProvinceActivity : Intent = Intent( this, ProvinceStatsActivity::class.java )
            startActivity( goProvinceActivity )
        }

        test.setOnClickListener {
            var goTestActivity : Intent = Intent( this, TestActivity::class.java )
            startActivity( goTestActivity )
        }

        updateStatus.setOnClickListener {
            var goStatusActivity : Intent = Intent( this, StatusActivity::class.java )
            goStatusActivity.putExtra("nameUser", edFullName.text.toString())
            startActivity( goStatusActivity )
        }

    }

    fun String.toEditable() : Editable = Editable.Factory.getInstance().newEditable(this)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.account_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.captureImageItem -> {
                var goCapture = Intent( this@MyAccountActivity, DetectObjectActivity::class.java )
                startActivity(goCapture )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
