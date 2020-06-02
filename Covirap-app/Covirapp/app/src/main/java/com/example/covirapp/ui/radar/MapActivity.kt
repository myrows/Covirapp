package com.example.covirapp.ui.radar

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.example.covirapp.MainActivity
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.models.Ubicacion
import com.example.covirapp.models.UsersResponse
import com.example.covirapp.models.UsersResponseItem
import com.example.covirapp.ui.account.DetectObjectActivity
import com.example.covirapp.ui.account.MyAccountActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_map.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    lateinit var supportMapFragment: SupportMapFragment
    lateinit var client: FusedLocationProviderClient

    lateinit var ubication: FusedLocationProviderClient
    lateinit var db: FirebaseFirestore

    lateinit var locationManager: LocationManager
    var userId: Int = 0
    lateinit var mMap : GoogleMap

    var generator : ServiceGenerator = ServiceGenerator()
    lateinit var service : CovirappService

    lateinit var userStats : String
    var mascarillaFound : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setTitle("Radar")

        userStats = ""

        // Instances Firebase y Location
        locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        db = FirebaseFirestore.getInstance()
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        client = LocationServices.getFusedLocationProviderClient(this)

        // Data of SharedPreferences

        userId = SharedPreferencesManager.SharedPreferencesManager.getSomeIntValue("userId")
        mascarillaFound = SharedPreferencesManager.SharedPreferencesManager.getSomeBooleanValue("mascarillaFound")
        userStats = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("status").toString()

        // Check permissions

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // Check mask
            if ( !mascarillaFound ) alertNotMask()

            ubication = LocationServices.getFusedLocationProviderClient(this)
            ubication.lastLocation.addOnSuccessListener {

                if (it != null) {
                    giveMeYourUbication(it)
                }
                addMarkers()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }

        floatingActionButtonRadar.setOnClickListener {
            addMarkers()
        }


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mMap.clear()

    }

    fun giveMeYourUbication(location: Location) {

        if (location != null) {
            var latitud: Double = location.latitude
            var longitud: Double = location.longitude


            var ub: Ubicacion = Ubicacion(userId, userStats, mascarillaFound, latitud, longitud)

            // Query para buscar aquellas ubicaciones existentes para el usuario authenticated

            db.collection("ubicaciones")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("Query", "${document.id} => ${document.data}")
                    }

                    if (documents.size() <= 0) {
                        // Se crea la ubicación en el Cloud Firestore
                        db.collection("ubicaciones")
                            .add(ub)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Ubicación añadida con éxito")
                            }

                    } else {

                        // Se edita la ubicación existente para el usuario authenticated

                        db.collection("ubicaciones")
                            .whereEqualTo("userId", userId)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    Log.d("Query", "${document.id} => ${document.data}")

                                    Log.d("Status", "$userStats")

                                    var map = mutableMapOf<String, Any>()
                                    map.put(key = "latitud", value = latitud)
                                    map.put(key = "longitud", value = longitud)
                                    map.put(key = "userStatus", value = userStats)
                                    map.put(key = "mascarilla", value = mascarillaFound)
                                    map.put(key = "userId", value = userId)
                                    db.collection("ubicaciones").document(document.id).update(map)
                                        .addOnSuccessListener {
                                            Log.d(
                                                "Update",
                                                "Se ha actualizado los datos correctamente"
                                            )

                                        }.addOnFailureListener {
                                        Log.d(
                                            "Update",
                                            "Ha ocurrido un error al actualizar el documento"
                                        )
                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Query", "Error getting documents: ", exception)
                            }

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("Query", "Error getting documents: ", exception)
                }
        }
    }

    fun addMarkers () {
        mMap.clear()
        supportMapFragment.getMapAsync {

            db.collection("ubicaciones")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        var ubication : Ubicacion = document.toObject(Ubicacion::class.java)

                        when (ubication.userStatus) {
                            "SALUDABLE" -> {
                                it.addMarker(MarkerOptions()
                                    .position(LatLng(ubication.latitud, ubication.longitud))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                            }
                            "INFECTADO" -> {
                                it.addMarker(MarkerOptions()
                                    .position(LatLng(ubication.latitud, ubication.longitud))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                            }
                            "ASINTOMATICO" -> {
                                it.addMarker(MarkerOptions()
                                    .position(LatLng(ubication.latitud, ubication.longitud))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)))
                            }
                            "RECUPERADO" -> {
                                it.addMarker(MarkerOptions()
                                    .position(LatLng(ubication.latitud, ubication.longitud))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
                            }
                        }


                    }
                }
        }
    }

    override fun onLocationChanged(newLocation: Location?) {

        db.collection("ubicaciones")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("Query", "${document.id} => ${document.data}")

                    if (documents.size() > 0) {

                        var map = mutableMapOf<String, Any>()
                        map.put(key = "latitud", value = newLocation!!.latitude)
                        map.put(key = "longitud", value = newLocation!!.longitude)
                        map.put(key = "mascarilla", value = mascarillaFound)
                        map.put(key = "userStatus", value = userStats)
                        map.put(key = "userId", value = userId)
                        db.collection("ubicaciones").document(document.id).update(map)
                            .addOnSuccessListener {
                                Log.d(
                                    "Update",
                                    "Se ha actualizado los datos correctamente"
                                )
                                addMarkers()

                            }.addOnFailureListener {
                                Log.d(
                                    "Update",
                                    "Ha ocurrido un error al actualizar el documento"
                                )
                            }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Query", "Error getting documents: ", exception)
            }
    }

    private fun alertNotMask () {
        val dialog  = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.custom_object_map_error, null)
        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("Aceptar", { dialogInterface: DialogInterface, i: Int -> })
        val customDialog = dialog.create()
        customDialog.show()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            var intentDetector = Intent( this@MapActivity, MainActivity::class.java )
            startActivity( intentDetector )
        }
    }
}
