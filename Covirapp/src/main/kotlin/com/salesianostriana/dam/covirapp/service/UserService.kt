package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.User
import java.util.*

interface UserService {
    fun tryCreate(user: User): Optional<User>
    fun findByUsername(username: String): Optional<User>
}