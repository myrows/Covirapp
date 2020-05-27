package com.example.covirapp.ui.account

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.*
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.models.QuizDto
import com.example.covirapp.models.QuizResponse
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TestActivity : AppCompatActivity() {

    lateinit var tYears : EditText
    lateinit var sFever : Switch
    lateinit var sRespiratoryPain : Switch
    lateinit var sCough : Switch
    lateinit var sNeckPain : Switch
    lateinit var sTasteLost : Switch
    lateinit var sSmellLost : Switch
    lateinit var sContactWithInfected: Switch
    lateinit var sRiskPerson : Switch
    lateinit var buttonTest : Button

    var isFever : Boolean = false
    var isRespiratoryPain: Boolean = false
    var isCough : Boolean = false
    var isNeckPain : Boolean = false
    var isTasteLost : Boolean = false
    var isSmellLost: Boolean = false
    var isContactWithInfected : Boolean = false
    var isRiskPerson : Boolean = false
    lateinit var textFever : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        setTitle("Test Covid-19")

        tYears = findViewById(R.id.txtYears)
        sFever = findViewById(R.id.switchFever)
        sRespiratoryPain = findViewById(R.id.switchRespiratoryPain)
        sCough = findViewById(R.id.switchCough)
        sNeckPain = findViewById(R.id.switchNeckPain)
        sTasteLost = findViewById(R.id.switchTasteLost)
        sSmellLost = findViewById(R.id.switchSmellLost)
        sContactWithInfected = findViewById(R.id.switchContactWithInfected)
        sRiskPerson = findViewById(R.id.switchRiskPerson)
        buttonTest = findViewById(R.id.buttonMyAccountSaveTest)
        textFever = textView13


        sFever.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isFever = true
        }

        sRespiratoryPain.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isRespiratoryPain = true
        }

        sCough.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isCough = true
        }

        sNeckPain.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isNeckPain = true
        }

        sTasteLost.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isTasteLost = true
        }

        sSmellLost.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isSmellLost = true
        }

        sContactWithInfected.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isContactWithInfected = true
        }

        sRiskPerson.setOnCheckedChangeListener { buttonView, isChecked ->
            if ( isChecked ) isRiskPerson = true
        }

        buttonTest.setOnClickListener {

            // Se guarda el Test

            var test : QuizDto = QuizDto( years = txtYears.text.toString().toInt(), cough = isCough, neckPain = isNeckPain, respiratoryPain = isRespiratoryPain, tasteLost = isTasteLost,
                smellLost = isSmellLost, fever = isFever, riskPerson = isRiskPerson, contactWithInfected = isContactWithInfected )

            var generator : ServiceGenerator = ServiceGenerator()
            var service : CovirappService

            service = generator.createServiceUser(CovirappService::class.java)

            var call : Call<QuizResponse> = service.createQuiz( test )
            call.enqueue( object : Callback<QuizResponse> {
                override fun onResponse(
                    call: Call<QuizResponse>,
                    response: Response<QuizResponse>
                ) {
                    if ( response.isSuccessful )
                    Toast.makeText(this@TestActivity, "Gracias por realizar el test! 👏🏻" , Toast.LENGTH_LONG).show()

                    var callTestUpdate : Call<ResponseBody> = service.statusTest()
                    callTestUpdate.enqueue( object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if ( response.isSuccessful ) {
                                Toast.makeText(this@TestActivity, "Status actualizado" , Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@TestActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                        }
                    })
                }

                override fun onFailure(call: Call<QuizResponse>, t: Throwable) {
                    Toast.makeText(this@TestActivity, "Error de conexión", Toast.LENGTH_LONG).show()
                }
            })



        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
}
