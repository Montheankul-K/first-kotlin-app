package com.example.firstkotlinapp.repository

import com.example.firstkotlinapp.model.AppUser
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface AppUserRepository : ReactiveCrudRepository<AppUser, Long> {
    fun findByEmail(email: String): Mono<AppUser>
}