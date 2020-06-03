package com.salesianostriana.dam.covirapp.repository

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>

    @Query("select u from User u where u.province = :province")
    fun findUsersWithSameProvince ( province : String ) : List<User>
}