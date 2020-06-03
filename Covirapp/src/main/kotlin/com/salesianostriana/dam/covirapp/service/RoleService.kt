package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Role
import java.util.*

interface RoleService {
    fun tryCreate(role: Role): Optional<Role>
    fun findByName(name: String): Optional<Role>
}