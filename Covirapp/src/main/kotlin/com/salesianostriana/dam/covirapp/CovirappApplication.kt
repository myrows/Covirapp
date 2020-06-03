package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.service.FileStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CovirappApplication

fun main(args: Array<String>) {
	runApplication<CovirappApplication>(*args)
}
