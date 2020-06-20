package com.salesianostriana.dam.covirapp.tasks

import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.domain.Role
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.service.PermissionService
import com.salesianostriana.dam.covirapp.service.RoleService
import com.salesianostriana.dam.covirapp.service.UserService
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SetupUserDataTask(
        private val userService: UserService,
        private val roleService: RoleService,
        private val permissionService: PermissionService
) : ApplicationListener<ContextRefreshedEvent> {
    private var alreadySetup = false

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) return
        alreadySetup = true

        val readPermission = createPermissionIfNotFound("READ_PERMISSION")
        val writePermission = createPermissionIfNotFound("WRITE_PERMISSION")

        val adminRole = createRoleIfNotFound("ROLE_ADMIN", hashSetOf(readPermission, writePermission))
        val userRole = createRoleIfNotFound("ROLE_USER", hashSetOf(readPermission))

        createUserIfNotFound("usuario", "12345678", "ashdgj", "Jaén", Status.SALUDABLE,  hashSetOf(adminRole, userRole), "photo-1546539782-6fc531453083.jpeg")
        createUserIfNotFound("user2", "12345678", "ashdgj", "Barcelona", Status.SALUDABLE, hashSetOf(userRole), "photo-1520813792240-56fc4a3765a7.jpeg")
    }

    fun createPermissionIfNotFound(name: String): Permission {
        val permission = Permission(name = name)
        var retrieval = permissionService.tryCreate(permission)
        if (!retrieval.isPresent)
            retrieval = permissionService.findByName(name)
        return retrieval.get()
    }


    fun createRoleIfNotFound(name: String, permissions: Set<Permission>): Role {
        val role = Role(name = name, permissions = permissions)
        var retrieval = roleService.tryCreate(role)
        if (!retrieval.isPresent)
            retrieval = roleService.findByName(name)
        return retrieval.get()
    }


    fun createUserIfNotFound(username: String, password: String, fullName : String, province : String, status : Status, roles: Set<Role>, avatar : String ): User {
        val user = User(username = username, password = password, fullName = fullName, province = province, status = status, roles = roles, avatar = avatar)
        var retrieval = userService.tryCreate(user)
        if (!retrieval.isPresent)
            retrieval = userService.findByUsername(username)
        return retrieval.get()
    }
}