package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.CreateUserDTO
import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.domain.Role
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.UserRepository
import com.salesianostriana.dam.covirapp.tasks.SetupUserDataTask
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserCreateService(
        private val userRepository: UserRepository,
        private val encoder: PasswordEncoder
) {

    fun create(newUser: CreateUserDTO): Optional<User> {
        if (userRepository.findByUsername(newUser.username).isPresent)
            return Optional.empty()
        return Optional.of(
                with(newUser) {
                    userRepository.save(User( username = username, password = encoder.encode(password), fullName = fullName, province = province))
                }

        )
    }

}