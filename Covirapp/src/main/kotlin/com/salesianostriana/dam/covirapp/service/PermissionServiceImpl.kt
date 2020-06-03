package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.repository.PermissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PermissionServiceImpl(
        private val repo: PermissionRepository
) : PermissionService {
    @Transactional
    override fun tryCreate(permission: Permission): Optional<Permission> {
        if (findByName(permission.name).isPresent)
            return Optional.empty()
        return Optional.of(repo.save(permission))
    }

    @Transactional
    override fun findByName(name: String) = repo.findByName(name)
}