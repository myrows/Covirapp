package com.example.covirapp.models

class Ubicacion ( val userId : Int, val userStatus : String, val mascarilla : Boolean, val latitud : Double, var longitud : Double ) {

    constructor() : this(2, "TEST", false, -432432.32, 342432.23)
}