package com.salesianostriana.dam.covirapp.controller

import com.salesianostriana.dam.covirapp.service.FileStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class UploadFileController {

    @Autowired
    lateinit var fileStorage: FileStorage

    @GetMapping("/")
    fun index(): String {
        return "multipartfile/uploadform.html"
    }
}