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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.covirapp.MainActivity
import com.example.covirapp.R
import com.example.covirapp.api.CovirappService
import com.example.covirapp.api.generator.ServiceGenerator
import com.example.covirapp.common.SharedPreferencesManager
import com.example.covirapp.models.Ubicacion
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

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
        db = FirebaseFirestore.getInstance()
        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
        //client = LocationServices.getFusedLocationProviderClient(this)

        // Data of SharedPreferences

        userId = SharedPreferencesManager.SharedPreferencesManager.getSomeIntValue("userId")
        mascarillaFound = SharedPreferencesManager.SharedPreferencesManager.getSomeBooleanValue("mascarillaFound")
        userStats = SharedPreferencesManager.SharedPreferencesManager.getSomeStringValue("status").toString()

        // Get user ubication and add marker

        giveMeYourUbication(getLocation()!!)

        floatingActionButtonRadar.setOnClickListener {
            addMarkers()
        }


    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        if( !mascarillaFound ) alertNotMask()

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
        addMarkers()

    }

    fun addMarkers () {

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

    fun getLocation(): Location? {
        var finalLoc: Location? = null

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                try {
                    var gps_enabled = false
                    var network_enabled = false

                    val lm : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

                    gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    var net_loc: Location? = null
                    var gps_loc: Location? = null

                    if (gps_enabled) gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (network_enabled) net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    if (gps_loc != null && net_loc != null) {

                        //smaller the number more accurate result will
                        finalLoc = if (gps_loc.accuracy > net_loc.accuracy) net_loc else gps_loc

                        // I used this just to get an idea (if both avail, its upto you which you want to take as I've taken location with more accuracy)
                    } else {
                        if (gps_loc != null) {
                            finalLoc = gps_loc
                        } else if (net_loc != null) {
                            finalLoc = net_loc
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
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

        return finalLoc
    }
}
