package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Province

interface ProvinceService {
    fun findProvincesByname ( nameFound : String) : List<Province>
    fun findProvinceByname ( name : String) : Province
    fun save( province : Province ) : Province
}