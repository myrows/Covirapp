package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.CreateUserDTO
import com.salesianostriana.dam.covirapp.NuevoQuizDTO
import com.salesianostriana.dam.covirapp.UserDTO
import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.domain.Role
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.UserRepository
import com.salesianostriana.dam.covirapp.tasks.SetupUserDataTask
import com.salesianostriana.dam.covirapp.toUserDTO
import org.codehaus.jackson.map.annotate.JsonDeserialize
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UserCreateService(
        private val userRepository: UserRepository,
        private val encoder: PasswordEncoder,
        val fileStorage: FileStorage
) {

    fun create(username : String, password : String, fullName : String, province : String, file : MultipartFile?, role : Role): Optional<User> {
        fileStorage.store(file)

        if (userRepository.findByUsername(username).isPresent)
            return Optional.empty()
        return Optional.of(
                    userRepository.save(User( username = username, password = encoder.encode(password), fullName = fullName, province = province, avatar = file?.originalFilename, status = Status.SALUDABLE, roles = hashSetOf(role)))
        )
    }

    fun edit( userToEdit : CreateUserDTO, userFound : User ) : UserDTO {
        var userUpdated : User = userFound.copy(username = userToEdit.username, password = encoder.encode(userToEdit.password), fullName = userToEdit.fullName, province = userFound.province,
                status = userFound.status, roles = userFound.roles, quizs = userFound.quizs, authorities = userFound.authorities, nonExpired = userFound.isAccountNonExpired, nonLocked = userFound.isAccountNonLocked, enabled = userFound.isEnabled, credentialsNonExpired = userFound.isCredentialsNonExpired )

        return userRepository.save(userUpdated).toUserDTO()
    }

    fun testStatus( quiz : NuevoQuizDTO, userFound : User ) : UserDTO {
        var countStatistics : Int = 0

        when ( quiz.cough ) { true -> countStatistics++ }
        when ( quiz.fever ) { true -> countStatistics+= 2 }
        when ( quiz.neckPain ) { true -> countStatistics++ }
        when ( quiz.respiratoryPain ) { true -> countStatistics+= 2 }
        when ( quiz.tasteLost ) { true -> countStatistics++ }
        when ( quiz.smellLost ) { true -> countStatistics++ }
        when ( quiz.riskPerson ) { true -> countStatistics++ }

        if ( quiz.fever && quiz.respiratoryPain ) {
            userFound.status = Status.INFECTADO
        }

        if ( quiz.contactWithInfected && countStatistics >= 3 ) {
            userFound.status = Status.INFECTADO
        }

        when ( countStatistics ) {
            in 0..3 -> { if( !quiz.contactWithInfected ) userFound.status = Status.SALUDABLE }
            in 4..9 -> userFound.status = Status.INFECTADO

        }


        return userRepository.save(userFound).toUserDTO()
    }

    fun updateStatus( user : CreateUserDTO, userFound : User ) : UserDTO {

        when( user.status.toString() ) {
            "SALUDABLE" -> userFound.status = Status.SALUDABLE
            "ASINTOMATICO" -> userFound.status = Status.ASINTOMATICO
            "INFECTADO" -> userFound.status = Status.INFECTADO
            "RECUPERADO" -> userFound.status = Status.RECUPERADO
        }

        return userRepository.save( userFound ).toUserDTO()
    }

}