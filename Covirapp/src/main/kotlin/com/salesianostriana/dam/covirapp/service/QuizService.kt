package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface QuizService {
    fun findById( id : Long ) : Optional<Quiz>
    fun findQuizOfUser( userAuthenticated : User) : List<Quiz>
    fun findQuizs ( id : Long ) : List<Quiz>
    fun findAll ( ) : List<Quiz>
    fun save(quiz : Quiz) : Quiz
}