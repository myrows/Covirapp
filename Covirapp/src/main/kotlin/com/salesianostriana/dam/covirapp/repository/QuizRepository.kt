package com.salesianostriana.dam.covirapp.repository

import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface QuizRepository: JpaRepository<Quiz, Long> {

    @Query("select q from Quiz q where q.user = :userAuthenticated")
    fun findQuizOfUser ( userAuthenticated : User ) : List<Quiz>
}