package com.example.covirapp.common

class Constantes {

    companion object {
        //const val API_BASE_URL = "http://10.0.2.2:9000"
        const val API_BASE_URL = "http://ec2-15-188-232-141.eu-west-3.compute.amazonaws.com:9000"
        const val API_BASE_URL_COVID_COUNTRY = "https://disease.sh/v2/"
        const val API_BASE_URL_REGIONS = "https://api.covid19tracking.narrativa.com"
        const val API_BASE_URL_GRAPHIC = "https://api.covid19api.com"
        const val SHARED_PREFS_FILE: String = "SHARED_PREFERENCES_FILE"
        const val TIMEOUT_INMILIS = 30000L
    }
}