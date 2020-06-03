package com.salesianostriana.dam.covirapp.repository

import com.salesianostriana.dam.covirapp.domain.Permission
import com.salesianostriana.dam.covirapp.domain.Province
import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ProvinceRepository: JpaRepository<Province, Long> {
    fun findByName(name: String): Optional<Province>

    @Query("select p from Province p where p.name = :nameFound")
    fun findProvincesByname ( nameFound : String) : List<Province>

    @Query("select p from Province p where p.name = :name")
    fun findProvinceByname ( name : String) : Province
}