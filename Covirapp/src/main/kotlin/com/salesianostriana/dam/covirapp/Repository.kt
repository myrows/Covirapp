package com.salesianostriana.dam.covirapp

import com.salesianostriana.dam.covirapp.domain.Province
import com.salesianostriana.dam.covirapp.domain.Quiz
import com.salesianostriana.dam.covirapp.domain.Status
import com.salesianostriana.dam.covirapp.domain.User
import com.salesianostriana.dam.covirapp.repository.ProvinceRepository
import com.salesianostriana.dam.covirapp.repository.QuizRepository
import com.salesianostriana.dam.covirapp.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class InitDataComponent( val provinceRepository: ProvinceRepository, val quizRepository: QuizRepository, val encoder : PasswordEncoder) {

    @PostConstruct
    fun initData ( ) {

        // Province
        var province = Province ( "A Coruña", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Escudo_de_A_Coru%C3%B1a.svg/1200px-Escudo_de_A_Coru%C3%B1a.svg.png" )
        var province1 = Province ( "Álava", "https://upload.wikimedia.org/wikipedia/commons/d/df/Escudo_alava.png" )
        var province2 = Province ( "Albacete", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/df/Escudo_Albacete.svg/1200px-Escudo_Albacete.svg.png" )
        var province3 = Province ( "Alicante", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Escut_d%27Alacant_02.svg/1200px-Escut_d%27Alacant_02.svg.png" )
        var province4 = Province ( "Almería", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/Escudo_ciudad_de_Almer%C3%ADa.svg/1200px-Escudo_ciudad_de_Almer%C3%ADa.svg.png" )
        var province5 = Province ( "Asturias", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Escudo_de_Asturias_%28oficial%29.svg/1200px-Escudo_de_Asturias_%28oficial%29.svg.png" )
        var province6 = Province ( "Ávila", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/Escudo_de_%C3%81vila.svg/1200px-Escudo_de_%C3%81vila.svg.png" )
        var province7 = Province ( "Badajoz", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Provincia_de_Badajoz_-_Escudo.svg/1200px-Provincia_de_Badajoz_-_Escudo.svg.png" )
        var province8 = Province ( "Baleares", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/32/Escut_de_Balears.svg/1200px-Escut_de_Balears.svg.png" )
        var province9 = Province ( "Barcelona", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e9/Escudo_de_la_provincia_de_Barcelona.svg/800px-Escudo_de_la_provincia_de_Barcelona.svg.png" )
        var province10 = Province ( "Burgos", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d2/Escudo_de_la_Provincia_de_Burgos.svg/344px-Escudo_de_la_Provincia_de_Burgos.svg.png" )
        var province11 = Province ( "Cáceres", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/Escudo_de_la_Diputaci%C3%B3n_de_C%C3%A1ceres.svg/344px-Escudo_de_la_Diputaci%C3%B3n_de_C%C3%A1ceres.svg.png" )
        var province12 = Province ( "Cádiz", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/Escudo_de_la_provincia_de_C%C3%A1diz.svg/528px-Escudo_de_la_provincia_de_C%C3%A1diz.svg.png" )
        var province13 = Province ( "Cantabria", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/56/Escudo_de_Cantabria_%28oficial%29.svg/325px-Escudo_de_Cantabria_%28oficial%29.svg.png" )
        var province14 = Province ( "Castellón", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Escut_de_la_Prov%C3%ADncia_de_Castell%C3%B3.svg/364px-Escut_de_la_Prov%C3%ADncia_de_Castell%C3%B3.svg.png" )
        var province15 = Province ( "Córdoba", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Provincia_de_C%C3%B3rdoba_-_Escudo.svg/338px-Provincia_de_C%C3%B3rdoba_-_Escudo.svg.png" )
        var province16 = Province ( "Cuenca", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Escudo_de_la_Provincia_de_Cuenca.svg/414px-Escudo_de_la_Provincia_de_Cuenca.svg.png" )
        var province17 = Province ( "Girona", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/91/Escut_de_la_provincia_de_Girona.svg/1200px-Escut_de_la_provincia_de_Girona.svg.png" )
        var province18 = Province ( "Granada", "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Escudo_de_la_provincia_de_Granada_%28Espa%C3%B1a%29.svg/330px-Escudo_de_la_provincia_de_Granada_%28Espa%C3%B1a%29.svg.png" )
        var province19 = Province ( "Guadalajara", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0d/Escudo_de_la_provincia_de_Guadalajara.svg/338px-Escudo_de_la_provincia_de_Guadalajara.svg.png" )
        var province20 = Province ( "Gipuzkoa", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/Escudo_de_Guipuzcoa.svg/1200px-Escudo_de_Guipuzcoa.svg.png" )
        var province21 = Province ( "Huelva", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/80/Escudo_Provincia_de_Huelva.svg/644px-Escudo_Provincia_de_Huelva.svg.png" )
        var province22 = Province ( "Huesca", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Escudo_d%27a_probinzia_de_Uesca.svg/327px-Escudo_d%27a_probinzia_de_Uesca.svg.png" )
        var province23 = Province ( "Jaén", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Escudo_de_la_provincia_de_Ja%C3%A9n.svg/324px-Escudo_de_la_provincia_de_Ja%C3%A9n.svg.png" )
        var province24 = Province ( "La Rioja", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Escudo_de_la_Comunidad_Autonoma_de_La_Rioja.svg/338px-Escudo_de_la_Comunidad_Autonoma_de_La_Rioja.svg.png" )
        var province25 = Province ( "Las Palmas", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Provincia_de_Las_Palmas_-_Escudo.svg/338px-Provincia_de_Las_Palmas_-_Escudo.svg.png" )
        var province26 = Province ( "León", "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Escudo_de_Le%C3%B3n.svg/470px-Escudo_de_Le%C3%B3n.svg.png" )
        var province27 = Province ( "Lugo", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/47/Escudo_de_la_provincia_de_Lugo.svg/338px-Escudo_de_la_provincia_de_Lugo.svg.png" )
        var province28 = Province ( "Madrid", "https://upload.wikimedia.org/wikipedia/commons/thumb/d/da/Escudo_de_la_Comunidad_de_Madrid_%28oficial%29.svg/351px-Escudo_de_la_Comunidad_de_Madrid_%28oficial%29.svg.png" )
        var province29 = Province ( "Málaga", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Escudo_de_la_provincia_de_M%C3%A1laga.svg/422px-Escudo_de_la_provincia_de_M%C3%A1laga.svg.png" )
        var province30 = Province ( "Navarra", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4b/Escudo_de_Navarra_%28oficial%29.svg/337px-Escudo_de_Navarra_%28oficial%29.svg.png" )
        var province31 = Province ( "Ourense", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Provincia_de_Ourense_-_Escudo.svg/1200px-Provincia_de_Ourense_-_Escudo.svg.png" )
        var province32 = Province ( "Palencia", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Escudo_de_la_Provincia_de_Palencia.svg/466px-Escudo_de_la_Provincia_de_Palencia.svg.png" )
        var province33 = Province ( "Pontevedra", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Escudo_de_la_provincia_de_Pontevedra.svg/572px-Escudo_de_la_provincia_de_Pontevedra.svg.png" )
        var province34 = Province ( "Salamanca", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Escudo_de_la_Provincia_de_Salamanca.svg/338px-Escudo_de_la_Provincia_de_Salamanca.svg.png" )
        var province35 = Province ( "Sevilla", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8c/Escudo_de_la_provincia_de_Sevilla.svg/520px-Escudo_de_la_provincia_de_Sevilla.svg.png" )
        var province36 = Province ( "Soria", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ec/Escudo_de_la_provincia_de_Soria2.svg/338px-Escudo_de_la_provincia_de_Soria2.svg.png" )
        var province37 = Province ( "Tarragona", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Escudo_de_la_Provincia_de_Tarragona.svg/473px-Escudo_de_la_Provincia_de_Tarragona.svg.png" )
        var province38 = Province ( "Santa Cruz de Tenerife", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Provincia_de_Santa_Cruz_de_Tenerife_-_Escudo.svg/338px-Provincia_de_Santa_Cruz_de_Tenerife_-_Escudo.svg.png" )
        var province39 = Province ( "Teruel", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Escudo_de_la_Provincia_de_Teruel.svg/510px-Escudo_de_la_Provincia_de_Teruel.svg.png" )
        var province40 = Province ( "Valencia", "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Escut_de_la_Prov%C3%ADncia_de_Val%C3%A8ncia.svg/510px-Escut_de_la_Prov%C3%ADncia_de_Val%C3%A8ncia.svg.png" )
        var province41 = Province ( "Valladolid", "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Va-dip.svg/567px-Va-dip.svg.png" )
        var province42 = Province ( "Vizcaya", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Escudo_de_Bizkaia_2007.svg/685px-Escudo_de_Bizkaia_2007.svg.png" )
        var province43 = Province ( "Zamora", "https://upload.wikimedia.org/wikipedia/commons/thumb/5/57/Escudo_de_la_provincia_de_Zamora.svg/338px-Escudo_de_la_provincia_de_Zamora.svg.png" )
        var province44 = Province ( "Zaragoza", "https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Escudo_d%27a_probinzia_de_Zaragoza.svg/383px-Escudo_d%27a_probinzia_de_Zaragoza.svg.png" )




        var listProvince = mutableListOf<Province>(province, province1, province2, province3, province4, province5, province6, province7, province8,
        province9, province10, province11, province12, province13, province14, province15, province16, province17, province18, province19, province19,
        province20, province21, province22, province23, province24, province25, province26, province27, province28, province29, province30, province31,
        province32, province33, province34, province35, province36, province37, province37, province38, province39, province40, province41, province42,
        province43, province44)
        for ( p in listProvince ) {
            var resp = provinceRepository.findProvincesByname(p.name)

            if (resp.isEmpty()) {
                provinceRepository.save(p)
            }
        }
    }
}