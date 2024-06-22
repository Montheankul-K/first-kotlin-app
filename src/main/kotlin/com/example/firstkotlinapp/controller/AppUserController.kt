package com.example.firstkotlinapp.controller

import com.example.firstkotlinapp.model.AppUser
import com.example.firstkotlinapp.model.AppUserRequest
import com.example.firstkotlinapp.service.AppUserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class AppUserController(
    private val appUserService: AppUserService
) {
    @GetMapping("/users")
    fun getAll(): Flux<AppUser> = appUserService.findAll()

    @GetMapping("/users/{anotherId}")
    fun getById(@PathVariable("anotherId") id: Long): Mono<AppUser> = appUserService.findById(id)

    @PostMapping("/users")
    fun createUser(@Valid @RequestBody appUserRequest: AppUserRequest): Mono<AppUser> =
        appUserService.createUser(appUserRequest)

    @PutMapping("/users/{id}")
    fun updateUser(
        @Valid @RequestBody appUserRequest: AppUserRequest,
        @PathVariable("id") id: Long
    ): Mono<AppUser> = appUserService.updateUser(id, appUserRequest)

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Long) : Mono<Void> =
        appUserService.deleteUser(id)
}