package com.salesianostriana.dam.covirapp.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.salesianostriana.dam.covirapp.annotation.NoArg
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@NoArg
@EntityListeners(AuditingEntityListener::class)
@Table( name = "\"province\"" )
data class Province (

        @Column( name = "name" )
        var name : String,

        @Column( name = "url" )
        var url : String,

        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0
)