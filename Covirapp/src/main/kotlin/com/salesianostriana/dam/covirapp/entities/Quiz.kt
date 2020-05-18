package com.salesianostriana.dam.covirapp.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.salesianostriana.dam.covirapp.annotation.NoArg
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.*
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

        @Column( name = "created_date", nullable = false, updatable = false )
        @CreatedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var timeCreated : LocalDate? = null,

        @Column( name = "last_modified_date", nullable = false )
        @LastModifiedDate
        @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" ) @DateTimeFormat(style = "yyyy-MM-dd") var lastUpdated : LocalDate? = null,

        @OneToOne
        @MapsId
        var user : User? = null,

        @Column( name = "id" )
        @Id @GeneratedValue val id : UUID? = null



)