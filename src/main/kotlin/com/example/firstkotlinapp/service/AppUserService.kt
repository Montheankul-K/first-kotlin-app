package com.example.firstkotlinapp.service

import com.example.firstkotlinapp.repository.AppUserRepository
import com.example.firstkotlinapp.model.AppUser
import com.example.firstkotlinapp.model.AppUserRequest
import com.example.firstkotlinapp.model.BadRequestException
import com.example.firstkotlinapp.model.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AppUserService(
    private val appUserRepository: AppUserRepository
) {
    fun findAll(): Flux<AppUser> = appUserRepository.findAll().switchIfEmpty(
        Flux.error(
            NotFoundException("users was not found")
        )
    )

    fun findById(id: Long): Mono<AppUser> = appUserRepository.findById(id).switchIfEmpty(
        Mono.error(
            NotFoundException("user with user id: $id was not found")
        )
    )

    private fun findByEmailOrError(appUserRequest: AppUserRequest) =
        appUserRepository.findByEmail(appUserRequest.email)
            .flatMap {
                Mono.error<AppUser>(
                    BadRequestException("user with email ${appUserRequest.email} already exists")
                )
            }

    fun createUser(appUserRequest: AppUserRequest): Mono<AppUser> =
        findByEmailOrError(appUserRequest).switchIfEmpty(
            appUserRepository.save(
                AppUser(
                    name = appUserRequest.name,
                    email = appUserRequest.email
                )
            )
        )

    fun updateUser(id: Long, appUserRequest: AppUserRequest): Mono<AppUser> =
        findById(id).flatMap {
            findByEmailOrError(appUserRequest)
                .switchIfEmpty(
                    appUserRepository.save(
                        AppUser(
                            id = id,
                            name = appUserRequest.name,
                            email = appUserRequest.email
                        )
                    )
                )
        }

    fun deleteUser(id: Long): Mono<Void> =
        findById(id).flatMap {
            appUserRepository.deleteById(id)
        }
}