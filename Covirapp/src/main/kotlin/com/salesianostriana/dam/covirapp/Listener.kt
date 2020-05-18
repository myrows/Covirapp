package com.salesianostriana.dam.covirapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
class JpaAuditingConfiguration {

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware { Optional.of("Mr. Auditor") }
    }

}