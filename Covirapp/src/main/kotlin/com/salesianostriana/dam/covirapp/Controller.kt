package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.domain.Province
import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.ProvinceRepository
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import com.salesianostriana.dam.covirapp.repository.UserRepository
import com.salesianostriana.dam.covirapp.service.FileStorage
import com.salesianostriana.dam.covirapp.service.RoleService
import com.salesianostriana.dam.covirapp.service.UserCreateService
import org.apache.coyote.Response
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/covirapp")
class UserController ( val userRepository: UserRepository, val quizRepository: QuizRepository, val userService : UserCreateService,
                       val provinceRepository: ProvinceRepository, val roleService : RoleService, val encoder: PasswordEncoder, val fileStorage: FileStorage) {

    @GetMapping("/quiz")
    fun findAllQuiz() = quizRepository.findAll().map { it.toQuizDTO() }

    @PostMapping("/quiz")
    fun createQuiz( @RequestBody quiz : NuevoQuizDTO, @AuthenticationPrincipal user : User ) : Quiz {
        return quizRepository.save(NuevoQuizDTO( quiz.years, quiz.cough, quiz.neckPain, quiz.respiratoryPain, quiz.tasteLost, quiz.smellLost, quiz.fever,
        quiz.riskPerson, quiz.contactWithInfected, user).toQuiz())
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

    @GetMapping("/province/{name}")
    fun getProvinceByName( @PathVariable name : String ) = provinceRepository.findProvinceByname(name).toProvinceDTO()

    @PostMapping("/province")
    fun createProvince( @RequestBody province : NuevoProvinceDTO ) : Province {
        return provinceRepository.save(NuevoProvinceDTO( province.name, province.url).toProvince())
    }

    @GetMapping("/user")
    fun getAllUsers() = userRepository.findAll().map { it.toUserDTO() }

    @GetMapping("/user/me")
    fun getUserInformation( @AuthenticationPrincipal user: User ) : UserDTO {
        return user.toUserDTO()
    }

    @GetMapping("/user/me/province")
    fun getAllUsersForProvince (@AuthenticationPrincipal user : User) = userRepository.findUsersWithSameProvince( user.province ).map { it.toUserDTO() }

    @GetMapping("/user/me/avatar/{id}")
    fun getUserAvatar ( @PathVariable id : Long ) : ResponseEntity<Resource>{
      var userFound : User = userRepository.findById( id ).get()
        val file = fileStorage.loadFile(userFound.avatar)

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.filename + "\"")
                .body(file)
    }

    @PostMapping("/user")
    fun createUserWithAvatar( @RequestParam("username") username : String, @RequestParam("password") password : String,
                              @RequestParam("fullName") fullName : String, @RequestParam("province") province : String,
                              @RequestParam("uploadfile") file: MultipartFile) : ResponseEntity<UserDTO> =
            userService.create(username, password, fullName, province, file, roleService.findByName("ROLE_USER").get()).map { ResponseEntity.status(HttpStatus.CREATED).body(it.toUserDTO()) }.orElseThrow {
                ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de  usuario $username ya existe")
            }

    @PutMapping("/user/{id}")
    fun editUser( @RequestBody user : CreateUserDTO, @PathVariable id: Long ) :UserDTO {
        return userService.edit( user, userRepository.findById( id ).get() )
    }

    @PutMapping("/quiz/status/")
    fun editStatusUser( @RequestBody quiz : NuevoQuizDTO, @AuthenticationPrincipal user: User ) :UserDTO {
        return userService.testStatus( quiz, user )
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

    @GetMapping("/files/{filename}")
    fun downloadFile(@PathVariable filename: String): ResponseEntity<Resource> {
        val file = fileStorage.loadFile(filename)
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.filename + "\"")
                .body(file)
    }

    @PostMapping("/upload")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): String {
        fileStorage.store(file)
        model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
        return file.originalFilename!!
    }

}