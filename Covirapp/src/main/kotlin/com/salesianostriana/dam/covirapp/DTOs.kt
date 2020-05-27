package com.salesianostriana.dam.covirapp

import com.fasterxml.jackson.annotation.JsonFormat
import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.time.LocalDate


data class QuizDTO (
        val years : Int,
        val cough : Boolean,
        val neckPain : Boolean,
        val respiratoryPain : Boolean,
        val tasteLost : Boolean,
        val smellLost : Boolean,
        val fever : Boolean,
        val riskPerson : Boolean,
        val contactWithInfected : Boolean,
        val timeCreated : LocalDate? = null,
        val lastUpdated : LocalDate? = null,
        val id : Long? = null
)

fun Quiz.toQuizDTO() = QuizDTO ( years, cough, neckPain, respiratoryPain, tasteLost, smellLost, fever, riskPerson, contactWithInfected, timeCreated, lastUpdated, id)

fun QuizDTO.toQuiz() = Quiz ( years, cough, neckPain, respiratoryPain, tasteLost, smellLost, fever, riskPerson, contactWithInfected, null, timeCreated, lastUpdated, id )

data class NuevoQuizDTO(
        val years : Int,
        val cough : Boolean,
        val neckPain : Boolean,
        val respiratoryPain : Boolean,
        val tasteLost : Boolean,
        val smellLost : Boolean,
        val fever : Boolean,
        val riskPerson : Boolean,
        val contactWithInfected : Boolean,
        @AuthenticationPrincipal val user : User? = null,
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
        @DateTimeFormat(style = "yyyy-MM-dd")
        @CreatedDate
        val timeCreated : LocalDate? = null,
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" )
        @DateTimeFormat(style = "yyyy-MM-dd")
        @LastModifiedDate
        val lastUpdated: LocalDate? = null
)

fun NuevoQuizDTO.toQuiz() = Quiz ( years, cough, neckPain, respiratoryPain, tasteLost, smellLost, fever, riskPerson, contactWithInfected, user, timeCreated, lastUpdated )

data class UserDTO(
        var username : String,
        var password : String,
        var fullName : String,
        var province : String,
        var avatar : String? = null,
        var status : Status?,
        val id : Long? = null
)

fun User.toUserDTO() = UserDTO( username, password, fullName, province, avatar, status, id )

data class CreateUserDTO(
        var username: String,
        var fullName: String,
        var province: String,
        var status: Status? = null,
        val password: String,
        val password2: String
)


