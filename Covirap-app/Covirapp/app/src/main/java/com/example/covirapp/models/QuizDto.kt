package com.example.covirapp.models

data class QuizDto(
    val years: Int,
    val cough: Boolean,
    val neckPain: Boolean,
    val respiratoryPain: Boolean,
    val tasteLost: Boolean,
    val smellLost: Boolean,
    val fever: Boolean,
    val riskPerson: Boolean,
    val contactWithInfected: Boolean

)