package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class QuizServiceImpl ( private val repo: QuizRepository) : QuizService {
    @Transactional
    override fun findById(id: Long): Optional<Quiz> = repo.findById( id )

    @Transactional
    override fun findQuizOfUser(userAuthenticated: User): List<Quiz> = repo.findQuizOfUser(userAuthenticated)

    @Transactional
    override fun findQuizs(id: Long): List<Quiz> = repo.findQuizs( id )

    @Transactional
    override fun findAll(): List<Quiz> = repo.findAll()

    @Transactional
    override fun save(quiz: Quiz): Quiz = repo.save(quiz)


}