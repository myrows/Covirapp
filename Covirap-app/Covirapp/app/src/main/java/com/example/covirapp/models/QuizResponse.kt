package com.example.covirapp.models

data class QuizResponse(
    val contactWithInfected: Boolean,
    val cough: Boolean,
    val fever: Boolean,
    val id: Int,
    val lastUpdated: String,
    val neckPain: Boolean,
    val respiratoryPain: Boolean,
    val riskPerson: Boolean,
    val smellLost: Boolean,
    val tasteLost: Boolean,
    val timeCreated: String,
    val years: Int
)