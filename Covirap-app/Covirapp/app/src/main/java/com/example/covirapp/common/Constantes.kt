package com.example.covirapp.common

class Constantes {

    companion object {
        const val API_BASE_URL = "http://10.0.2.2:9000"
        const val API_BASE_URL_COVID_COUNTRY = "https://disease.sh/v2/"
        const val API_BASE_URL_REGIONS = "https://api.covid19tracking.narrativa.com"
        const val API_BASE_URL_GRAPHIC = "https://api.covid19api.com"
        const val SHARED_PREFS_FILE: String = "SHARED_PREFERENCES_FILE"
        const val TIMEOUT_INMILIS = 30000L
        const val  COGNITO_POOL_ID = "us-east-2:811a3201-becf-419a-88df-82a083a9e444"
        const val COGNITO_POOL_REGION = "us-east-2"
        const val BUCKET_NAME = "dmtroncosobucket"
        const val BUCKET_REGION = "eu-west-3"
        const val ACCESS_KEY = "AKIAW7KVJR665VAWDGLR"
        const val SECRET_KEY = "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"
        const val TOPICARN= "arn:aws:s3:::dmtroncosobucket"
        const val ROLEARN= "arn:aws:iam::479604608957:user/dmtroncosoTemporalUser"
    }
}