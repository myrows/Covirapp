package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import com.salesianostriana.dam.covirapp.repository.UserRepository
import com.salesianostriana.dam.covirapp.service.UserCreateService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/covirapp")
class UserController ( val userRepository: UserRepository, val quizRepository: QuizRepository, val userService : UserCreateService ) {

    @GetMapping("/quiz")
    fun findAllQuiz() = quizRepository.findAll().map { it.toQuizDTO() }

    @PostMapping("/quiz")
    fun createQuiz( @RequestBody quiz : NuevoQuizDTO, @AuthenticationPrincipal user : User ) : Quiz {
        return quizRepository.save(NuevoQuizDTO( quiz.years, quiz.cough, quiz.neckPain, quiz.respiratoryPain, quiz.tasteLost, quiz.smellLost, quiz.fever,
        quiz.riskPerson, user).toQuiz())
    }

    @GetMapping("/user")
    fun getAllUsers() = userRepository.findAll().map { it.toUserDTO() }

    @PostMapping("/user")
    fun createUser( @RequestBody newUser : CreateUserDTO ) : ResponseEntity<UserDTO> =
            userService.create(newUser).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUserDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de  usuario ${newUser.username} ya existe")
            }

}