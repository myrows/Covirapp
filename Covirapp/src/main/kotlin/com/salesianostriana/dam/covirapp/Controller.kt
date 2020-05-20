package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import com.salesianostriana.dam.covirapp.repository.UserRepository
import com.salesianostriana.dam.covirapp.service.RoleService
import com.salesianostriana.dam.covirapp.service.UserCreateService
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/covirapp")
class UserController ( val userRepository: UserRepository, val quizRepository: QuizRepository, val userService : UserCreateService,
    val roleService : RoleService, val encoder: PasswordEncoder) {

    @GetMapping("/quiz")
    fun findAllQuiz() = quizRepository.findAll().map { it.toQuizDTO() }

    @PostMapping("/quiz")
    fun createQuiz( @RequestBody quiz : NuevoQuizDTO, @AuthenticationPrincipal user : User ) : Quiz {
        return quizRepository.save(NuevoQuizDTO( quiz.years, quiz.cough, quiz.neckPain, quiz.respiratoryPain, quiz.tasteLost, quiz.smellLost, quiz.fever,
        quiz.riskPerson, user).toQuiz())
    }

    @PutMapping("/quiz/{userAuthenticated}")
    fun editQuiz( @RequestBody quiz : NuevoQuizDTO, @AuthenticationPrincipal userAuthenticated : User ) : QuizDTO {
        var sizeOfHistoyQuiz = quizRepository.findQuizOfUser( userAuthenticated ).size

        if ( sizeOfHistoyQuiz < 0 )
            ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado resultados")

        var lastQuizDone = quizRepository.findQuizOfUser( userAuthenticated ).get(sizeOfHistoyQuiz - 1)
        var quizUpdated : Quiz = lastQuizDone.copy( years = quiz.years, cough = quiz.cough, neckPain = quiz.neckPain,
        respiratoryPain = quiz.respiratoryPain, tasteLost = quiz.tasteLost, smellLost = quiz.smellLost, fever = quiz.fever,
        riskPerson = quiz.riskPerson, user = userAuthenticated)

        return quizRepository.save( quizUpdated ).toQuizDTO()
    }

    @GetMapping("/user")
    fun getAllUsers() = userRepository.findAll().map { it.toUserDTO() }

    @GetMapping("/user/me")
    fun getUserInformation( @AuthenticationPrincipal user: User ) : UserDTO {
        return user.toUserDTO()
    }

    @PostMapping("/user")
    fun createUser( @RequestBody newUser : CreateUserDTO ) : ResponseEntity<UserDTO> =
            userService.create(newUser, roleService.findByName("ROLE_USER").get()).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUserDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de  usuario ${newUser.username} ya existe")
            }

    @PutMapping("/user/{id}")
    fun editUser( @RequestBody user : CreateUserDTO, @PathVariable id: Long ) :UserDTO {
        return userService.edit( user, userRepository.findById( id ).get() )
    }

    @PutMapping("/user/status/{id}")
    fun editStatusUser( @PathVariable id: Long ) :UserDTO {
        return userService.testStatus( userRepository.findById( id ).get() )
    }

    @PutMapping("/user/me/status")
    fun updateStatus( @RequestBody user : CreateUserDTO, @AuthenticationPrincipal userAuthenticated: User ) : UserDTO {
        return userService.updateStatus( user, userAuthenticated )
    }

    @DeleteMapping("/user/{id}")
    fun deleteAccount( @PathVariable id : Long ) : ResponseEntity<Void> {
        userRepository.deleteById( id )
        return ResponseEntity.noContent().build()
    }

}