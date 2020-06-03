package com.example.covirapp.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.di.MyApplication
import com.example.covirapp.models.Ubicacion
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity() {

    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE_READ = 1001
    private val PERMISSION_CODE_WRITE = 1002

    lateinit var textIniciarSesion : TextView
    lateinit var btnRegister : Button
    lateinit var edtName : EditText
    lateinit var edtFullName : EditText
    lateinit var edtPassword : EditText
    var uriSelected : Uri? = null
    lateinit var originalFileName : String
    lateinit var imgRegister : ImageView
    lateinit var file : File
    lateinit var provinceSelected : String
    lateinit var ubication : FusedLocationProviderClient
    lateinit var database : FirebaseDatabase
    lateinit var refUbication : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        database = FirebaseDatabase.getInstance()
        refUbication = database.getReference("ubicacion")

        var pattern = "HH:mm:ss.SSS"
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat(pattern)
        var date: String = simpleDateFormat.format(Date())

        textIniciarSesion = findViewById(R.id.textViewIniciarSesion)
        btnRegister = findViewById(R.id.buttonRegister)
        edtName = findViewById(R.id.editTextUsernameR)
        edtFullName = findViewById(R.id.editFullName)
        edtPassword = findViewById(R.id.editPassword)
        imgRegister = findViewById(R.id.imageViewRegister)
        var generator = ServiceGenerator()
        var service = generator.createServiceRegister(CovirappService::class.java)

        uriSelected = null

        ArrayAdapter.createFromResource(this, R.array.provinces, android.R.layout.simple_spinner_item).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerProvince.adapter = arrayAdapter
        }

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                provinceSelected = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        textIniciarSesion.setOnClickListener {
            var intent : Intent = Intent( this@RegisterActivity, LoginActivity::class.java )
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            if ( !edtName.text.toString().isEmpty() && !edtFullName.text.toString().isEmpty() && !provinceSelected.isEmpty()
                && !edtPassword.text.toString().isEmpty()) {
                if (uriSelected != null) {

                        val requestFile: RequestBody = RequestBody.create(getContentResolver().getType(uriSelected!!)?.toMediaTypeOrNull(), file)

                        val body: MultipartBody.Part = MultipartBody.Part.createFormData("uploadfile", "$date$originalFileName", requestFile)

                        val username: RequestBody = RequestBody.create(MultipartBody.FORM, edtName.getText().toString())

                        val fullName: RequestBody = RequestBody.create(MultipartBody.FORM, edtFullName.getText().toString())

                        val province: RequestBody = RequestBody.create(MultipartBody.FORM, provinceSelected)

                        val password: RequestBody = RequestBody.create(MultipartBody.FORM, edtPassword.getText().toString())

                        val registerService: CovirappService =
                            generator.createServiceRegister(CovirappService::class.java)

                        val callRegister: Call<ResponseBody> =
                            registerService.register(username, password, fullName, province, body)
                        callRegister.enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(
                                call: Call<ResponseBody?>?,
                                response: Response<ResponseBody?>
                            ) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(this@RegisterActivity, "Usuario registrado", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Log.e("Upload error", response.errorBody().toString())
                                }
                            }

                            override fun onFailure(
                                call: Call<ResponseBody?>?,
                                t: Throwable?
                            ) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "Error de conexión",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                } else if (uriSelected == null) {
                    Toast.makeText(this@RegisterActivity, "No has elegido avatar, sácate una foto 😁" , Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Algún campo del registro está vacío",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        imgRegister.setOnClickListener(View.OnClickListener() {
            checkPermissionForImage();
        });
    }

    fun checkPermissionForImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(permission, PERMISSION_CODE_READ)
                requestPermissions(permissionCoarse, PERMISSION_CODE_WRITE)
            } else {
                pickImageFromGallery()
            }
        }
    }

    fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            var uri : Uri? = null
            if ( data != null ) {
                uri = data.data
                originalFileName = getFileName(uri!!)!!

                file =File ( getPath( uri ) )

                Log.d("FILE", "$file")

                // Show user avatar selected from gallery
                imgRegister.load( uri ) {
                    transformations(CircleCropTransformation())

                }

                uriSelected = uri
            }
        }
    }

    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result.substring(cut + 1)
            }
        }
        return result
    }

    // Obtiene la ruta absoluta del file
    fun getPath(uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            contentResolver.query(uri!!, projection, null, null, null)
                ?: return null
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val s = cursor.getString(column_index)
        cursor.close()
        return s
    }

    fun getAbsolutePath(uri: Uri?): String? {
        val projection = arrayOf(MediaColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        return if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }
}
