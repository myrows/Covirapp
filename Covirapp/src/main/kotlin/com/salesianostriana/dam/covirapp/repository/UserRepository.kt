package com.salesianostriana.dam.covirapp.repository

import com.salesianostriana.dam.covirapp.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(propagation = Propagation.MANDATORY)
interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
}