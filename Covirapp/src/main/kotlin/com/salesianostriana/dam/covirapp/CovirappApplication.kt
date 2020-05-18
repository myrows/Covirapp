package com.salesianostriana.dam.covirapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CovirappApplication

fun main(args: Array<String>) {
	runApplication<CovirappApplication>(*args)
}
