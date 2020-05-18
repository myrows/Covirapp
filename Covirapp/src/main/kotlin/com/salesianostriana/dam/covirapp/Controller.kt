package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/covirapp")
class UserController ( val userRepository: UserRepository ) {

    @GetMapping("/")
    fun findAllUsers() = userRepository.findAll()

}