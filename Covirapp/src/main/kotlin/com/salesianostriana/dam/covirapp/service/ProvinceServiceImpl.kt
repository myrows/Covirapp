package com.salesianostriana.dam.covirapp.service

import com.salesianostriana.dam.covirapp.domain.Province
import com.salesianostriana.dam.covirapp.repository.ProvinceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProvinceServiceImpl ( private val repo : ProvinceRepository ) : ProvinceService {
    @Transactional
    override fun findProvincesByname(nameFound: String): List<Province> = repo.findProvincesByname( nameFound )

    @Transactional
    override fun findProvinceByname(name: String): Province = repo.findProvinceByname( name )

    @Transactional
    override fun save(province: Province): Province = repo.save( province )


}