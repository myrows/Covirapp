package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.entities.User
import java.util.*

interface UserService {
    fun tryCreate(user: User): Optional<User>
    fun findByUsername(username: String): Optional<User>
}