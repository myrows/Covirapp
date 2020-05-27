package com.salesianostriana.dam.covirapp.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonFormat
import com.salesianostriana.dam.covirapp.annotation.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.persistence.*

@Entity
@NoArg
@EntityListeners(AuditingEntityListener::class)
@Table( name = "\"quiz\"" )
data class Quiz (

        @Column( name = "years" )
        var years : Int,

        @Column( name = "cough" )
        var cough : Boolean,

        @Column( name = "neck_pain" )
        var neckPain : Boolean,

        @Column( name = "respiratory_pain" )
        var respiratoryPain : Boolean,

        @Column( name = "taste_lost" )
        var tasteLost : Boolean,

        @Column( name = "smell_lost" )
        var smellLost : Boolean,

        @Column( name = "fever" )
        var fever : Boolean,

        @Column( name = "risk_person" )
        var riskPerson : Boolean,

        @Column( name = "contact_with_infected" )
        var contactWithInfected : Boolean,

        @JsonBackReference @ManyToOne var user : User? = null,

        @Column( name = "created_date", nullable = false, updatable = false )
        @CreatedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var timeCreated : LocalDate? = null,

        @Column( name = "last_modified_date", nullable = false )
        @LastModifiedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var lastUpdated : LocalDate? = null,

        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0



)