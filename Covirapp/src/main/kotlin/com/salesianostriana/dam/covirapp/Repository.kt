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

        // Quiz
        var quiz = Quiz ( 36, false, false, false, false, true, false, false, false )
        var quiz1 = Quiz ( 39, true, false, false, false, true, false, false, false )

        var listQuiz = listOf<Quiz>(quiz, quiz1)
        for ( q in listQuiz ) {
            var resp = quizRepository.findById( q.id!! )

            if ( !resp.isPresent ) {
                quizRepository.save(q)
            }
        }

        // User


        var listUsers = listOf<User>()
        for ( u in listUsers ) {
            var resp = userRepository.findById( u.id!! )

            if ( !resp.isPresent ) {
                userRepository.save(u)
            }
        }
    }
}