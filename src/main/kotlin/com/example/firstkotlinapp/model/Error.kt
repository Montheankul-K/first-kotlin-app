package com.example.firstkotlinapp.model

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class NotFoundException(val msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.BAD_REQUEST)
data class BadRequestException(val msg: String) : RuntimeException(msg)