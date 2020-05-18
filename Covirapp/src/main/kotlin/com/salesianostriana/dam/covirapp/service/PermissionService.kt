package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.entities.Permission
import java.util.*

interface PermissionService {
    fun tryCreate(permission: Permission): Optional<Permission>
    fun findByName(name: String): Optional<Permission>
}