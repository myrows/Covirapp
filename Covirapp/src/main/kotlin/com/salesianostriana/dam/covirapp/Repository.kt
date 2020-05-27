package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import com.salesianostriana.dam.covirapp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class InitDataComponent( val userRepository: UserRepository, val quizRepository: QuizRepository, val encoder : PasswordEncoder) {

    @PostConstruct
    fun initData ( ) {

        var quiz = Quiz ( 36, false, false, false, false, true, false, false, false )
        var quiz1 = Quiz ( 39, true, false, false, false, true, false, false, false )

        quizRepository.saveAll(listOf<Quiz>(quiz, quiz1))
    }
}